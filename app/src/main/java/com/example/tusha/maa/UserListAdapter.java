package com.example.tusha.maa;

import android.content.Context;
import android.content.Intent;
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

public class UserListAdapter extends ArrayAdapter<UserModel> {
    private List<UserModel> anm_list;
    private Context mContext;

    public UserListAdapter( ArrayList<UserModel> catList ,Context context) {
        super(context,R.layout.customlayout,catList);
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
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.customlayout,null,true);

        TextView textView_name = view.findViewById(R.id.momname);
        TextView textView_mobile = view.findViewById(R.id.momnumber);
        TextView textView_aadhar = view.findViewById(R.id.momaadhar);
        TextView textView_anganwadi = view.findViewById(R.id.momanganwadi);


        textView_name.setText(anm_list.get(position).getMother_name());
        textView_mobile.setText(anm_list.get(position).getContact_number());
        textView_aadhar.setText(anm_list.get(position).getAadhar_number());
        textView_anganwadi.setText(anm_list.get(position).getArea_name());

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