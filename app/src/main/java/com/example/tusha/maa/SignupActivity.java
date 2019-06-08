package com.example.tusha.maa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;


import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SignupActivity  extends AppCompatActivity implements View.OnClickListener {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText edtPhoneNumber;
    private EditText edtOtp;
    private String mVerificationId;
    private String code;
    private CountDownTimer Count;


    private String phNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        ButterKnife.bind(this);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Firebase.setAndroidContext(this);
        edtPhoneNumber =findViewById(R.id.activity_login_sign_up_edt_ph_number);
        edtOtp = findViewById(R.id.activity_login_sign_up_edt_otp);
        final CardView btnSignIn =findViewById(R.id.activity_log_in_sign_up_btn_login);
        final CardView btnVerifyOtp =findViewById(R.id.activity_log_in_sign_up_btn_verify_otp);



        btnSignIn.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
//                Log.d(TAG, "onVerificationCompleted:" + credential);
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
//                Log.w(TAG, "onVerificationFailed", e);
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Log.d("Invalid request", "Invalid request");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
//                    Log.d("Error", "The SMS quota for the project has been exceeded");
                    Toast.makeText(SignupActivity.this, getString(R.string.sms_qutoa_exceeded_alert), Toast.LENGTH_SHORT).show();
//                    llPhNumberLayout.setVisibility(View.GONE);
//                    llOTPNumberLayout.setVisibility(View.VISIBLE);
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

                code = edtOtp.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(SignupActivity.this, getString(R.string.otp_blank_alert), Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(s, code);
                    signInWithPhoneAuthCredential(credential);
                }
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
                mVerificationId = verificationId;
                final String mResendToken = token.toString();
                startTimer();
                Toast.makeText(SignupActivity.this, getString(R.string.otp_send_message), Toast.LENGTH_SHORT).show();
                // ...
            }
        };
    }

    @Override
    public void onClick(View v) {
        hideKeyBoard();
        switch (v.getId()) {
            case R.id.activity_log_in_sign_up_btn_login:
                if (TextUtils.isEmpty(edtPhoneNumber.getText().toString().trim())) {
                    Toast.makeText(this, getString(R.string.ph_number_empty_alert), Toast.LENGTH_SHORT).show();
                } else {
                    phNumber = "+91" + edtPhoneNumber.getText().toString().trim();
                    fireBasePhLogin("+91" + edtPhoneNumber.getText().toString().trim());
                }
                break;

            case R.id.activity_log_in_sign_up_btn_verify_otp:

                if (TextUtils.isEmpty(edtOtp.getText().toString().trim())) {
                    Toast.makeText(this, getString(R.string.otp_blank_alert), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = showProgressDialog(this, getString(R.string.please_wait), false);
                    code = edtOtp.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                    finish();
                    Intent i = new Intent(SignupActivity.this, MomTypeActivity.class);
                    startActivity(i);

                    SharedPreferences prefs_2= getSharedPreferences("PHONE_NUM", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs_2.edit();
                    editor.putString("phone_number",phNumber);
                    editor.apply();
                }
                break;

        }
    }

    /**
     * This method is for the login with ph number using firebase
     */
    private void fireBasePhLogin(final String phoneNumber) {
        progressDialog = showProgressDialog(this, getString(R.string.please_wait), false);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Log.d("", "onComplete: " + user);
//                            Toast.makeText(MainActivity.this, "Ph number verified", Toast.LENGTH_SHORT).show();
                            stopTimer();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignupActivity.this, getString(R.string.wrong_verification_code_alert), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    //this method is to show the count down timer for OTP


    //this method is to start timer
    private void startTimer() {
        if (Count != null) {
            Count.start();
        }
    }

    //this method is to stop timer
    private void stopTimer() {
        if (Count != null) {
            Count.cancel();
        }
    }

    //this method is to hide keyboard
    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Method to show progress dialog
     *
     * @param mActivity
     * @param message
     * @param isCancelable
     * @return dialog
     */
    public ProgressDialog showProgressDialog(final Context mActivity, final String message, boolean isCancelable) {
        final ProgressDialog mDialog = new ProgressDialog(mActivity);
        mDialog.show();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        return mDialog;
    }

    /**
     * Method to dismiss progress dialog
     *
     * @param progressDialog
     */
    public final void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
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

