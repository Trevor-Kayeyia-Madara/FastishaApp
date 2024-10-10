package com.example.fastishaapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String session = "userSession";
    private static final String logged_in = "isLoggedIn";
    private static  final String Key_is_admin = "isAdmin";
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
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(logged_in, false);
    }

    public void setAdmin(boolean isAdmin){
        editor.putBoolean(Key_is_admin, isAdmin);
        editor.commit();
    }

    public boolean isAdmin(){
        return pref.getBoolean(Key_is_admin, false);
    }
}