package com.example.tusha.maa;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Pregnant2Activity extends AppCompatActivity {
    private static final int Request_call=1;
    private TextToSpeech tts;
    TextView rb1,rb2,rb3,rb4;
    EditText b;
    RadioGroup c,d,e;
    RadioButton x,y,z;
    ProgressDialog pDialog;
    String contact_number,whose_contact_number,religion,category,aadhar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnant2);
        rb1=findViewById(R.id.mobnum);
        rb2=findViewById(R.id.whosenum);
        rb3=findViewById(R.id.caste);
        rb4=findViewById(R.id.category);
        b = findViewById(R.id.contactnum);


        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("preg_aadhar","");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        speakOut();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            contact_number=b.getText().toString();
                            c=findViewById(R.id.whose_contact_is_this);
                            int selectedid1 = c.getCheckedRadioButtonId();
                            x=findViewById(selectedid1);
                            whose_contact_number=x.getText().toString();
                            d=findViewById(R.id.religion_option);
                            int selectedid2 = d.getCheckedRadioButtonId();
                            y=findViewById(selectedid2);
                            religion=y.getText().toString();
                            e=findViewById(R.id.category_option);
                            int selectedid3 = e.getCheckedRadioButtonId();
                            z=findViewById(selectedid3);
                            category=z.getText().toString();
                            addString();

                            break;
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
    private void callActivity(){
        Intent i=new Intent(this,Pregnant3Activity.class);
        startActivity(i);
        finish();
    }
    private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Pregnant2Activity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Pregnant2Activity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(Pregnant2Activity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
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
                rb4.setEnabled(true);

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
        String text4 =rb4.getText().toString();

        tts.speak("\n  first  \n " + text1 +   "    second   \n" +text2 +" third \n" + text3+" fourth \n" + text4, TextToSpeech.QUEUE_FLUSH, null);

    }
    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"insertpregnant2.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        callActivity();
                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError){
                            Toast.makeText(Pregnant2Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
                            pDialog.hide();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                })
        {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("aadhar_number", aadhar1);
                params.put("contact_number", contact_number);
                params.put("whose_number", whose_contact_number);
                params.put("religion", religion);
                params.put("category",category);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
    private void addString(){
        if(contact_number.length()!=10){
            Toast.makeText(this,"length of contact number should be 10",Toast.LENGTH_LONG).show();
            b.requestFocus();
            return;
        }
        else
        {
            InsertForm();
        }
        //  progressBar.setVisibility(View.VISIBLE);
    }
}
