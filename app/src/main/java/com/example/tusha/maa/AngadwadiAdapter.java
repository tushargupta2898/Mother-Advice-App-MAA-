package com.example.tusha.maa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06-08-2018.*
 */

public class AngadwadiAdapter extends ArrayAdapter<AngadwadiModel> {
    private List<AngadwadiModel> ang_list;
    private Context mContext;

    public AngadwadiAdapter(ArrayList<AngadwadiModel> catList , Context context) {
        super(context,R.layout.custom_layout_angadwadi,catList);
        this.mContext = context;
        this.ang_list = catList;
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
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.custom_layout_angadwadi,null,true);

        TextView textView_name = view.findViewById(R.id.momname);
        TextView textView_mobile = view.findViewById(R.id.momnumber);
        TextView textView_aadhar = view.findViewById(R.id.momaadhar);

        textView_name.setText(ang_list.get(position).getMother_name());
        textView_mobile.setText(ang_list.get(position).getContact_number());
        textView_aadhar.setText(ang_list.get(position).getAadhar_number());


        //textView_timein.setText("IN "+user_List.get(position).getIntime());

//        if(user_List.get(position).getOuttime()!= null && user_List.get(position).getOuttime().equals("")){
//            textView_timeout.setVisibility(View.GONE);
//        }else {
//            textView_timeout.setText("OUT "+user_List.get(position).getOuttime());
//        }
//        if(!user_List.get(position).getImage().equals("")) {
//            Picasso.with(mContext)
//                    .load(user_List.get(position).getImage())
//                    .placeholder(R.drawable.ic_assignment_black_24dp)   // optional
//                    .error(R.drawable.ic_assignment_black_24dp)      // optional
//                    .resize(400, 400)                        // optional
//                    .into(image);
//        }

        return view;
    }
}