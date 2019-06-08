package com.example.tusha.maa;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Planning1Activity extends AppCompatActivity implements View.OnClickListener{

    private static final int Request_call=1;
    private TextToSpeech tts;
    TextView rb1,rb2,rb3,rb4;
    private int mYear, mMonth, mDay;
    ImageView btnDatePicker1,btnDatePicker2;
    EditText wifename,husbandname,wifedob,husbanddob;
    String aadhar1,wife_name,husband_name,wife_dob,husband_dob;
   // ProgressDialog pDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning1);

        rb1=(TextView)findViewById(R.id.wife_name);
        rb2=(TextView)findViewById(R.id.wife_dob);

        rb3=(TextView)findViewById(R.id.husband_name);
        rb4=(TextView)findViewById(R.id.husband_dob);
        btnDatePicker1=(ImageView) findViewById(R.id.wife_dob_img);
        btnDatePicker2=(ImageView)findViewById(R.id.husband_dob_img);
        wifename = findViewById(R.id.autoCompleteTextView1);
        husbandname = findViewById(R.id.autoCompleteTextView2);
        wifedob = findViewById(R.id.wife_dob2);
        husbanddob = findViewById(R.id.husband_dob2);
        btnDatePicker1.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("plan_aadhar","");
       // speakOut();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }


    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        if (v == btnDatePicker1) {

            // Get Current Date


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        private DatePicker view;
                        private int year;
                        private int monthOfYear;
                        private int dayOfMonth;

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            this.view = view;
                            this.year = year;
                            this.monthOfYear = monthOfYear;
                            this.dayOfMonth = dayOfMonth;

                            wifedob.setText( year  + "-" + (monthOfYear + 1)+ "-" +dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        private DatePicker view;
                        private int year;
                        private int monthOfYear;
                        private int dayOfMonth;

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            this.view = view;
                            this.year = year;
                            this.monthOfYear = monthOfYear;
                            this.dayOfMonth = dayOfMonth;

                            husbanddob.setText( year  + "-" + (monthOfYear + 1)+ "-" +dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                         wife_name = wifename.getText().toString();
                            husband_name = husbandname.getText().toString();
                            wife_dob = wifedob.getText().toString();
                            husband_dob = husbanddob.getText().toString();
                            InsertForm();

                            break;
                        case R.id.emergency:
                           // makephonecall();
                            break;
                        case R.id.text_to_speech:
                            //speakOut();
                            break;

                    }
                    return true;
                }

            };
    private void callActivity(){
        Intent i=new Intent(this,Planning2Activity.class);
        startActivity(i);
        finish();
    }
   /* private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Planning1Activity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Planning1Activity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(Planning1Activity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
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
                rb5.setEnabled(true);
                rb6.setEnabled(true);

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
        String text5 =rb5.getText().toString();
        String text6 =rb6.getText().toString();

        tts.speak("\n first \n"+text1+"\n second \n"+text2+"\n third \n"+text3+"\n fourth \n"+text4+"\n fifth \n"+text5+"\n sixth \n"+text6, TextToSpeech.QUEUE_FLUSH, null);

    }*/

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi +"planning_insert1.php",
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
                            Toast.makeText(Planning1Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
                           // pDialog.hide();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                })
        {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("aadhar_number", aadhar1);
                params.put("wife_name",wife_name);
                params.put("wife_dob",wife_dob);
                params.put("husband_name",husband_name);
                params.put("husband_dob",husband_dob);

                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}