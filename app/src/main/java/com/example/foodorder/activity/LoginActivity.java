package com.example.foodorder.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.foodorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editEmail, editPassword;
    private Button buttonSignIn, buttonSignUp;
    private TextView forgotPassword;
//    private Internet internetBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        internetBroadcastReceiver = new Internet();
        initUi();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(internetBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(internetBroadcastReceiver);
    }
    private void initListener() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }

        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgotPassword();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSignIn() {
        String strEmail = editEmail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //Xử lý kết quả đăng nhập
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Vui long kiem tra lai email va password",
                                    Toast.LENGTH_SHORT).show();
                        }
//                        if (strPassword.isEmpty()){
//                            Toast.makeText(LoginActivity.this, "Vui long nhap password",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (strEmail.isEmpty()){
//                            Toast.makeText(LoginActivity.this, "Vui long nhap email",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
                    }
                });
    }

    private void onClickForgotPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editEmail.getText().toString().trim();
        if(emailAddress == null || emailAddress.toString().trim().isEmpty())
            Toast.makeText(LoginActivity.this, "Vui long nhap email!", Toast.LENGTH_SHORT).show();
        else{
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email sent!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "Email sent fail!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void initUi() {
        editEmail = findViewById(R.id.edit_gmail);
        editPassword = findViewById(R.id.edit_password);
        buttonSignIn = findViewById(R.id.btn_sign_in);
        buttonSignUp = findViewById(R.id.btn_sign_up);
        forgotPassword = findViewById(R.id.txtForgot);
    }
}