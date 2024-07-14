package com.example.dislocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Discover extends AppCompatActivity {

    private static final int SCANNER_REQUEST_CODE = 123;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_discover);

        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(Discover.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        ImageView locationicon = findViewById(R.id.locationicon);
        locationicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, LocationMap.class);
                startActivity(intent);
            }
        });

        ImageView qricon = findViewById(R.id.qricon);
        qricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, QR.class);
                startActivity(intent);
            }
        });

        ImageView homenav = findViewById(R.id.homenav);
        homenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, Home.class);
                startActivity(intent);
            }
        });

        ImageView aboutnav = findViewById(R.id.aboutnav);
        aboutnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, About.class);
                startActivity(intent);
            }
        });

        ImageView discovernav = findViewById(R.id.discovernav);
        discovernav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, Discover.class);
                startActivity(intent);
            }
        });

        ImageView feedbacknav = findViewById(R.id.feedbacknav);
        feedbacknav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, Feedback.class);
                startActivity(intent);
            }
        });

        ImageView profilenav = findViewById(R.id.profilenav);
        profilenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, Profile.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.qricon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Discover.this, QR.class);
                startActivityForResult(intent, SCANNER_REQUEST_CODE);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String qrCode = data.getStringExtra("qrCode");
                // Handle the QR code result
            }
        }
    }
}