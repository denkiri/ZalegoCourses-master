package com.denkiri.zalego.mpesa.utils;

import android.util.Base64;




public class Preferences {
    static com.denkiri.zalego.mpesa.utils.Preferences instance;
    private String access_token = "";
    private String key = "", secret = "";

    private Preferences() {}
    public static com.denkiri.zalego.mpesa.utils.Preferences getInstance(){
        if(instance == null){
            instance = new com.denkiri.zalego.mpesa.utils.Preferences();
        }
        return instance;
    }
    public String getAccessToken() {
        return access_token;
    }
    public String getAuthorization(){
        String keys = key + ":" + secret;
        return Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP);
    }

    public void setAccessToken(String accessToken) {
       this.access_token = accessToken;
    }

    public void setKey(String key){
        this.key = key;
    }
    public void setSecret(String secret){
        this.secret = secret;
    }
}
