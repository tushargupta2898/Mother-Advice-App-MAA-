package com.example.tusha.maa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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

public class ANMLoginActivity extends AppCompatActivity {
    EditText useridEt,passwordEt;
    String userid,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anmlogin);
        useridEt = findViewById(R.id.useridlogin);
        passwordEt = findViewById(R.id.passwordlogin);

    }


    public void OnLogin(View view){
        userid=useridEt.getText().toString();

//        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(ANMLoginActivity.this);
//        SharedPreferences.Editor editor=prefs.edit();
//        editor.putString("anm_user_id",userid);
//        editor.apply();
        password=passwordEt.getText().toString();

        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi +"anm_login.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ANMLoginActivity.this,"you are logged in", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ANMLoginActivity.this, ANMRegistration.class);
                        //i.putExtra("anmuserid",userid);
                        //Log.d("anmuserid", userid);

                        SharedPreferences prefs= getSharedPreferences("ANM_ID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefs.edit();
                        editor.putString("anm_user_id",userid);
                        editor.apply();

                        startActivity(i);
                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError){
                            Toast.makeText(ANMLoginActivity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                })
        {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("password", password);
                Log.e("params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }

}