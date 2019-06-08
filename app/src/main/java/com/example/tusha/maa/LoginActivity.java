package com.example.tusha.maa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity{

    EditText username,password;
    TextView textViewsignup;

    ProgressBar progressBar;
    TextView btnlogin;
//    private  FirebaseAuth.AuthStateListener authStateListener;
//    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.phonenum) ;
        password = (EditText)findViewById(R.id.passwordlogin) ;
        btnlogin = (TextView)findViewById(R.id.login) ;
        textViewsignup = (TextView)findViewById(R.id.textViewsignup) ;
//        firebaseAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

/*

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(I);
                } else {
                    Toast.makeText(LoginActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
        textViewsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent I = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(I);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addString();
                String userEmail = username.getText().toString();
                String userPaswd = password.getText().toString();
                if (userEmail.isEmpty()) {
                    username.setError("Provide your Email first!");
                    username.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    password.setError("Enter Password!");
                    password.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, WhoYouAreActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addString(){

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();



        if (TextUtils.isEmpty(username.getText())){
            Toast.makeText(this,"you should enter a username",Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password.getText())){
            Toast.makeText(this,"you should enter a password",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_LONG).show();
            username.requestFocus();
            return;
        }
        if(pass.length()<6){
            Toast.makeText(this,"minimum length of password should be 6",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        if(pass.length()>20){
            Toast.makeText(this,"maximum length of password should be 20",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);*/
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.next:
                            Intent i=new Intent(LoginActivity.this,MomTypeActivity.class);
                            startActivity(i);

                            break;
                        case R.id.emergency:
                            //  makephonecall();
                            break;
                        case R.id.text_to_speech:
                            //  speakOut();
                            break;

                    }
                    return true;
                }

            };
}
