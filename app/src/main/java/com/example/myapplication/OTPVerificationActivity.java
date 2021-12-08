package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {
    private EditText input_code1, input_code2, input_code3, input_code4, input_code5, input_code6;
    private String verificationId;

    protected FirebaseAuth fAuth;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView textmobile = findViewById(R.id.textmobile);
        TextView resendOTP = findViewById(R.id.text_resendOTP);
        textmobile.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));
        input_code1 = findViewById(R.id.input_code_1);
        input_code2 = findViewById(R.id.input_code_2);
        input_code3 = findViewById(R.id.input_code_3);
        input_code4 = findViewById(R.id.input_code_4);
        input_code5 = findViewById(R.id.input_code_5);
        input_code6 = findViewById(R.id.input_code_6);
        setupOTPInputs();
        ProgressBar progressbar = findViewById(R.id.progressBar2);
        final Button Otp_verify = findViewById(R.id.buttonVerification);
        verificationId = getIntent().getStringExtra("verificationId");

        Otp_verify.setOnClickListener(v -> {
            if (input_code1.getText().toString().trim().isEmpty()
                    || input_code2.getText().toString().trim().isEmpty()
                    || input_code3.getText().toString().trim().isEmpty()
                    || input_code4.getText().toString().trim().isEmpty()
                    || input_code5.getText().toString().trim().isEmpty()
                    || input_code6.getText().toString().trim().isEmpty()) {
                Toast.makeText(OTPVerificationActivity.this, "please Enter valid code", Toast.LENGTH_SHORT).show();
                return;
            }
            String code = input_code1.getText().toString() +
                    input_code2.getText().toString() +
                    input_code3.getText().toString() +
                    input_code4.getText().toString() +
                    input_code5.getText().toString() +
                    input_code6.getText().toString();

            if (verificationId != null) {
                progressbar.setVisibility(View.VISIBLE);
                Otp_verify.setVisibility(View.INVISIBLE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                    progressbar.setVisibility(View.GONE);
                    Otp_verify.setVisibility(View.VISIBLE);
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getApplicationContext(), ActionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OTPVerificationActivity.this, "Invalid Verification code", Toast.LENGTH_SHORT).show();
                    }

                });
            }

        });
        resendOTP.setOnClickListener(v -> PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + getIntent().getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                OTPVerificationActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(OTPVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId = newVerificationId;
                        Toast.makeText(OTPVerificationActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();


                    }
                }));

    }

    public void setupOTPInputs() {
        input_code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input_code2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input_code3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input_code4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input_code5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    input_code6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

}