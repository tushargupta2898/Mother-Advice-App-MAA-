package com.example.tusha.maa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class HealthCheckup extends AppCompatActivity implements View.OnClickListener {

    private static final int Request_call = 1;
    private TextToSpeech tts;
    TextView regdate,weightmother,sysbpmother,diabpmother;
    private int mYear, mMonth, mDay;
    ImageView btnDatePicker1;
    Button btnsubmit;
    EditText check_up_hint_date,check_up_hint_weight,check_up_hint_sys_bp_hint,check_up_hint_dia_bp_hint;
    String reg_date,weight_mother,sys_bp_mother,dia_bp_mother,aadhar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup);


        check_up_hint_date=findViewById(R.id.check_up_hint_date);
        check_up_hint_weight=findViewById(R.id.check_up_hint_weight);
        check_up_hint_sys_bp_hint=findViewById(R.id.check_up_hint_sys_bp_hint);
        check_up_hint_dia_bp_hint=findViewById(R.id.check_up_hint_dia_bp_hint);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("plan_aadhar","");
        btnDatePicker1=findViewById(R.id.check_up_img);
        btnDatePicker1.setOnClickListener(HealthCheckup.this);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(HealthCheckup.this);


    }
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker1) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
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

                            check_up_hint_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if(v == btnsubmit){
            reg_date = regdate.getText().toString();
            weight_mother = weightmother.getText().toString();
            sys_bp_mother = sysbpmother.getText().toString();
            dia_bp_mother= diabpmother.getText().toString();
            InsertForm();
        }
    }


    private void callActivity() {
        Intent i = new Intent(this, ANMRegistration.class);
        startActivity(i);
    }
    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"checkupinsert.php",
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
                            Toast.makeText(HealthCheckup.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("date_of_checkup",reg_date);
                params.put("weight_of_mother",weight_mother);
                params.put("sys_bp_of_mother",sys_bp_mother);
                params.put("dia_bp_of_mother",dia_bp_mother);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
