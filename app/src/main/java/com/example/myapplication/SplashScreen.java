package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    //LottieAnimationView animationView;
    TextView name;
    ImageView icon;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah_screen);
        name = findViewById(R.id.app_name);
        icon = findViewById(R.id.splash_icon);
        icon.animate().translationY(4000).setStartDelay(3000);
        name.animate().translationY(5000).setStartDelay(2000);
       // animationView = findViewById(R.id.animationView);
       new Handler().postDelayed(() -> {
           Intent intent = new Intent(SplashScreen.this,MainActivity.class);
           startActivity(intent);
           finish();
       },5*1000);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
        }
        else
        {
            Toast backToast = Toast.makeText(SplashScreen.this, "press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

    }
}