package com.example.atulgoel.nirogaapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myandroidlibrary.YouTubePlayerModule;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {
    private YouTubePlayerView youTubeView;
    private String videoURL;
    private String YOUTUBE_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoURL = "https://youtu.be/aiQA1OzE7-Q";
        YOUTUBE_API_KEY = "https://youtu.be/aiQA1OzE7-Q";
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        YouTubePlayerModule youtubePlayer = new YouTubePlayerModule(this, YOUTUBE_API_KEY, videoURL, youTubeView);
    }
}
