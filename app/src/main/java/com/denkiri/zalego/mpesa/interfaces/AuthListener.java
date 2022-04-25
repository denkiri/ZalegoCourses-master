package com.denkiri.zalego.mpesa.interfaces;

import com.denkiri.zalego.mpesa.utils.Pair;



public interface AuthListener {
    public void onAuthError(Pair<Integer, String> result);
    public void onAuthSuccess();
}
