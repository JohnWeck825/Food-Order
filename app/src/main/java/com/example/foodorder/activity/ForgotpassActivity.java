package com.example.foodorder.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodorder.R;

import java.util.Random;


public class ForgotpassActivity extends AppCompatActivity {
    Button btnConfirm;
    Button btnSend;
    EditText edtPhonereset;
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpass);

        btnConfirm= findViewById(R.id.btnConfirm);
        edtPhonereset=findViewById(R.id.editPhonereset);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    edtPhonereset = (EditText)findViewById(R.id.editPhonereset);
                    String phoneNumber = edtPhonereset.getText().toString();
                    Random rd=new Random();
//                    Integer codeid=(int)rd.nextInt((99999 - 10000) + 1) + 10000;
                    sendSMS(phoneNumber, "tong tuan");
                }

                catch (Exception e)

                {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    AlertDialog dialog = alertDialogBuilder.create();

                    dialog.setMessage(e.getMessage());

                    dialog.show();

                }

            }

        });



    }



//

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
//            PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
//            smsManager.sendTextMessage(phoneNumber, null, message, sentIntent, null);


            Toast.makeText(getApplicationContext(), "Tin nhắn đã được gửi!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Gửi tin nhắn thất bại, vui lòng thử lại sau.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.activity_send_sms, menu);
//
//        return true;
//
//    }
}