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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Pregnant6Activity extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    ProgressDialog pDialog;
    String hospital_code,hospital_name,hospital_address,aadhar1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregnant_activity6);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ed1=findViewById(R.id.hosp_code_hint);
        ed2=findViewById(R.id.hosp_name_hint);
        ed3=findViewById(R.id.hosp_address_hint);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("preg_aadhar","");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.next:
                            hospital_code=ed1.getText().toString();
                            hospital_name=ed2.getText().toString();
                            hospital_address=ed3.getText().toString();
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
        Intent i = new Intent(this, Pregnant7Activity.class);
        startActivity(i);
    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"pregnantinsert6.php",
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
                            Toast.makeText(Pregnant6Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("preferred_hospital_code",hospital_code);
                params.put("hospital_name", hospital_name);
                params.put("hospital_address",hospital_address);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
