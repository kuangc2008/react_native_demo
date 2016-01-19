package com.helloworld;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.ViewManager;
import com.helloworld.network.HttpModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kuangcheng01 on 2016/1/7.
 */
public class KCReactPackage implements ReactPackage  {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new CommonModule(reactContext));
        modules.add(new HttpModule(reactContext));

        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return new ArrayList<>(0);
    }
}
