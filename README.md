# Girl Talk Radio Application

## Description
  
The Girl Talk Radio android application allows users to stream the same popdcasts from the Girl Talk Radio website on a mobile device. 

This application is written in Java and uses Firebase as a backend solution. The decision to use Java was to reach a higher audience as there are statistically more android users than IOS or other.

## Set-up
1. To run this application on Android Studio - Open the project from the folder labeled "JustAGirlCo"
2. The project may take a moment to open, and once it does make sure you are able to see the top level folder as "app" and there should be a phone emulator option
    ![How the Editor should Look](https://firebasestorage.googleapis.com/v0/b/justagirlco-b4de1.appspot.com/o/androidStudio.png?alt=media&token=80ac76f5-a690-4955-9519-e1b81becc682)

    1. If it does not look this way, you might need to select **File > Sync Project with Gradle Files**

 3. Select the green play button to start the emulator and launch the application. 



## Technologies 
* Android Studio is reccommended to use as the developer environment
* Firebase Realtime database is populated using a Google Sheet 
* Firebase Authentication is used to keep track of users.
* The [Firebase-UI](https://github.com/firebase/FirebaseUI-Android/tree/4455f411e3605743bb4511d1a75cda7f398bc399/database) API is used to populate the grid views in both the Podcasts page and the Top 10 Page
    * The Podcasts page uses the FirebaseRecyclerPagingAdapter
    * The Top 10 page uses the FirebaseRecyclerAdapter because there are only 10 items 
* [Picasso](https://square.github.io/picasso/) is used to parse the image urls for display in many areas of the application

## Status
This application is almost ready for going live on the Google Play Store. The minimum two options to get this ready for completion are:
1. Add a user account or settings page
2. Make sure that the audio can play outside of the listening screen -- audio should play while the phone is locked, or while the user navigates to other pages of the app or their own device.


## High Level Software Design Flow
![Software Design Flow Chart](https://firebasestorage.googleapis.com/v0/b/justagirlco-b4de1.appspot.com/o/HighLevelSoftwareDesignFlow.png?alt=media&token=dae431b7-3e15-4558-b1d4-af55db0bd709)
