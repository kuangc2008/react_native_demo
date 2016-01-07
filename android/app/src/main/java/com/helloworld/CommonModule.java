package com.helloworld;

import android.os.Handler;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.security.auth.callback.Callback;

/**
 * Created by kuangcheng01 on 2016/1/7.
 */
public class CommonModule extends ReactContextBaseJavaModule {
    private static final String COOKIE= "cookie_kc";

    public CommonModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "Utility";
    }


    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constans = new HashMap<>();
        constans.put(COOKIE, "YYY");
        return constans;
    }

    @ReactMethod
    public void getCookie(String message, com.facebook.react.bridge.Callback callback) {
        Log.i("kcc", "getCookie");
        callback.invoke("回到成功啦" + message);
    }
}
