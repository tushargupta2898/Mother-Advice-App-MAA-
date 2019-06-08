package com.example.tusha.maa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class temp555 extends AppCompatActivity {

    TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp555);
        a = (TextView) findViewById(R.id.textView);
        UserLogin();
    }

    private void UserLogin() {
        String cancel_req_tag = "login";
        Log.d("OmSai:-", "start");
        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://192.168.1.100/web_services/select.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);


                        try {

//            jsonObject=new JSONObject();
                            JSONObject jsonObject = new JSONObject(response);
//            JSONArray query = jsonParse.getJSONArray("courses");

                            JSONArray jsonArray = jsonObject.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobject = jsonArray.getJSONObject(i);

                                String AID = jobject.getString("aadhar_number");
                                Toast.makeText(temp555.this, AID, Toast.LENGTH_SHORT).show();
                                a.setText(AID);
                            }


//                           JSONArray jsonArray=jsonObject.getJSONArray("result");
//
//
//                            int count=0;
//                            String aadhar_number = null;
//
//                            while (count<jsonArray.length()){
//                                JSONObject JO=jsonArray.getJSONObject(count);
//                                aadhar_number=JO.getString("aadhar_number");
//                                Toast.makeText(temp555.this, aadhar_number, Toast.LENGTH_SHORT).show();
//                            }

                            //  Log.d("MyAdhar@@@@", aadhar_number);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(temp555.this, "Please Check your connection", Toast.LENGTH_SHORT).show();
                        }

                        Log.d("OmSai:ErrorResponse", error.toString());
                    }
                }) {
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                // params.put("aadhar_number", aadhar);
                Log.e("params", params.toString());
                return params;

            }

        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest, cancel_req_tag);

    }
}
