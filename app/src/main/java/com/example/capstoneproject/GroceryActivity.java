package com.example.capstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class GroceryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        Button btn = (Button)findViewById(R.id.saved_button2);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GroceryActivity.this, SavedActivity.class));
            }
        });
        Button btn2 = (Button)findViewById(R.id.swipe_button);

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GroceryActivity.this, SwipeActivity.class));
            }
        });
    }
}