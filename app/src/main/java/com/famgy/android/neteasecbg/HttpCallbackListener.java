package com.famgy.android.neteasecbg;

import org.json.JSONException;

/**
 * Created by uniking on 17-9-11.
 */

public interface HttpCallbackListener {
    void onFinish(String response) throws JSONException;
    void onError(Exception e);
}