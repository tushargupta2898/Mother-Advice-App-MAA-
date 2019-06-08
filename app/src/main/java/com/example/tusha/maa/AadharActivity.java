package com.example.tusha.maa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class AadharActivity extends AppCompatActivity {

    EditText a;
    ProgressDialog pDialog;
   String aadhar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);
        a=(EditText)findViewById(R.id.wife_id_hint);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    public ProgressDialog showProgressDialog(final Context mActivity, final String message, boolean isCancelable) {
        final ProgressDialog mDialog = new ProgressDialog(mActivity);
        mDialog.show();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        return mDialog;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            aadhar=a.getText().toString();
                            addString();

                            break;
                        case R.id.emergency:
                           // makephonecall();
                            break;
                        case R.id.text_to_speech:
                           // speakOut();
                            break;
                    }
                    return true;
                }

            };


    private void InsertForm() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, commonapi+"insertpregnantaadhar.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Intent i=new Intent(AadharActivity.this,PregnantActivity.class);
                        SharedPreferences prefs= getSharedPreferences("Aadhar", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=prefs.edit();
                        editor.putString("preg_aadhar",aadhar);
                        editor.apply();
                        startActivity(i);
                        finish();

                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError){
                            Toast.makeText(AadharActivity.this,"Please Check your connection", Toast.LENGTH_SHORT).show();
                           pDialog.hide();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                })
        {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("aadhar_number", aadhar);
                Log.e("params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }

    private void addString(){



        if (TextUtils.isEmpty(aadhar)){
            Toast.makeText(this,"you should enter your aadhar number",Toast.LENGTH_LONG).show();
            a.requestFocus();
            return;
        }
        else if(aadhar.length()!=16){
            Toast.makeText(this,"length of aadhar number should be 16",Toast.LENGTH_LONG).show();
            a.requestFocus();
            return;
        }
        else
        {
            InsertForm();
        }
      //  progressBar.setVisibility(View.VISIBLE);
    }
}
