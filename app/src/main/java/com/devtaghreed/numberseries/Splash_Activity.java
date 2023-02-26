package com.devtaghreed.numberseries;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.devtaghreed.numberseries.databinding.ActivitySplashBinding;
import com.devtaghreed.numberseries.databinding.ActivitySplashBinding;

public class Splash_Activity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), Login_Activity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
