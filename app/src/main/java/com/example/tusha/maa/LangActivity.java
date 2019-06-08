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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class LangActivity extends AppCompatActivity{

RadioButton rb1,rb2,rb3,rb4;
    private static final int Request_call=1;
    private TextToSpeech tts;
    private TextView txtText,txtText1,txtText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);

        rb1=(RadioButton)findViewById(R.id.en);
        rb2=(RadioButton)findViewById(R.id.hi);
        rb3=(RadioButton)findViewById(R.id.mr);
        rb4=(RadioButton)findViewById(R.id.ta);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setLanguage(new Locale("hi","IN"));
                    tts.setLanguage(new Locale("mr","IN"));
                    tts.setLanguage(new Locale("ta","IN"));
                }
            }
        });
        txtText = (TextView) findViewById(R.id.textlanguage1);
        txtText1 = (TextView) findViewById(R.id.textlanguage2);
        txtText2 = (TextView) findViewById(R.id.textlanguage3);
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

        //is the current radio button now checked?

        Intent i = new Intent(this, WhoYouAreActivity.class);
        startActivity(i);

        boolean  checked = ((RadioButton) v).isChecked();
        String lang="en";
        //now check which radio button is selected
        //android switch statement
        switch( v.getId()){
            case R.id.en:
                if(checked)
                    lang = "en";
                    LocaleHelper.setLocale(this,lang);
                    recreate();
                break;

            case R.id.hi:
                if(checked)
                    lang="hi";
                    LocaleHelper.setLocale(this, lang);
                    recreate();
                break;
            case R.id.mr:
                if(checked)
                    lang="mr";
                LocaleHelper.setLocale(this, lang);
                recreate();
                break;
            case R.id.ta:
                if(checked)
                    lang="ta";
                LocaleHelper.setLocale(this, lang);
                recreate();
                break;
        }
    }

    private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(LangActivity.this,
                    Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LangActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }else{
            Toast.makeText(LangActivity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
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
    private void speakOut() {

        String text =txtText.getText().toString();
        String tex =txtText1.getText().toString();
        String te =txtText2.getText().toString();
        String text1 =rb1.getText().toString();
        String text2 =rb2.getText().toString();
        String text3=rb3.getText().toString();
        String text4=rb4.getText().toString();
        tts.speak(text +"\n"+ tex +"\n"+ te + "\nfirst\n" + text1 + "\nsecond\n" + text2+ "\nthird\n"+text3 + "\nfourth\n"+text4,TextToSpeech.QUEUE_FLUSH, null);

    }

}