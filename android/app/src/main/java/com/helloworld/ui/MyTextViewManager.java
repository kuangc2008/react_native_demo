package com.helloworld.ui;

import android.graphics.Matrix;
import android.support.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.uimanager.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;

/**
 * Created by kuangcheng01 on 2016/1/21.
 */
public class MyTextViewManager extends SimpleViewManager<MyTextView>{

    @Override
    public String getName() {
        return "MyTextView";
    }

    @Override
    protected MyTextView createViewInstance(ThemedReactContext reactContext) {
        return new MyTextView(reactContext);
    }

    @ReactProp(name = "text")
    public void setSource(MyTextView view, String text) {
        view.setText(text);
    }

}
