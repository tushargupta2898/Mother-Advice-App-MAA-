package com.example.tusha.maa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

//import butterknife.OnClick;

public class ANMRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmregistration);
        CardView card_view1 = (CardView) findViewById(R.id.newregistration);
        CardView card_view2 = (CardView) findViewById(R.id.registeredmom);
        CardView card_view3 = (CardView) findViewById(R.id.highriskmom);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ANMRegistration.this, AadharActivity.class);
                startActivity(I);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ANMRegistration.this, RegisteredMotherList.class);
                startActivity(I);
            }
        });
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ANMRegistration.this, HighRiskAnm.class);
                startActivity(I);
            }
        });
    }
}
