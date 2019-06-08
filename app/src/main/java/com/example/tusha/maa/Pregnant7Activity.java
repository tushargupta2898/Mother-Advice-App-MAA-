package com.example.tusha.maa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Pregnant7Activity extends AppCompatActivity {

    RadioGroup radioGroup1,radioGroup2,radioGroup3;
    RadioButton x,y,z;
    ProgressDialog pDialog;
    String vdrl,hiv,sickling,aadhar1,userid_ang,userid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregnant_activity7);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        SharedPreferences preferences1=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences1.getString("preg_aadhar","");
//        SharedPreferences preferences2=getSharedPreferences("ANG_ID", Context.MODE_PRIVATE);
//        userid_ang=preferences2.getString("ang_user_id","");
//        SharedPreferences preferences3=getSharedPreferences("ANM_ID", Context.MODE_PRIVATE);
//        userid=preferences3.getString("anm_user_id","");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.next:
                            radioGroup1=findViewById(R.id.vdrl_test_option);
                            int selectedid1 = radioGroup1.getCheckedRadioButtonId();
                            x=findViewById(selectedid1);
                            vdrl=x.getText().toString();

                            radioGroup2=findViewById(R.id.hiv_test_option);
                            int selectedid2 = radioGroup2.getCheckedRadioButtonId();
                            y=findViewById(selectedid2);
                            hiv=y.getText().toString();

                            radioGroup3=findViewById(R.id.sickling_test_option);
                            int selectedid3 = radioGroup3.getCheckedRadioButtonId();
                            z=findViewById(selectedid3);
                            sickling=z.getText().toString();
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
            Intent i = new Intent(Pregnant7Activity.this, MomFrontActivity.class);
            startActivity(i);
    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"pregnantinsert7.php",
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
                            Toast.makeText(Pregnant7Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("VDRL_RPR_test",vdrl);
                params.put("HIV_test", hiv);
                params.put("sickling_test",sickling);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
