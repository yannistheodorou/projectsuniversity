package com.katanemimena.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Responsible for storing small key-value pairs
 */
public class LocalStorage {  //topiki apothikeysi sto kinito
    private Context context;
    private SharedPreferences pref;

    public static final String CACHE_SYSTEM_CONTROLLER_RESPONSE = "CACHE_SYSTEM_CONTROLLER_RESPONSE";
    public static final String CACHE_SAVED_MUSIC = "CACHE_SAVED_MUSIC";

    public LocalStorage(Context context) {
        this.context = context;
        final String APP_KEY = context.getPackageName();
        pref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
    }

    public void setString(String keyIdentifier, String value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(keyIdentifier, value);
        editor.apply();
    }

    public void setInt(String keyIdentifier, int value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(keyIdentifier, value);
        editor.apply();
    }

    public String getString(String keyIdentifier){
        return pref.getString(keyIdentifier, "");
    }

    public int getInt(String keyIdentifier){
        return pref.getInt(keyIdentifier, Integer.MAX_VALUE);
    }
}
