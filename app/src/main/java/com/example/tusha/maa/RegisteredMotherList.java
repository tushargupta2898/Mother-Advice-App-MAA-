//package com.example.tusha.maa;
//
//import android.app.ProgressDialog;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.TextView;
//
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.example.tusha.maa.CommonActivity.commonapi;
//
//public class RegisteredMotherList extends AppCompatActivity {
//
//    ArrayList<UserModel> userlist;
//    ListView momlist;
//
//    private List<UserModel> anm_list;
//    private Context mContext;
//
//    ProgressDialog progressDoalog;
//    String userid;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registered_mother_list);
//
//        momlist = findViewById(R.id.registeredmotherlist);
//        userlist=new ArrayList<>();
//        progressDoalog = new ProgressDialog(RegisteredMotherList.this);
//
//        SharedPreferences preferences=getSharedPreferences("ANM_ID", Context.MODE_PRIVATE);
//        userid=preferences.getString("anm_user_id","");
//        Log.d("ID!!!!!!!!", userid);
//        showList();
//    }
//
//
//    public void showList()
//    {
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, commonapi +"filter.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("RESPONSE!!!!!", response);
//                        try {
//                            try {
//                                JSONObject jsonObj = new JSONObject(response);
//                                JSONArray array = jsonObj.getJSONArray("result");
//                                for (int i = 0; i < array.length(); i++) {
//                                    JSONObject jsonObject = array.getJSONObject(i);
//                                    UserModel u = new UserModel(jsonObject.getString("pre_sno"), jsonObject.getString("aadhar_number"), jsonObject.getString("mother_name"), jsonObject.getString("contact_number"), jsonObject.getString("area_name"));
//                                    userlist.add(u);
//                                }
//                                UserListAdapter adapter = new UserListAdapter(userlist, getApplicationContext());
//                                momlist.setAdapter(adapter);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (NullPointerException e) {
//                                e.printStackTrace();
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        if (progressDoalog.isShowing()) {
//                            progressDoalog.dismiss();
//                        }
//                    }
//                }){
//            @Override
//            protected Map<String,String> getParams()
//            {
//                ///Intent i=getIntent();
//                //userid=i.getStringExtra("anmuserid");
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id",userid);
//                Log.e("params   ", params.toString());
//
//                return params;
//            }
//
//        };
//        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }
//
//
//    public UserListAdapter( ArrayList<UserModel> catList ,Context context) extends ArrayList{
//        super(context,R.layout.customlayout,catList);
//        this.mContext = context;
//        this.anm_list = catList;
//    }
//
//    public View getView(final int position, View view, ViewGroup viewGroup)
//    {
//        LayoutInflater layoutInflater=LayoutInflater.from(this);
//        view = layoutInflater.inflate(R.layout.customlayout,null,true);
//
//        TextView textView_name = view.findViewById(R.id.momname);
//        TextView textView_mobile = view.findViewById(R.id.momnumber);
//        TextView textView_aadhar = view.findViewById(R.id.momaadhar);
//        TextView textView_anganwadi = view.findViewById(R.id.momanganwadi);
//
//
//        textView_name.setText(anm_list.get(position).getMother_name());
//        textView_mobile.setText(anm_list.get(position).getContact_number());
//        textView_aadhar.setText(anm_list.get(position).getAadhar_number());
//        textView_anganwadi.setText(anm_list.get(position).getArea_name());
//
//
//        return view;
//    }
//
//
//
//}


package com.example.tusha.maa;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tusha.maa.CommonActivity.commonapi;

public class RegisteredMotherList extends AppCompatActivity {

    ArrayList<UserModel> userlist;
    ListView momlist;


    ProgressDialog progressDoalog;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_mother_list);

        momlist = findViewById(R.id.registeredmotherlist);
        userlist = new ArrayList<>();
        progressDoalog = new ProgressDialog(RegisteredMotherList.this);



        SharedPreferences preferences = getSharedPreferences("ANM_ID", Context.MODE_PRIVATE);
        userid = preferences.getString("anm_user_id", "");
        Log.d("ID!!!!!!!!", userid);
        showList();
    }


    public void showList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, commonapi + "filter.php",
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
                                    UserModel u = new UserModel(jsonObject.getString("pre_sno"), jsonObject.getString("aadhar_number"), jsonObject.getString("mother_name"), jsonObject.getString("contact_number"), jsonObject.getString("area_name"));
                                    userlist.add(u);
                                }
                                UserListAdapter adapter = new UserListAdapter(userlist, getApplicationContext());
                                momlist.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                ///Intent i=getIntent();
                //userid=i.getStringExtra("anmuserid");
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                Log.e("params   ", params.toString());

                return params;
            }

        };
        Handler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }


    class UserListAdapter extends ArrayAdapter<UserModel> {
        private List<UserModel> anm_list;
        private Context mContext;
        Button verify_form,checkup_form;


        public UserListAdapter(ArrayList<UserModel> catList, Context context) {
            super(context, R.layout.customlayout, catList);
            this.mContext = context;
            this.anm_list = catList;

        }

//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return user_List.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return 0;
//    }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.customlayout, null, true);

            TextView textView_name = view.findViewById(R.id.momname);
            TextView textView_mobile = view.findViewById(R.id.momnumber);
            TextView textView_aadhar = view.findViewById(R.id.momaadhar);
            TextView textView_anganwadi = view.findViewById(R.id.momanganwadi);


            textView_name.setText(anm_list.get(position).getMother_name());
            textView_mobile.setText(anm_list.get(position).getContact_number());
            textView_aadhar.setText(anm_list.get(position).getAadhar_number());
            String aadhar = textView_aadhar.getText().toString();
            SharedPreferences prefs= getSharedPreferences("Verify_Aadhar", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("preg_verify_aadhar",aadhar);
            editor.apply();
            textView_anganwadi.setText(anm_list.get(position).getArea_name());


            verify_form=view.findViewById(R.id.verfyformbutton);
            checkup_form=view.findViewById(R.id.checkupbutton);

            verify_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent verify_next=new Intent(RegisteredMotherList.this,Verify1.class);
                    startActivity(verify_next);
                }
            });

            checkup_form.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent checkup_next=new Intent(RegisteredMotherList.this,HealthCheckup.class);
                    startActivity(checkup_next);
                }
            });






//        Button verfyformbutton = view.findViewById(R.id.verfyformbutton);
//        Button checkupbutton = view.findViewById(R.id.checkupbutton);
//        verfyformbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),HealthCheckup.class);
//                view.getContext().startActivity(intent);}
//        });
//        checkupbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),HealthCheckup.class);
//                view.getContext().startActivity(intent);}
//        });

            return view;
        }




    }


}

