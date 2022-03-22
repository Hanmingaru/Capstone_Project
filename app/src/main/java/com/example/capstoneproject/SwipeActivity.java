package com.example.capstoneproject;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class SwipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        Button btn = (Button)findViewById(R.id.saved_button);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SwipeActivity.this, SavedActivity.class));
            }
        });
        Button btn2 = (Button)findViewById(R.id.grocery_button);

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SwipeActivity.this, GroceryActivity.class));
            }
        });
    }
}