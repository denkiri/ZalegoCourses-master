package com.denkiri.zalego.mpesa.interfaces;

import com.denkiri.zalego.mpesa.utils.Pair;


public interface MpesaListener {
    public void onMpesaError(Pair<Integer, String> result);
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage);
}
