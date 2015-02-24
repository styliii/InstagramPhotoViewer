# Instagram Popular Photo Viewer
This is an Android networking and layout demo for displaying popular photos from Instagram using the [Instagram API](https://instagram.com/developer/). 

## Overview
The app does the following: 

1.  User can scroll through current popular photos from Instagram
2.  For each photo displayed, user can see the following details:
    - Graphic, Caption, Username
    - relative timestamp, like count, user profile image
3.  Shows latest comment for each photo
4.  Displays each photo with the same style and proportions as the real Instagram (sorta)
5.  Displays each user profile image using a RoundedImageView
6.  Improves the user interface through styling and coloring
7.  Displays a nice default placeholder graphic for each image during loading

Time Spent: 5 hours

## Walkthrough
![app demo](./popularPhotos_demo.gif)
GIF created with [LiceCap](http://www.cockos.com/licecap/)

## Libraries

- [Android AsyncHTTPClient](http://loopj.com/android-async-http/) - For asynchronous network requests
- [Picasso](http://square.github.io/picasso/) - For remote image loading
