/*
 * Created by Elliott Rheault on 2022.4.29
 * Copyright © 2022 Elliott Rheault. All rights reserved.
 */

package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, SwipeActivity.class));
                finish();
            }
        }, 2000);



    }
}