package com.denkiri.zalego.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.denkiri.zalego.AuthActivity;
import com.denkiri.zalego.MainActivity;
import com.denkiri.zalego.R;
import com.denkiri.zalego.WelcomeActivity;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;
import com.denkiri.zalego.storage.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (PreferenceManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);


        //if student presses on login
        //calling the method login
        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        findViewById(R.id.backtoauth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });
        //if student presses on not registered
        findViewById(R.id.signinback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });
        findViewById(R.id.forgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));

            }
        });
        //i
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                //getting the student from the response
                                JSONObject studentJson = obj.getJSONObject("student");

                                //creating a new student object
                                Student student = new Student(
                                        studentJson.getInt("id"),
                                        studentJson.getString("firstname"),
                                        studentJson.getString("lastname"),
                                        studentJson.getString("email"),
                                        studentJson.getString("mobile"),
                                        studentJson.getString("username"),
                                        studentJson.getString("gender")
                                );

                                //storing the student in shared preferences
                                PreferenceManager.getInstance(getApplicationContext()).studentLogin(student);
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //starting the Course activity

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}