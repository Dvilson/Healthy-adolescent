package com.dvilson.healthyadolescent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    Animation topAnim;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mainActivityLaunch();

        topAnim = AnimationUtils.loadAnimation(this, R.anim.topanimation);
        mTextView = findViewById(R.id.activity_splash_tv);
        mTextView.setAnimation(topAnim);
    }

    public void mainActivityLaunch() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LogInActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkAuthenticationState() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }
    }

}