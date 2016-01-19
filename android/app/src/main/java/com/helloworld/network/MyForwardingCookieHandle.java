package com.helloworld.network;

import com.facebook.react.bridge.ReactContext;
import com.squareup.okhttp.Callback;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by kuangcheng01 on 2016/1/19.
 */
public class MyForwardingCookieHandle extends CookieHandler {

    public MyForwardingCookieHandle(ReactContext context) {
//        mContext = context;
//        mCookieSaver = new CookieSaver();
    }

    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        return null;
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {

    }

    public void destory() {


    }

    public void clearCookies(final Callback callback) {
    }
}
