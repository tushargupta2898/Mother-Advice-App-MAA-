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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class Pregnant4Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int Request_call = 1;
    private TextToSpeech tts;
    TextView rb1, rb2;
    private int mYear, mMonth, mDay;
    ImageView btnDatePicker1;
    EditText wife_dod,disease,pregnancies;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    ProgressDialog pDialog;
    String expected_date,blood_group,present_disease,total_pregnancy,aadhar1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregnant_activity4);

        /*rb1 = (TextView) findViewById(R.id.curr);
        rb2 = (TextView) findViewById(R.id.bloodgroup);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
        speakOut();*/

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        btnDatePicker1=findViewById(R.id.dod_img);
        btnDatePicker1.setOnClickListener(this);
        disease=findViewById(R.id.dis_hint_text);
        pregnancies=findViewById(R.id.nop_hint_text);
        spinner=findViewById(R.id.spinner1);
        adapter=ArrayAdapter.createFromResource(this,R.array.blood,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wife_dod=findViewById(R.id.wife_dod);
        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("preg_aadhar","");
        spinner.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if (v == btnDatePicker1) {
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

                            wife_dod.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.next:
                            expected_date=wife_dod.getText().toString();
                            blood_group=spinner.getSelectedItem().toString();
                            present_disease=disease.getText().toString();
                            total_pregnancy=pregnancies.getText().toString();
                            InsertForm();
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
    private void callActivity() {
        Intent i = new Intent(this, Pregnant5Activity.class);
        startActivity(i);
    }


        private void makephonecall() {
        String s = "8574781211";
        if (s.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Pregnant4Activity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Pregnant4Activity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, Request_call);
            } else {
                String dial = "tel:" + s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(Pregnant4Activity.this, "Enter phonr number", Toast.LENGTH_SHORT).show();
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
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"pregnantinsert4.php",
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
                            Toast.makeText(Pregnant4Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("delivery_date",expected_date);
                params.put("blood_group", blood_group);
                params.put("disease",present_disease);
                params.put("no_of_prenancies",total_pregnancy);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
