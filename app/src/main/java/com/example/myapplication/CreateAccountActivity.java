package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    EditText username, user_email, user_number, user_password;
    TextView sign_up, A_sign_in;
    FirebaseAuth fAuth;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.uname);
        user_email = findViewById(R.id.user_email);
        user_number = findViewById(R.id.phone_number);
        user_password = findViewById(R.id.user_password);
        sign_up = findViewById(R.id.sign_up);
        A_sign_in = findViewById(R.id.signActivity);
        pb = findViewById(R.id.progressBar1);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ActionActivity.class));
            finish();
        }
        sign_up.setOnClickListener(view -> {
            String name = username.getText().toString().trim();
            String number = user_number.getText().toString().trim();
            String email = user_email.getText().toString().trim();
            String password = user_password.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                username.setError("Please Enter the Name");
                return;
            } else if (TextUtils.isEmpty(number)) {
                user_number.setError("Please Enter the Number");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                user_email.setError("email is required");
                return;
            }
            if (password.length() < 8) {
                user_password.setError("password must be 8 characters");
                return;
            }
            pb.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "user Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ActionActivity.class));
                } else {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(CreateAccountActivity.this, "Error this!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });
        A_sign_in.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });


    }


}
