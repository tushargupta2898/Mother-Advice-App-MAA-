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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MomTypeActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3;
    private static final int Request_call=1;
    private TextToSpeech tts;
    private TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momtype);
        rb1=(RadioButton)findViewById(R.id.planning);
        rb2=(RadioButton)findViewById(R.id.pregnant);
        rb3=(RadioButton)findViewById(R.id.parent);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        txtText = (TextView) findViewById(R.id.areyou);
        speakOut();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.emergency:
                            makephonecall();
                            break;
                        case R.id.text_to_speech:
                            speakOut();
                            break;
                    }
                    return true;
                }

            };


    public void onRadioButtonClicked(View v)
    {
        boolean  checked = ((RadioButton) v).isChecked();

        String lang="en";
        switch( v.getId()){
            case R.id.planning:
                if(checked)
                    startActivity(new Intent(this,PlanningAadhar.class));
                break;

            case R.id.pregnant:
                if(checked)
                   startActivity(new Intent(this,AadharActivity.class));
                break;
            case R.id.parent:
                if(checked)
                    startActivity(new Intent(this,ParentAadhar.class));
                break;
        }
    }

    private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MomTypeActivity.this,
                    Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MomTypeActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }else{
            Toast.makeText(MomTypeActivity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
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
                txtText.setEnabled(true);
                rb1.setEnabled(true);
                rb2.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }
    private void speakOut() {

        String text =txtText.getText().toString();
        String text1 =rb1.getText().toString();
        String text2 =rb2.getText().toString();
        String text3 =rb3.getText().toString();

        tts.speak(text + "\n  first  \n " + text1 + "    second   \n" + text2 +" third \n" +text3, TextToSpeech.QUEUE_FLUSH, null);

    }

}
