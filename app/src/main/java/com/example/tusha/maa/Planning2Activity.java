package com.example.tusha.maa;

import android.Manifest;
import android.app.DatePickerDialog;
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

public class Planning2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int Request_call=1;
    private TextToSpeech tts;
    TextView rb1,rb2,rb3,rb4,rb5,rb6,rb7;
    private int mYear, mMonth, mDay;
    ImageView btnDatePicker1,btnDatePicker2;
    EditText marriagedate,lmpdate;
    String aadhar1,marriage_date,lmp_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning2);

        marriagedate = findViewById(R.id.wedding_date);
        lmpdate = findViewById(R.id.lmp_hint_date);
        btnDatePicker1=(ImageView) findViewById(R.id.marriage_dob_img);
        btnDatePicker2=(ImageView)findViewById(R.id.lmp_dob_img);
        btnDatePicker1.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);
        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("plan_aadhar","");

        /*rb1=(TextView)findViewById(R.id.height);
        rb2=(TextView)findViewById(R.id.weight);
        rb3=(TextView)findViewById(R.id.health_concern);
        rb4=(TextView)findViewById(R.id.checkBox1);
        rb5=(TextView)findViewById(R.id.checkBox2);
        rb6=(TextView)findViewById(R.id.checkBox3);
        rb7=(TextView)findViewById(R.id.checkBox4);




        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        speakOut();*/

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

                            marriagedate.setText( year  + "-" + (monthOfYear + 1)+ "-" +dayOfMonth);

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

                            lmpdate.setText( year  + "-" + (monthOfYear + 1)+ "-" +dayOfMonth);

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
                            marriage_date = marriagedate.getText().toString();
                            lmp_date = lmpdate.getText().toString();
                           InsertForm();

                            break;
                        case R.id.emergency:
                            //makephonecall();
                            break;
                        case R.id.text_to_speech:
                            //speakOut();
                            break;

                    }
                    return true;
                }

            };
    private void callActivity1(){
        Intent i=new Intent(this,Planning3Activity.class);
        startActivity(i);
        finish();
    }
    /*private void makephonecall(){
        String s="8574781211";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Planning2Activity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Planning2Activity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(Planning2Activity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
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
                rb7.setEnabled(true);

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
        String text7 =rb7.getText().toString();

        tts.speak("\n first \n"+text1+" second \n"+text2+" third \n"+text3+"\n first \n"+text4+" second \n"+text5+" third \n"+text6+" fourth \n"+text7, TextToSpeech.QUEUE_FLUSH, null);

    }*/

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi + "planning_insert2.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        callActivity1();
                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError){
                            Toast.makeText(Planning2Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("marriage_date",marriage_date);
                params.put("last_period_date",lmp_date);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
