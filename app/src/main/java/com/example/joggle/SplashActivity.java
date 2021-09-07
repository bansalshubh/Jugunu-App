package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    ImageView notes;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        notes=(ImageView)findViewById(R.id.notes);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null) {
                    Intent i = new Intent(SplashActivity.this,
                            MainActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.
                    finish();
                }
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}