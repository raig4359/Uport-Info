package com.raig.uportinfo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gaurav on 23-01-2018.
 */

public class SharedPrefUtils {

    private final static String preference_file_key = "com.uport_info.uport_preference";
    private final static String IS_LOGGED_IN = "is_logged_in";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_LOGGED_IN, false);
    }

    public static void saveLoginState(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }

    public static void logOut(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.apply();
    }
}
