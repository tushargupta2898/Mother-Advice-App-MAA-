package com.example.tusha.maa;

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

public class Parent2Activity extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    String weight,sys_bp,dia_bp,aadhar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity2);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ed1=findViewById(R.id.wgt_hint);
        ed2=findViewById(R.id.sys_bp_hint);
        ed3=findViewById(R.id.dia_bp_hint);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("parent_aadhar","");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            weight=ed1.getText().toString();
                            sys_bp=ed2.getText().toString();
                            dia_bp=ed3.getText().toString();
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
        Intent i=new Intent(this,Parent3Activity.class);
        startActivity(i);
        finish();
    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi +"parent_insert2.php",
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
                            Toast.makeText(Parent2Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("mother_weight",weight);
                params.put("sys_bp",sys_bp);
                params.put("dia_bp",dia_bp);

                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}