package com.example.foodorder.SharePreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodorder.Constants.StateDownload;

public class PreferenceDownload {
    private static final String SHARE_PREFERENCE="SHARE_PREFERENCE";
    private final Context context;

    public PreferenceDownload(Context context) {
        this.context = context;
    }
    public void setValueDownload(String key, StateDownload state){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,state.ordinal());
        editor.apply();
    }
    public int getValueDownload(String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,StateDownload.FIRSTDOWLOAD.ordinal());
    }
}
