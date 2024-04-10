package com.example.foodorder.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.Constants.StateDownload;
import com.example.foodorder.R;
import com.example.foodorder.SharePreference.PreferenceDownload;
import com.example.foodorder.utils.Apputil;

public class SplashActivity extends AppCompatActivity {
    PreferenceDownload preferences;
    private Apputil internetBroadcastReceiver;
    private static final String KEY="DOWNLOAD_CHECK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loaddata();
        internetBroadcastReceiver = new Apputil();
    }
    @Override
    protected void onStart() { //Đăng ký bộ thu phát internet khi bắt đầu hoạt động
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() { // Hủy đăng ký bộ thu phát internet khi hủy hoạt động
        super.onDestroy();
        unregisterReceiver(internetBroadcastReceiver);
    }
    private void loaddata() {
        preferences=new PreferenceDownload(this);
        //luu trang thai nguoi dung o SharePreference
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getValueDownload(KEY) == StateDownload.FIRSTDOWLOAD.ordinal()){
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

    }
}