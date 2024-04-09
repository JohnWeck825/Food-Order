package com.example.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.Model.User;
import com.example.foodorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText editEmail, editPassword, Editname; //bien luu tru
    private Button buttonSignUp, buttonSignIn;
    FirebaseAuth mAuth;
    //    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference accountRef = database.getReference("Account");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initUi();
        initListener();
    }

    private void initListener() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return Pattern.matches(regex, email);
    }
    private boolean isStrongPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 6 &&
                Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$", password);
    }
    private void onClickSignUp() {
//        DatabaseReference newAccountRef = accountRef.push();
        String strEmail = editEmail.getText().toString().trim(); //Lấy dữ liệu từ các trường nhập liệu
        String strPassword = editPassword.getText().toString().trim(); //Loại bỏ khoảng trắng đầu và cuối bằng
        String strEditname = Editname.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance(); //Sử dụng Firebase Authentication để tạo tài khoản mới
        auth.createUserWithEmailAndPassword(strEmail, strPassword) //tra ve doi tuong Task
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User user = new User( strEmail, "", strPassword, strEditname);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("Account");
                            databaseReference.child(mAuth.getUid()).setValue(user);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            if (!isValidEmail(strEmail)) {
                                Toast.makeText(RegisterActivity.this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();;
                            }
                            if (!isStrongPassword(strPassword)) {
                                Toast.makeText(RegisterActivity.this, "Mật khẩu phải dài 6 kí tự", Toast.LENGTH_SHORT).show();;
                            }
                        }
                    }
                });
    }
    private void initUi(){ //khởi tạo các thành phần giao diện bằng cách tìm ID của chúng trong layout
        editEmail = findViewById(R.id.edit_gmail);
        editPassword = findViewById(R.id.edit_password);
        buttonSignUp = findViewById(R.id.signup);
        buttonSignIn = findViewById(R.id.login);
        Editname = findViewById(R.id.staff_name);
    }
}