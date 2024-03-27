package com.example.foodorder.Login;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail,edtName,edtAddress,edtPass,edtPhone;
    TextView txtHaveacc;
    Button btnRegister;
    ToggleButton toggleEye;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MapId();
        Setlistener();
    }
    void MapId(){
        edtEmail=findViewById(R.id.edtEmail);
        edtName=findViewById(R.id.edtName);
        edtAddress=findViewById(R.id.edtAddress);
        edtPass=findViewById(R.id.edtPassword);
        edtPhone=findViewById(R.id.edtPhone);
        txtHaveacc=findViewById(R.id.txtHaveAcc);
        btnRegister=findViewById(R.id.btnRegister);
        toggleEye=findViewById(R.id.toggleEye);
        mAuth = FirebaseAuth.getInstance();
    }
    void Setlistener(){
        toggleEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toggleEye.setBackgroundResource(R.drawable.eyedash);
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }
                else{
                    toggleEye.setBackgroundResource(R.drawable.eye);
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        txtHaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itOpenRegister=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(itOpenRegister);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidate();
            }
        });
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    private void CheckValidate() {
        boolean successed=true;
        if(isEmpty(edtEmail)){
            Toast.makeText(this, "You must enter Email to register!", Toast.LENGTH_SHORT).show();
            successed=false;
        }
        if(isEmpty(edtName)){
            edtName.setError("Name is required!");
            successed=false;
        }
        if(isEmpty(edtAddress)){
            edtAddress.setError("Address is required!");
            successed=false;
        }
        if(isEmpty(edtPhone)){
            edtPhone.setError("Phone is required!");
            successed=false;
        }
        if(isEmpty(edtPass)){
            edtPass.setError("PassWord is required!");
            successed=false;

        }
        if(isEmail(edtEmail)==false){
            edtEmail.setError("Enter valid Email!");
            successed=false;
        }
        if(successed==true) {

            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(it);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}