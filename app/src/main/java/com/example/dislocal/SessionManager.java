package com.example.dislocal;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SessionManager {
    private static final String PREF_NAME = "SessionPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private FirebaseAuth mAuth;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        mAuth = FirebaseAuth.getInstance();
    }

    public void createLoginSession() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null && pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public void logoutUser() {
        mAuth.signOut();
        editor.clear();
        editor.commit();
    }
}
