package com.example.foodorder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.foodorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtuser,edtpass;
    Button btnLogin;
    ToggleButton toggleEye;
    TextView txtCreatAccount,txtForgot;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        MapId();
        SetListener();
    }
    private void MapId(){
        edtuser=findViewById(R.id.edtUsername);
        edtpass=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        toggleEye=findViewById(R.id.toggleEye);
        txtCreatAccount=findViewById(R.id.txtCreatAcc);
        txtForgot=findViewById(R.id.txtForgot);
    }
    private void SetListener(){
        toggleEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toggleEye.setBackgroundResource(R.drawable.eyedash);
                    edtpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }
                else{
                    toggleEye.setBackgroundResource(R.drawable.eye);
                    edtpass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        txtCreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itOpenRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(itOpenRegister);
            }

        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidate();
            }
        });
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itOpenForgotpass=new Intent(LoginActivity.this,ForgotpassActivity.class);
                startActivity(itOpenForgotpass);
            }
        });

    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    void CheckValidate(){
        boolean isValid = true;
        if (isEmpty(edtuser)) {
            edtuser.setError("You must enter username to login!");
            isValid = false;
        } else {
            if (!isEmail(edtuser)) {
                edtuser.setError("Enter valid email!");
                isValid = false;
            }
        }

        if (isEmpty(edtpass)) {
            edtpass.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (edtpass.getText().toString().length() < 4) {
                edtpass.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        //check email and password
        //IMPORTANT: here should be call to backend or safer function for local check; For example simple check is cool
        //For example simple check is cool
        if (isValid) {
            String usernameValue = edtuser.getText().toString();
            String passwordValue = edtpass.getText().toString();

            mAuth.signInWithEmailAndPassword(usernameValue, passwordValue)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Authentication succesfull.",
                                        Toast.LENGTH_SHORT).show();
                                Intent it =new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}