package com.example.tusha.maa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Locale;

public class MomFrontActivity extends AppCompatActivity {

    private static final int Request_call=1;
    private TextToSpeech tts;
    TextView rb1,rb2,rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momfront);

        CardView card_view1 = (CardView) findViewById(R.id.pregnantvid);
        CardView card_view2 = (CardView) findViewById(R.id.pregnanthelpline);
        CardView card_view3 = (CardView) findViewById(R.id.pregnantscheme);
        CardView card_view4 = (CardView) findViewById(R.id.pregnantdiet);
        ImageView imageView = findViewById(R.id.imageView5);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MomFrontActivity.this, VideoTutorial.class);
                startActivity(I);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MomFrontActivity.this, HelpLineActivity.class);
                startActivity(I);
            }
        });
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MomFrontActivity.this, GovtSchemes.class);
                startActivity(I);
            }
        });
        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MomFrontActivity.this, DietPlanActivity.class);
                startActivity(I);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MomFrontActivity.this, MomProfileActivity.class);
                startActivity(I);
            }
        });

       /* rb1=(TextView)findViewById(R.id.mobnum);
        rb2=(TextView)findViewById(R.id.whosenum);
        rb3=(TextView)findViewById(R.id.caste);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });*/
       // speakOut();

        BottomNavigationView bottomNav = findViewById(R.id.homepage_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener1);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener1 =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.homeicon:
                            callActivity1();
                            break;
                        case R.id.emergency:
                          //  makephonecall();
                            break;
                        case R.id.text_to_speech:
                          //  speakOut();
                            break;

                    }
                    return true;
                }

            };
    private void callActivity1(){
        Intent i=new Intent(this,MomFrontActivity.class);
        startActivity(i);
    }

   /* private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MomFrontActivity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MomFrontActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(MomFrontActivity.this,"Enter phone number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String[] permissions,@NonNull int[] grantResults){
        if(requestcode==Request_call){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                rb3.setEnabled(true);
                rb1.setEnabled(true);
                rb2.setEnabled(true);

                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }
    private void speakOut() {


        String text1 =rb1.getText().toString();
        String text2 =rb2.getText().toString();
        String text3 =rb3.getText().toString();

        tts.speak("\n  first  \n " + text1 +   "    second   \n" +text2 +" third \n" + text3, TextToSpeech.QUEUE_FLUSH, null);

    }*/
    }
