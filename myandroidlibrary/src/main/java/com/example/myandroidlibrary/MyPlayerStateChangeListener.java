package com.example.myandroidlibrary;

import android.content.Context;
import android.content.Intent;

import com.google.android.youtube.player.YouTubePlayer;

public class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

    Context context;

    public MyPlayerStateChangeListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLoading() {
        // Called when the player is loading a video
        // At this point, it's not ready to accept commands affecting playback such as play() or pause()
    }

    @Override
    public void onLoaded(String s) {
        // Called when a video is done loading.
        // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
    }

    @Override
    public void onAdStarted() {
        // Called when playback of an advertisement starts.
    }

    @Override
    public void onVideoStarted() {
        // Called when playback of the video starts.
    }

    @Override
    public void onVideoEnded() {
/*        if(SharedPreferencesHelper.getSharedPreferenceInt(context,"InitialFeelingsIntensity",0) == 0){
            Intent intent = new Intent(context, HomePageActivity.class);
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, SessionOffboardActivity.class);
            context.startActivity(intent);
        }*/
       /* Intent intent = new Intent(context, SessionOffboardActivity.class);
        context.startActivity(intent);*/
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        // Called when an error occurs.
    }
}