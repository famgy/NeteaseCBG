package com.famgy.android.neteasecbg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by uniking on 17-9-11.
 */

public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener)
    {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (listener != null) {
                listener.onFinish(response.toString());
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.onError(e);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
