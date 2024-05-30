package com.example.fastishaapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String session = "userSession";
    private static final String logged_in = "isLoggedIn";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    public  SessionManager(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(session, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(logged_in, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(logged_in, false);
    }
}
