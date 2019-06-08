package com.example.tusha.maa;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class Verify1 extends AppCompatActivity {

    EditText e1;
    String preg_verify_aadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify1);

        e1=findViewById(R.id.wife_id_hint);

        SharedPreferences preferences=getSharedPreferences("Verify_Aadhar", Context.MODE_PRIVATE);
        preg_verify_aadhar=preferences.getString("preg_verify_aadhar","");
        Log.d("ID!!!!!!!!", preg_verify_aadhar);
        e1.setText(preg_verify_aadhar);
    }
}

