package com.example.dislocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);

        String[] gridItems = {"Item 1", "Item 2", "Item 3", "Item 4"};

        ImageAdapter gridAdapter = new ImageAdapter(this, gridItems);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(Home.this, About.class);
                    break;
                case 1:
                    intent = new Intent(Home.this, Discover.class);
                    break;
                case 2:
                    intent = new Intent(Home.this, Feedback.class);
                    break;
                case 3:
                    intent = new Intent(Home.this, Profile.class);
                    break;
                default:
                    intent = new Intent(Home.this, Home.class);
                    break;
            }
            startActivity(intent);
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}