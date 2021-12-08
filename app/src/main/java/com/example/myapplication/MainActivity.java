package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView sign_up, forgot_password, otp;
    EditText email_input, password1;
    Button login;
    FirebaseAuth fAuth;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//identifying the buttons and textview
        sign_up = findViewById(R.id.signup);
        email_input = findViewById(R.id.username_input);
        password1 = findViewById(R.id.password_input);
        login = findViewById(R.id.sign_in);
        otp = findViewById(R.id.otp);
        forgot_password = findViewById(R.id.forgot_password);

        fAuth = FirebaseAuth.getInstance();

        CustomProgressDialog dialog = new CustomProgressDialog(MainActivity.this);

//login button operation with firebase authentication
        login.setOnClickListener(view -> {
            String email = email_input.getText().toString().trim();
            String password = password1.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                email_input.setError("email is required");
                return;
            }
            if (password.length() < 8) {
                password1.setError("password must be 8 characters");
                return;
            }
            dialog.show();
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ActionActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Error this!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    // p.setVisibility(View.INVISIBLE);
                }
                dialog.hide();
            });
        });

//sign up operation block
        sign_up.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
            // p.setVisibility(View.VISIBLE);
        });

//forgot password block code
        forgot_password.setOnClickListener(view -> {
            EditText reset_email = new EditText(view.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter the Email to Receive the Link");
            passwordResetDialog.setView(reset_email);


//reset password dialog block code
            passwordResetDialog.setPositiveButton("yes", (dialogInterface, i) -> {
                String mail = reset_email.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Reset LInk send to the mail", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "error..!" + e.getMessage(), Toast.LENGTH_SHORT).show());

            });
            passwordResetDialog.setNegativeButton("No", (dialogInterface, i) -> {

            });
            passwordResetDialog.create().show();

        });

//otp authentication block code
        otp.setOnClickListener(view -> {
            dialog.show();
            Intent otp = new Intent(view.getContext(), LoginWithOTPActivity.class);
            startActivity(otp);
            dialog.hide();

        });

    }

}
