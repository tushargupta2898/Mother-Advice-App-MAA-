package com.example.tusha.maa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class PlanningFront extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planningfront);
        CardView card_view1 = (CardView) findViewById(R.id.planningvid);
        CardView card_view2 = (CardView) findViewById(R.id.planninghelpline);
        ImageView imageView = findViewById(R.id.imageView5);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(PlanningFront.this, VideoTutorial.class);
                startActivity(I);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(PlanningFront.this, HelpLineActivity.class);
                startActivity(I);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(PlanningFront.this, MomProfileActivity.class);
                startActivity(I);
            }
        });
    }
}
