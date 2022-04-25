package com.denkiri.zalego.storage;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.denkiri.zalego.auth.LoginActivity;
import com.denkiri.zalego.models.Student;

import java.util.Date;

public class PreferenceManager {
    private static final String SHARED_PREF_NAME = "sharedpref";
    private static final String KEY_NAME ="keyname";
    private static final String KEY_LASTNAME ="keylastname";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_MOBILE ="keymobile";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static PreferenceManager mInstance;
    private static Context mCtx;

    private PreferenceManager(Context context) {
        mCtx = context;
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenceManager(context);
        }
        return mInstance;
    }

    //method to let the student login
    //this method will store the student data in shared preferences
    public void studentLogin(Student student) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, student.getId());
        editor.putString(KEY_NAME,student.getName());
        editor.putString(KEY_LASTNAME,student.getLastName());
        editor.putString(KEY_USERNAME, student.getUsername());
        editor.putString(KEY_MOBILE, student.getMobile());
        editor.putString(KEY_EMAIL, student.getEmail());
        editor.putString(KEY_GENDER, student.getGender());
        editor.putLong("lastlogin", new Date().getTime());
        editor.apply();

    }

    //this method will checker whether student is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in student
    public Student getStudent() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Student(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_LASTNAME,null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }

    //this method will logout the student
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}

