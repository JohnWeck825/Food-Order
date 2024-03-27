package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.foodorder.Login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loaddata();
    }

    private void loaddata() {
        if(Apputil.isNewwordAvailable(this)){
            //netword connected;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent it=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            },2000);
        }
        else{
            Toast.makeText(SplashActivity.this, "Network disconected!", Toast.LENGTH_SHORT).show();
        }
    }
}