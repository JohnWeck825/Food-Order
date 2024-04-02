package com.example.foodorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.foodorder.Constants.StateDownload;
import com.example.foodorder.SharePreference.PreferenceDownload;
import com.example.foodorder.utils.Apputil;
import com.example.foodorder.R;

public class SplashActivity extends AppCompatActivity {
    PreferenceDownload preferences;
    private String KEY="DOWLOAD_CHECK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loaddata();
    }

    private void loaddata() {
        preferences=new PreferenceDownload(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getValueDownload(KEY)== StateDownload.FIRSTDOWLOAD.ordinal()){
                    preferences.setValueDownload(KEY,StateDownload.DOWLOADED);
                    Intent it=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(it);
                    finish();
                }
                else{
                    Intent it=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        },1000);
//        if(Apputil.isNetworkAvailable(this)){
//            netword connected;
//
//        }
//        else{
//            Toast.makeText(SplashActivity.this, "Network disconected!", Toast.LENGTH_SHORT).show();
//        }
    }
}