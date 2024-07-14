package com.example.dislocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private SessionManager sessionManager;
    EditText e1, e2, e3;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);

        sessionManager = new SessionManager(getApplicationContext());

        e1 = (EditText)findViewById(R.id.feedback_name);
        e2 = (EditText)findViewById(R.id.feedback_email);
        e3 = (EditText)findViewById(R.id.feedback_message);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("feedback");

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(Feedback.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }



        ImageView homenav = findViewById(R.id.homenav);
        homenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Home.class);
                startActivity(intent);
            }
        });

        ImageView aboutnav = findViewById(R.id.aboutnav);
        aboutnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, About.class);
                startActivity(intent);
            }
        });

        ImageView discovernav = findViewById(R.id.discovernav);
        discovernav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Discover.class);
                startActivity(intent);
            }
        });

        ImageView feedbacknav = findViewById(R.id.feedbacknav);
        feedbacknav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Feedback.class);
                startActivity(intent);
            }
        });

        ImageView profilenav = findViewById(R.id.profilenav);
        profilenav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Profile.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void createFeedback(View v){
        if(e1.getText().toString().equals("") && e2.getText().toString().equals("") && e3.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Blank not allowed", Toast.LENGTH_SHORT).show();
        }else{
            String name = e1.getText().toString();
            String email = e2.getText().toString();
            String message = e3.getText().toString();
                                String userId = mAuth.getCurrentUser().getUid();
                                Map<String, Object> feedback = new HashMap<>();
                                feedback.put("name", name);
                                feedback.put("email", email);
                                feedback.put("message", message);

                                dbRef.child(userId).setValue(feedback)
                                        .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Feedback send successfully", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error send feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                Toast.makeText(getApplicationContext(),"Feedback send succesfully",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(getApplicationContext(),Feedback.class);
                                startActivity(i);




        }
    }
}