package com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.devsupport.DevServerHelper;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import junit.framework.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okio.Okio;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AssetManager am = getAssets();
//        try {
//            InputStream is = am.open("index.android.bundle.js");
//            int len = 0;
//            byte[] buffer = new byte[2048];
//            ByteArrayOutputStream sb  = new ByteArrayOutputStream();
//            while( (len = is.read(buffer)) > 0) {
//                sb.write(buffer, 0 ,len);
//            }
//            sb.flush();
//            Log.i("kcc", "s-->" + sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        Debug.waitForDebugger();



    //    Debug.waitForDebugger();
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("IntentAndroidExample.android")  //自定义intent
                .addPackage(new MainReactPackage())
                .addPackage(new KCReactPackage())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "HelloWorld", null);

        setContentView(mReactRootView);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String action = DevServerHelper.getReloadAppAction(MainActivity.this);
//                sendBroadcast(new Intent(action));
//            }
//        }, 2000);


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                WritableMap params = Arguments.createMap();
//                params.putInt("gaga", 0);
////                mReactInstanceManager.getCurrentReactContext()
////                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
////                        .emit("onActivityCreate", params);
//            }
//        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
      if (mReactInstanceManager != null) {
        mReactInstanceManager.onBackPressed();
      } else {
        super.onBackPressed();
      }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
      super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this, this);
        }

    }
}
