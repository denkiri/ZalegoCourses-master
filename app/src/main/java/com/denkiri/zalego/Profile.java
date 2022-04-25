package com.denkiri.zalego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.denkiri.zalego.auth.LoginActivity;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.storage.PreferenceManager;

public class Profile extends AppCompatActivity {
TextView firstname,lastname,mobile,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (!PreferenceManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        firstname = (TextView) findViewById(R.id.firstname);
        lastname = (TextView) findViewById(R.id.lastname);
        mobile = (TextView) findViewById(R.id.mobile);
        email = (TextView) findViewById(R.id.email);
        findViewById(R.id.backtomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        Student student = PreferenceManager.getInstance(this).getStudent();
        firstname.setText((String.valueOf(student.getName()) ));
        lastname.setText((String.valueOf(student.getLastName())));
        mobile.setText((String.valueOf(student.getMobile()) ));
        email.setText((String.valueOf(student.getEmail()) ));
    }
}
