package com.example.tusha.maa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class DietPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan);
    }
    public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();
        String lang = "en";
        //now check which radio button is selected
        //android switch statement
        switch (v.getId()) {
            case R.id.planning:
                if (checked)
                startActivity( new Intent(DietPlanActivity.this, FirstTrimester.class));
                    break;

            case R.id.pregnant:
                if (checked)
                    startActivity( new Intent(DietPlanActivity.this, SecondTrimester.class));
                break;
            case R.id.parent:
                if (checked)
                    startActivity( new Intent(DietPlanActivity.this, ThirdTrimester.class));;
                break;
        }
    }
}
