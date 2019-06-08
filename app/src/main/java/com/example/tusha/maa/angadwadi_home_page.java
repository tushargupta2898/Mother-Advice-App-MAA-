package com.example.tusha.maa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class angadwadi_home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angadwadi_home_page);

        CardView card_view1 =  findViewById(R.id.newregistration_ang);
        CardView card_view2 =  findViewById(R.id.registeredmom_ang);
        CardView card_view3 = (CardView) findViewById(R.id.highriskmom_ang);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(angadwadi_home_page.this, AadharActivity.class);
                startActivity(I);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(angadwadi_home_page.this, RegisteredMotherListAngadwadi.class);
                startActivity(I);
            }
        });
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(angadwadi_home_page.this, HighRiskAng.class);
                startActivity(I);
            }
        });
    }
}
