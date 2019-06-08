package com.example.tusha.maa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoTutorial extends YouTubeBaseActivity {
    private static final String Tag="VideoTutorial";
    YouTubePlayerView youTubePlayerView,youTubePlayerView2;
    //Button button;
    YouTubePlayer.OnInitializedListener mOnInitializedListener,mOnInitializedListener1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorial);
        Log.d(Tag,"ON Create Initialising");
        //button=findViewById(R.id.button);
        youTubePlayerView=findViewById(R.id.youtube_view);
        mOnInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(Tag,"On Click done Initializing");
                youTubePlayer.loadVideo("cxGVziyLAhk");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(Tag,"On Click failed Initializing");
            }
        };

        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag,"On Create Initializing Youtube ");
                youTubePlayerView.initialize(YouTubeApiActivity.getAPI_KEY(),mOnInitializedListener);
                Log.d(Tag,"OnCreate ");
            }
        });

        youTubePlayerView2=findViewById(R.id.youtube_view2);
        mOnInitializedListener1=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(Tag,"On Click done Initializing");
                youTubePlayer.loadVideo("I7h9FklN0GU");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(Tag,"On Click failed Initializing");
            }
        };

        youTubePlayerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag,"On Create Initializing Youtube ");
                youTubePlayerView.initialize(YouTubeApiActivity.getAPI_KEY(),mOnInitializedListener1);
                Log.d(Tag,"OnCreate ");
            }
        });
    }
}