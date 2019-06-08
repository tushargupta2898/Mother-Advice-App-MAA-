package com.example.tusha.maa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class HelpLineActivity extends AppCompatActivity {
    private static final int Request_call=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_line);

        CardView card_view1 = (CardView) findViewById(R.id.doctorhelpline1);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             makephonecall();
            }
        });
    }

    private void makephonecall(){
        String s="7999199819";
        if(s.trim().length()>0){
            if(ContextCompat.checkSelfPermission(HelpLineActivity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(HelpLineActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},Request_call);
            }else {
                String dial="tel:"+s;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else{
            Toast.makeText(HelpLineActivity.this,"Enter phonr number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestcode==Request_call){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
