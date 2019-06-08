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
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Pregnant5Activity extends AppCompatActivity {

    private static final int Request_call = 1;
    private TextToSpeech tts;
    TextView rb1, rb2;
    ProgressDialog pDialog;
    EditText ed1,ed2;
    RadioGroup radioGroup1,radioGroup2;
    RadioButton x,y;
    String prev_com_code,prev_result_code,before_prev_com_code,before_prev_result_code,aadhar1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregnant_activity5);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ed1=findViewById(R.id.prev_com_code);
        ed2=findViewById(R.id.bef_prev_com_code);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("preg_aadhar","");
    }

            private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.next:
                            radioGroup1=findViewById(R.id.code_select);
                            int selectedid1 = radioGroup1.getCheckedRadioButtonId();
                            x=findViewById(selectedid1);
                            prev_result_code=x.getText().toString();

                            prev_com_code=ed1.getText().toString();
                            before_prev_com_code=ed2.getText().toString();

                            radioGroup2=findViewById(R.id.before_code_select);
                            int selectedid2 = radioGroup2.getCheckedRadioButtonId();
                            y=findViewById(selectedid2);
                            prev_result_code=y.getText().toString();
                            before_prev_result_code=y.getText().toString();

                            InsertForm();
                            break;
                        case R.id.emergency:

                            break;
                        case R.id.text_to_speech:

                            break;

                    }
                    return true;
                }

            };
    private void callActivity() {
        Intent i = new Intent(this, Pregnant6Activity.class);
        startActivity(i);
    }

    private void makephonecall() {
        String s = "8574781211";
        if (s.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Pregnant5Activity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Pregnant5Activity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, Request_call);
            } else {
                String dial = "tel:" + s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(Pregnant5Activity.this, "Enter phonr number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestcode == Request_call) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makephonecall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                rb2.setEnabled(true);
                rb1.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {


        String text1 = rb1.getText().toString();
        String text2 = rb2.getText().toString();

        tts.speak("\n  first  \n " + text1 + "    second   \n" + text2, TextToSpeech.QUEUE_FLUSH, null);

    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"pregnantinsert5.php",
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
                            Toast.makeText(Pregnant5Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("previous_complexity",prev_com_code);
                params.put("previous_result", prev_result_code);
                params.put("before_complexity",before_prev_com_code);
                params.put("before_result",before_prev_result_code);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
