package com.helloworld.module;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
public class MyToastModule extends ReactContextBaseJavaModule {
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";
    private static final String COOKIE= "custom";

    public MyToastModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyToast";
    }


    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constans = new HashMap<>();
        constans.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constans.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        constans.put(COOKIE, "comfrom_kc");
        return constans;
    }

    @ReactMethod
    public void show(String message, int duration, com.facebook.react.bridge.Callback callback) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
        callback.invoke(message+"成功啦");
    }
}
