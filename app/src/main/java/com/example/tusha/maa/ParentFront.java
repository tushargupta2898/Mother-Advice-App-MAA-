package com.example.tusha.maa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class ParentFront extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentfront);
        CardView card_view1 = (CardView) findViewById(R.id.parentvid);
        CardView card_view2 = (CardView) findViewById(R.id.parenthelpline);
        CardView card_view3 = (CardView) findViewById(R.id.parentscheme);
        CardView card_view4 = (CardView) findViewById(R.id.parentdiet);
        ImageView imageView = findViewById(R.id.imageView5);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ParentFront.this, VideoTutorial.class);
                startActivity(I);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ParentFront.this, HelpLineActivity.class);
                startActivity(I);
            }
        });
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ParentFront.this, GovtSchemes.class);
                startActivity(I);
            }
        });
        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ParentFront.this, DietPlanActivity.class);
                startActivity(I);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(ParentFront.this, MomProfileActivity.class);
                startActivity(I);
            }
        });
    }
}

