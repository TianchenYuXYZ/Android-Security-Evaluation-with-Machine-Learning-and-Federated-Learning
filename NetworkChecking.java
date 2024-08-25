package com.example.securityevaluationwithexpertsystems;


import static android.content.ContentValues.TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import android.net.*;

import android.os.Build;
import android.security.NetworkSecurityPolicy;
import android.util.Log;

import java.util.List;

public class NetworkChecking {
    static TransportInfo info;



    public Map checkNetworkStatus(Context context) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo processInfo = mConnectivityManager.getActiveNetworkInfo();
        assert processInfo != null;
        boolean isconnected = processInfo.isConnectedOrConnecting();
        String name = processInfo.getTypeName();
        Network network = mConnectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = mConnectivityManager.getNetworkCapabilities(network);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            assert capabilities != null;
            info = capabilities.getTransportInfo();
        }

        LinkProperties properties = mConnectivityManager.getLinkProperties(network);
        assert properties != null;
        List<LinkAddress> addresses = properties.getLinkAddresses();

        boolean communicationEncryption = true;
        try {
            communicationEncryption = NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
        }
        catch (Exception e){
            Log.d(TAG,"Exception");
        }
        String[] a = Objects.requireNonNull(capabilities).toString().split("^.*Capabilities:\\s*");
        int i = 0;
        String c = "";
        for (String b:a){
            c = b;
        }
        String[] d = c.split("&");

        String[] f = Arrays.copyOf(d, 9);
        StringBuilder concatenatedString = new StringBuilder();
        for (int k = 0; i < f.length; i++) {
            concatenatedString.append(f[i]);
            if (i != f.length - 1) {
                concatenatedString.append(" ");
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("communication Encrypted", String.valueOf(communicationEncryption));
        map.put("active network info", String.valueOf(concatenatedString));

            return map;
         }


}
