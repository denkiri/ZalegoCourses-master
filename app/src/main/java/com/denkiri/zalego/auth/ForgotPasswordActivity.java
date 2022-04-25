package com.denkiri.zalego.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.denkiri.zalego.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.backtoauth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = findViewById(R.id.mobile);
                String mobile = phone.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    phone.setError("Enter a valid mobile");
                    phone.requestFocus();
                    return;
                }

                Intent intent = new Intent(ForgotPasswordActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }
    }

