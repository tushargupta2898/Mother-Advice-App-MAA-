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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Parent3Activity extends AppCompatActivity {
    EditText ed1,ed2;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    String weight_baby,height_baby,vaccine,aadhar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity3);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        ed1=findViewById(R.id.wgt_hint_baby);
        ed2=findViewById(R.id.height_hint_baby);
        checkBox1= findViewById(R.id.checkBox1_vaac);
        checkBox2= findViewById(R.id.checkBox2_vacc);
        checkBox3= findViewById(R.id.checkBox3_vacc);
        checkBox4= findViewById(R.id.checkBox4_vacc);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("parent_aadhar","");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            weight_baby=ed1.getText().toString();
                            height_baby=ed2.getText().toString();
                            vaccineType();
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
        Intent i=new Intent(this,ParentFront.class);
        startActivity(i);
        finish();
    }

    public String vaccineType(){
        if(checkBox1.isChecked())
            vaccine=checkBox1.getText().toString();
        else if(checkBox2.isChecked())
            vaccine=checkBox2.getText().toString();
        else if(checkBox3.isChecked())
            vaccine=checkBox3.getText().toString();
        else
            vaccine=checkBox4.getText().toString();

        return vaccine;
    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi +"parent_insert3.php",
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
                            Toast.makeText(Parent3Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("baby_weight",weight_baby);
                params.put("height_baby",height_baby);
                params.put("vaccination",vaccine);

                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}