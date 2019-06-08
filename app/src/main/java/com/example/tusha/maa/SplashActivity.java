package com.example.tusha.maa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    private ImageView icon;
    private static int SPLASH_TIME = 3000; //This is 4 seconds
    String phnumber=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        icon = (ImageView) findViewById(R.id.icon);
        Animation iconanim = AnimationUtils.loadAnimation(this,R.anim.icontransition);

        icon.startAnimation(iconanim);
        SharedPreferences preferences=getSharedPreferences("PHONE_NUM", Context.MODE_PRIVATE);
        phnumber=preferences.getString("phone_number","");

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (phnumber != null) {
//                    Intent mySuperIntent = new Intent(SplashActivity.this, MomFrontActivity.class);
//                    startActivity(mySuperIntent);
//                }else {
                    //Do any action here. Now we are moving to next page
                    Intent mySuperIntent = new Intent(SplashActivity.this, LangActivity.class);
                    startActivity(mySuperIntent);
                    /* This 'finish()' is for exiting the app when back button pressed
                     *  from Home page which is ActivityHome
                     */
                    finish();

            }
        }, SPLASH_TIME);
    }
}
