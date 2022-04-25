package com.denkiri.zalego.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.regex.Pattern;

import com.denkiri.zalego.R;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;
import com.denkiri.zalego.storage.PreferenceManager;

public class RegistrationActivity extends AppCompatActivity {
    EditText firstname, lastname, email,mobile,username,password;
    RadioGroup radioGroupGender;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firstname=(EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        mobile =(EditText) findViewById(R.id.mobile);
        username=(EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);


        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if student pressed on button register
                //here we will register the student to server
                registerUser();
            }
        });

        findViewById(R.id.backtoauth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if student pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser() {
        final String FirstName= firstname.getText().toString().trim();
        final String LastName = lastname.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Mobile =mobile.getText().toString().trim();
        final String Username =username.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations
        if(TextUtils.isEmpty(FirstName)){
            firstname.setError("please enter your FirstName");
            firstname.requestFocus();
        }

        if (TextUtils.isEmpty(LastName)) {
            lastname.setError("Please enter your LastName");
            lastname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Mobile)) {
            mobile.setError("Please enter your Mobile Number");
            mobile.requestFocus();
            return;
        }
        if (!isValidMobile(Mobile)){
            mobile.setError("Invalid Mobile Number");
            mobile.requestFocus();
            return ;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {

            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Username)) {
            username.setError("Please enter your Username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if(!obj.getBoolean("error"))   {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                                //starting the course activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
                params.put("firstname",FirstName);
                params.put("lastname", LastName);
                params.put("email", Email);
                params.put("mobile",Mobile);
                params.put("username",Username);
                params.put("password", Password);
                params.put("gender", gender);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return  phone.length() == 14;
        }
        return false;
    }

}

