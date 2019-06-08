package com.example.tusha.maa;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Handler {
    public static Handler mInst;
    private RequestQueue requestQueue;
    private static Context mcontext;


    private Handler(Context context) {
        mcontext = context;
        requestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue= Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return  requestQueue;
    }
    public static synchronized Handler getInstance(Context context){
        if (mInst==null){
            mInst=new Handler(context);
        }
        return mInst;
    }
    public <T>void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}