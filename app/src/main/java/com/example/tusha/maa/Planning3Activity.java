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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Planning3Activity extends AppCompatActivity {

    EditText heightwife,weightwife;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    String aadhar1,height_wife,weight_wife,check_Box1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning3);
        heightwife= findViewById(R.id.height_wife);
        weightwife= findViewById(R.id.weight_wife);
        checkBox1= findViewById(R.id.checkBox1);
        checkBox2= findViewById(R.id.checkBox2);
        checkBox3= findViewById(R.id.checkBox3);
        checkBox4= findViewById(R.id.checkBox4);

        SharedPreferences preferences=getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
        aadhar1=preferences.getString("plan_aadhar","");


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            height_wife=heightwife.getText().toString();
                            weight_wife=weightwife.getText().toString();
                            check_Box1 = checkBox1.getText().toString();
                           InsertForm();

                            break;
                        case R.id.emergency:
                         //   makephonecall();
                            break;
                        case R.id.text_to_speech:
                           // speakOut();
                            break;

                    }
                    return true;
                }

            };
    private void callActivity1(){
        Intent i=new Intent(this,PlanningFront.class);
        startActivity(i);
        finish();
    }

    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi +"planning_insert3.php",
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
                            Toast.makeText(Planning3Activity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
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
                params.put("wife_height",height_wife);
                params.put("wife_weight",weight_wife);
                params.put("health_concern",check_Box1);
                Log.e("@params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
