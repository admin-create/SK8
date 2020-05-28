package com.teenbande.atom_things.atom_things;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AwesomeSpeedState {
    private static final String TAG = GattServer.class.getSimpleName();
    private static final String PREFS_NAME = "awesomeness";
    private static final String PREFS_KEY_SPEED = "level";

    private final SharedPreferences mPrefs;

    public AwesomeSpeedState(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getSpeedValue() {
        return mPrefs.getInt(PREFS_KEY_SPEED, 0);
    }

    @SuppressLint("ApplySharedPref")
    public int incrementSpeedValue(int Wspeed) {
//      int newValue = getSpeedValue()+ 1;
        Log.d(TAG, "incrementSpeedValue: " + Wspeed);
        mPrefs.edit().putInt(PREFS_KEY_SPEED, Wspeed).commit();
        return Wspeed;
    }
}
