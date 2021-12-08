package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginWithOTPActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_otpactivity);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final EditText input_mbl = findViewById(R.id.mobile_number);
        final ProgressBar progressbar = findViewById(R.id.progressBar);
        Button OTP_button = findViewById(R.id.buttonGetOTP);
        fAuth = FirebaseAuth.getInstance();


        OTP_button.setOnClickListener(v -> {
            if (input_mbl.getText().toString().trim().isEmpty()) {
                Toast.makeText(LoginWithOTPActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                return;
            }
            progressbar.setVisibility(View.VISIBLE);
            OTP_button.setVisibility(View.INVISIBLE);

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + input_mbl.getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    LoginWithOTPActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            progressbar.setVisibility(View.GONE);
                            OTP_button.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressbar.setVisibility(View.GONE);
                            OTP_button.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginWithOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            progressbar.setVisibility(View.GONE);
                            OTP_button.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(getApplicationContext(), OTPVerificationActivity.class);
                            intent.putExtra("mobile", input_mbl.getText().toString());
                            intent.putExtra("verificationId", VerificationId);
                            startActivity(intent);


                        }
                    });

        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(LoginWithOTPActivity.this, "press above corner back button", Toast.LENGTH_SHORT).show();
    }
}