package com.example.capstoneproject;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.capstoneproject.Listeners.RandomAPIResponseListener;
import com.example.capstoneproject.Models.RandomRecipe;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {
    private SwipeDeck cardStack;
    private RequestManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        manager = new RequestManager(this);
        manager.GetRandomRecipes(listener, new ArrayList<>());
        cardStack = findViewById(R.id.swipe_deck);
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(SwipeActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                Toast.makeText(SwipeActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                Toast.makeText(SwipeActivity.this, "No more courses present", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                Log.i("TAG", "CARDS MOVED UP");
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.navbar_fragment_id, NavBarFragment.class, null)
                    .commit();
        }
    }
    private final RandomAPIResponseListener listener = new RandomAPIResponseListener() {
        @Override
        public void didFetch(List<RandomRecipe> responses, String message) {
            final DeckAdapter adapter = new DeckAdapter(responses, SwipeActivity.this);
            cardStack.setAdapter(adapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SwipeActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
