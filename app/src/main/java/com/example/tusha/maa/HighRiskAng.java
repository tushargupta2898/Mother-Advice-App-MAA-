package com.example.tusha.maa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class HighRiskAng extends AppCompatActivity {
    ArrayList<AngadwadiModel> userlist_ang;
    ListView momlist_ang;

    ProgressDialog progressDoalog;
    String userid_ang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_mother_list_angadwadi);

        momlist_ang = findViewById(R.id.registeredmotherlist_ang);
        userlist_ang=new ArrayList<>();
        progressDoalog = new ProgressDialog(HighRiskAng.this);
        //UserList(RegisteredMotherList.this);

        SharedPreferences preferences=getSharedPreferences("ANG_ID", Context.MODE_PRIVATE);
        userid_ang=preferences.getString("ang_user_id","");
        Log.d("ID!!!!!!!!", userid_ang);
        showList();
    }

    public void showList()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, commonapi +"highriskang.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE!!!!!", response);
                        try {
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray array = jsonObj.getJSONArray("result");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    AngadwadiModel au = new AngadwadiModel(jsonObject.getString("pre_sno"), jsonObject.getString("aadhar_number"), jsonObject.getString("mother_name"), jsonObject.getString("contact_number"));
                                    userlist_ang.add(au);
                                }
                                AngadwadiAdapter adapter = new AngadwadiAdapter(userlist_ang, getApplicationContext());
                                momlist_ang.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id_ang",userid_ang);
                Log.e("params   ", params.toString());

                return params;
            }

        };
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
