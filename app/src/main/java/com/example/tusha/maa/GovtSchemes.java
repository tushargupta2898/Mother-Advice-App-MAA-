package com.example.tusha.maa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class GovtSchemes extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_schemes);
        context = this;


        CardView card_view1 = (CardView) findViewById(R.id.scheme1);
        CardView card_view2 = (CardView) findViewById(R.id.scheme2);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context,Scheme1Activity.class);
                        startActivity(intent);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(context,Scheme2Activity.class);
                        startActivity(intent);
                    }
        });

    }
}
