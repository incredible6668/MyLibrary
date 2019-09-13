package com.example.myandroidlibrary;

import android.content.Context;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubePlayerModule extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private Context mContext;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String videoURL;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private String YOUTUBE_API_KEY;

    public YouTubePlayerModule(Context context, String YOUTUBE_API_KEY, String videoURL, YouTubePlayerView youTubeView){
        this.mContext = context;
        this.videoURL = videoURL;
        this.youTubeView = youTubeView;
        this.YOUTUBE_API_KEY = YOUTUBE_API_KEY;
        playerStateChangeListener = new MyPlayerStateChangeListener(context);
        youTubeView.initialize(YOUTUBE_API_KEY, this);
    }

    public static String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        if (!wasRestored) {
            player.cueVideo(extractYTId(videoURL));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            /*String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();*/
        }
    }
}
