package com.helloworld.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

/**
 * Created by kuangcheng01 on 2016/1/20.
 */
public class MyIntentModule2 extends ReactContextBaseJavaModule {
    public MyIntentModule2(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyIntent2";
    }

    @ReactMethod
    public void canOpen(String action, String type, String uri, ReadableMap extra, Callback callback) {
        if (action == null || action.isEmpty()) {
            throw new JSApplicationIllegalArgumentException("Invalid action: ");
        }

            try {
                Activity currentActivity = getCurrentActivity();
                Intent intent = null;
                if (uri == null || uri.isEmpty()) {
                    intent = new Intent(action);
                } else {
                    intent = new Intent(action, Uri.parse(uri));
                }

                if (type != null) {
                    intent.setType(type);
                }

                if (extra != null) {
                    ReadableMapKeySetIterator iterator = extra.keySetIterator();
                    while (iterator.hasNextKey()) {
                        String key = iterator.nextKey();
                        ReadableType valueType = extra.getType(key);
                        if (valueType == ReadableType.Boolean) {
                            intent.putExtra(key, extra.getBoolean(key));
                        } else if (valueType == ReadableType.Number) {
                            intent.putExtra(key, extra.getInt(key));
                        } else if(valueType == ReadableType.String) {
                            intent.putExtra(key, extra.getString(key));
                        }
                    }
                }

            boolean canOpen = intent.resolveActivity(getReactApplicationContext().getPackageManager()) != null;
            callback.invoke(canOpen);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "could not check if URL "  + " can be opened: " + e.getMessage());
        }
    }

    @ReactMethod
    public void open(String action, String type, String uri, ReadableMap extra) {
        if (action == null || action.isEmpty()) {
            throw new JSApplicationIllegalArgumentException("Invalid action: ");
        }

//        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = null;
            if (uri == null || uri.isEmpty()) {
                intent = new Intent(action);
            } else {
                intent = new Intent(action, Uri.parse(uri));
            }

            if (type != null) {
                intent.setType(type);
            }

            if (extra != null) {
                ReadableMapKeySetIterator iterator = extra.keySetIterator();
                while (iterator.hasNextKey()) {
                    String key = iterator.nextKey();
                    ReadableType valueType = extra.getType(key);
                    if (valueType == ReadableType.Boolean) {
                        intent.putExtra(key, extra.getBoolean(key));
                    } else if (valueType == ReadableType.Number) {
                        intent.putExtra(key, extra.getInt(key));
                    } else if(valueType == ReadableType.String) {
                        intent.putExtra(key, extra.getString(key));
                    }
                }
            }

            if (currentActivity != null) {
                currentActivity.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getReactApplicationContext().startActivity(intent);
            }
//        } catch (Exception e) {
//            throw new JSApplicationIllegalArgumentException(
//                    "Could not open URL '"  + "': " + e.getMessage()
//            );
//        }
    }
}
