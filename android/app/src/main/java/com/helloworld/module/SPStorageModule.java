package com.helloworld.module;

import android.content.SharedPreferences;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by kuangcheng01 on 2016/1/28.
 */
public class SPStorageModule extends ReactContextBaseJavaModule {
    private static final String NAME = "SPStorage";
    private static final String SP_NAME = "react_native";

    public SPStorageModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private SharedPreferences getSp() {
        return getReactApplicationContext().getSharedPreferences(SP_NAME, ReactApplicationContext.MODE_PRIVATE);
    }

    @ReactMethod
    public final void getString(String key, String defaultvalue, Callback callback) {
        String value = getSp().getString(key, defaultvalue);
        callback.invoke(value);
    }

    @ReactMethod
    public final void getInt(String key, int defaultvalue, Callback callback) {
        int value = getSp().getInt(key, defaultvalue);
        callback.invoke(value);
    }

    @ReactMethod
    public final void getBoolean(String key, boolean booelan ,Callback callback) {
        boolean value = getSp().getBoolean(key, booelan);
        callback.invoke(value);
    }

    @ReactMethod
    public final void setString(String key, String value) {
        getSp().edit().putString(key, value).apply();
    }

    @ReactMethod
    public final void setInt(String key, int valuek) {
        getSp().edit().putInt(key, valuek).apply();
    }

    @ReactMethod
    public final void setBoolean(String key, boolean value) {
        getSp().edit().putBoolean(key, value).apply();
    }
}
