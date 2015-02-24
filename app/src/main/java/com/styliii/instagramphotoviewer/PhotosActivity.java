package com.styliii.instagramphotoviewer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "fd32cbecc0e74d49894486f97f95514a";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // Send out API request to get popular photos
        photos = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);
        fetchPopularPhotos();
    }


    public void fetchPopularPhotos() {
       // https://api.instagram.com/v1/media/popular?client_id=fd32cbecc0e74d49894486f97f95514a
        // creating client
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSON dictionary
                JSONArray photosJSON = null;
                try {
                   photosJSON = response.getJSONArray("data");
                   for (int i = 0; i < photosJSON.length(); i++) {
                      JSONObject photoJSON = photosJSON.getJSONObject(i);
                       InstagramPhoto photo = new InstagramPhoto();
                       photo.username = photoJSON.getJSONObject("user").getString("username");
                       photo.createdAt = Long.parseLong(photoJSON.getJSONObject("caption").getString("created_time"));
                       photo.profileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                       photo.caption = photoJSON.getJSONObject("caption").getString("text");
                       photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                       photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                       photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                       if (photoJSON.optJSONObject("comments") != null) {
                           photo.recentComment = photoJSON
                                .getJSONObject("comments")
                                .getJSONArray("data")
                                .getJSONObject(0)
                                .getString("text");
                           photo.commentUser = photoJSON
                               .getJSONObject("comments")
                               .getJSONArray("data")
                               .getJSONObject(0)
                               .getJSONObject("from")
                               .getString("username");
                       }
                       photos.add(photo);
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                // DO SOMETHING
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instagram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
