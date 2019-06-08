package com.example.tusha.maa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class angadwadi_login extends AppCompatActivity {
    EditText userid_ang_Et,password_ang_Et;
    String userid_ang,password_ang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angadwadi_login);

        userid_ang_Et = findViewById(R.id.userid_ang_login);
        password_ang_Et = findViewById(R.id.password_ang_login);

    }

    public void OnLogin(View view){

        userid_ang=userid_ang_Et.getText().toString();
        password_ang=password_ang_Et.getText().toString();

        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"angadwadi_login.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(angadwadi_login.this,"you are logged in", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(angadwadi_login.this, angadwadi_home_page.class);
                        //i.putExtra("anmuserid",userid_ang);

                        SharedPreferences prefs_2= getSharedPreferences("ANG_ID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefs_2.edit();
                        editor.putString("ang_user_id",userid_ang);
                        editor.apply();

                        startActivity(i);
                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError){
                            Toast.makeText(angadwadi_login.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                })
        {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid_ang);
                params.put("password", password_ang);
                Log.e("params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
