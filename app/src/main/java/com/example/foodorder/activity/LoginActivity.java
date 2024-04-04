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
import com.example.foodorder.utils.Apputil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editEmail, editPassword;
    private Button buttonSignIn, buttonSignUp;
    private TextView forgotPassword;
    private Apputil internetBroadcastReceiver;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        internetBroadcastReceiver = new Apputil();
        mAuth = FirebaseAuth.getInstance();
        initUi();
        initListener();
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
    private void initListener() { //Thiết lập lắng nghe sự kiện
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
        auth.signInWithEmailAndPassword(strEmail, strPassword) //tra ve doi tuong task
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //Xử lý kết quả đăng nhập
                        //phương thức này nhận một đối tượng Task<AuthResult> làm tham số. Đối tượng này chứa kết quả của tác vụ đăng nhập
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại email và password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }



                });
    }

    private void onClickForgotPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editEmail.getText().toString().trim();
        if(emailAddress == null || emailAddress.toString().trim().isEmpty())
            Toast.makeText(LoginActivity.this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
        else{
            auth.sendPasswordResetEmail(emailAddress) //Gửi email đặt lại mật khẩu đến địa chỉ emailAddress. Phương thức này cũng trả về một Task

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email đã được gửi!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "Email gửi thất bại!", Toast.LENGTH_SHORT).show();
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