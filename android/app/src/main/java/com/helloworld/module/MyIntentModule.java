package com.helloworld.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by kuangcheng01 on 2016/1/20.
 */
public class MyIntentModule extends ReactContextBaseJavaModule {

    public MyIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyIntent";
    }


    @ReactMethod
    public void canOpenUrl(String url, Callback callback) {
        if (url == null || url.isEmpty()) {
            throw new JSApplicationIllegalArgumentException("Invalid URL: " + url);
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            boolean canOpen = intent.resolveActivity(getReactApplicationContext().getPackageManager()) != null;
            callback.invoke(canOpen);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "could not check if URL " + url + " can be opened: " + e.getMessage());
        }
    }

    @ReactMethod
    public void openURL(String url) {
        if (url == null || url.isEmpty()) {
            throw new JSApplicationIllegalArgumentException("Invalid URL: " + url);
        }

        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (currentActivity != null) {
                currentActivity.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getReactApplicationContext().startActivity(intent);
            }
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "Could not open URL '" + url + "': " + e.getMessage()
            );
        }
    }

    @ReactMethod
    public void getInitialURL(Callback callback) {
        try {
            Activity currentActivity = getCurrentActivity();
            String initialURL = null;
            if (currentActivity != null) {
                Intent intent = currentActivity.getIntent();
                String action = intent.getAction();
                Uri uri = intent.getData();

                if (Intent.ACTION_VIEW.equals(action) && uri != null) {
                    initialURL = uri.toString();
                }
            }
            callback.invoke(initialURL);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "Could not get the initial URL : " + e.getMessage());
        }
    }

}
