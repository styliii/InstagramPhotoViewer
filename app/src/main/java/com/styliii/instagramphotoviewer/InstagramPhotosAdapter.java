package com.styliii.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item  for this position
        InstagramPhoto photo = getItem(position);

        // check if this is a convert view, if not, inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // lookup the views for populating the data
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvProfile = (TextView) convertView.findViewById(R.id.tvProfile);
        TextView tvTimeAgo = (TextView) convertView.findViewById(R.id.tvTimeAgo);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        // insert the model data into each of the view items
        String formattedCaption = "<strong>" + photo.username + "</strong> " + photo.caption;
        String formattedComment = "<strong>" + photo.commentUser + "</strong> " + photo.recentComment;

        tvCaption.setText(Html.fromHtml(formattedCaption));
        tvComment.setText(Html.fromHtml(formattedComment));
        tvProfile.setText(photo.username);
        tvTimeAgo.setText(DateUtils.getRelativeTimeSpanString(photo.createdAt * 1000));
        tvLikes.setText(photo.likesCount + " likes");
        ivPhoto.setImageResource(0);
        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).fit().centerCrop().placeholder(R.drawable.ic_launcher).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profileUrl).transform(new CircleTransform()).into(ivProfile);
        return convertView;
    }
}
