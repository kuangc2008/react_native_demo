package com.helloworld.network;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by kuangcheng01 on 2016/1/19.
 */
public class HttpModule extends ReactContextBaseJavaModule {
    private static final String USER_AGENT_HEADER_NAME = "user-agent";
    private static final String REQUEST_BODY_KEY_STRING = "string";
    private static final String CONTENT_ENCODING_HEADER_NAME = "content-encoding";

    private static final String CONTENT_TYPE_HEADER_NAME = "content-type";
    private static final String REQUEST_BODY_KEY_URI = "uri";
    private static final String REQUEST_BODY_KEY_FORMDATA = "formData";

    private static final int MIN_BUFFER_SIZE = 8 * 1024; // 8kb
    private static final int MAX_BUFFER_SIZE = 512 * 1024; // 512kb

    private static final int CHUNK_TIMEOUT_NS = 100 * 1000000; // 100ms


    private final OkHttpClient mClient;
    private final MyForwardingCookieHandle mCookieHandler;
    private final @Nullable String mDefaultUserAgent;
    private boolean mShuttingDown;

    @Override
    public String getName() {
        return "Http";
    }

    /**
     * @param context the ReactContext of the application
     */
    public HttpModule(final ReactApplicationContext context) {
        this(context, null, OkHttpClientProvider.getOkHttpClient());
    }

    public HttpModule(ReactApplicationContext context, String defaultUserAgent) {
        this(context, defaultUserAgent, OkHttpClientProvider.getOkHttpClient());
    }

    public HttpModule(ReactApplicationContext reactContext, OkHttpClient client) {
        this(reactContext, null, client);
    }

    HttpModule(
            ReactApplicationContext reactContext,
            String defaultUserAgent,
            OkHttpClient client) {
        super(reactContext);
        mClient = client;
        mClient.networkInterceptors().add(new StethoInterceptor());
        mCookieHandler = new MyForwardingCookieHandle(reactContext);
        mShuttingDown = false;
        mDefaultUserAgent = defaultUserAgent;
    }

    @Override
    public void initialize() {
        mClient.setCookieHandler(mCookieHandler);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        mShuttingDown = true;
        mClient.cancel(null);
        mCookieHandler.destory();
        mClient.setCookieHandler(null);
    }

    @ReactMethod
    public void sendRequest(String method, String url, final int requestId, ReadableArray headers,
                            ReadableMap data, final boolean useIncrementalUpdates) {

        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestId != 0) {
            requestBuilder.tag(requestId);
        }

        Headers requestHeaders = extractHeaders(headers, data);
        if (requestHeaders == null) {
            onRequestError(requestId, "Unrecognized headers format");
            return;
        }

        String contentType = requestHeaders.get(CONTENT_TYPE_HEADER_NAME);
        String contentEncoding = requestHeaders.get(CONTENT_ENCODING_HEADER_NAME);
        requestBuilder.headers(requestHeaders);

        if (data == null) {
            requestBuilder.method(method, RequestBodyUtil.getEmptyBody(method));
        } else if (data.hasKey(REQUEST_BODY_KEY_STRING)) {
            if (contentType == null) {
                onRequestError(requestId, "Payload is set but content-type header specified");
                return;
            }

            String body = data.getString(REQUEST_BODY_KEY_STRING);
            MediaType contentMediaType = MediaType.parse(contentType);
            if (RequestBodyUtil.isGzipEncoding(contentEncoding)) {
                RequestBody requestBody = RequestBodyUtil.createGzip(contentMediaType, body);
                if (requestBody == null) {
                    onRequestError(requestId, "Failed to gzip request body");
                }
                requestBuilder.method(method, requestBody);
            } else {
                requestBuilder.method(method, RequestBody.create(contentMediaType, body));
            }
        } else if (data.hasKey(REQUEST_BODY_KEY_URI)) {
            if (contentType == null) {
                onRequestError(requestId, "Paypload is set but no content-type header specified");
                return;
            }
            String uri = data.getString(REQUEST_BODY_KEY_URI);
            InputStream fileInputStream = RequestBodyUtil.getFileInputStream(getReactApplicationContext(), uri);
            if (fileInputStream == null) {
                onRequestError(requestId, "Could not retrieve file for uri " + uri);
                return;
            }
            requestBuilder.method(method, RequestBodyUtil.create(MediaType.parse(contentType), fileInputStream));
        } else if (data.hasKey(REQUEST_BODY_KEY_FORMDATA)) {
            if (contentType == null) {
                contentType = "multipart/form-data";
            }
            ReadableArray parts = data.getArray(REQUEST_BODY_KEY_FORMDATA);
            MultipartBuilder multipartBuilder = constructMultipartBody(parts, contentType, requestId);
            if (multipartBuilder == null) {
                return;
            }
            requestBuilder.method(method, multipartBuilder.build());
        } else {
            requestBuilder.method(method, null);
        }

        mClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (mShuttingDown) {
                    return;
                }
                onRequestError(requestId, e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (mShuttingDown) {
                    return;
                }
                onResponseReceived(requestId, response);

                ResponseBody responseBody = response.body();

                try {
                    if (useIncrementalUpdates) {
                        readWithProgress(requestId, responseBody);
                        onRequestSuccess(requestId);
                    } else {
                        onDataReceived(requestId, responseBody.string());
                        onRequestSuccess(requestId);
                    }
                } catch (IOException e) {
                    onRequestError(requestId, e.getMessage());
                }
            }
        });
    }

    private Headers extractHeaders(ReadableArray headersArray, ReadableMap requestData) {
        if (headersArray == null) {
            return null;
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        for( int headersIdx = 0, size = headersArray.size();  headersIdx < size; headersIdx++) {
            ReadableArray header = headersArray.getArray(headersIdx);
            if (header == null || header.size() != 2) {
                return null;
            }

            String headerName = header.getString(0);
            String headerValue = header.getString(1);
            headersBuilder.add(headerName, headerValue);
        }

        if (headersBuilder.get(USER_AGENT_HEADER_NAME) == null && mDefaultUserAgent != null) {
            headersBuilder.add(USER_AGENT_HEADER_NAME, mDefaultUserAgent);
        }

        boolean isGzipSupported = requestData != null && requestData.hasKey(REQUEST_BODY_KEY_STRING);
        if (!isGzipSupported) {
            headersBuilder.removeAll(CONTENT_ENCODING_HEADER_NAME);
        }

        return headersBuilder.build();
    }

    private DeviceEventManagerModule.RCTDeviceEventEmitter getEventEmitter() {
        return  getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }


    private @Nullable MultipartBuilder constructMultipartBody(
            ReadableArray body,
            String contentType,
            int requestId) {
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MediaType.parse(contentType));

        for (int i = 0, size = body.size(); i < size; i++) {
            ReadableMap bodyPart = body.getMap(i);

            // Determine part's content type.
            ReadableArray headersArray = bodyPart.getArray("headers");
            Headers headers = extractHeaders(headersArray, null);
            if (headers == null) {
                onRequestError(requestId, "Missing or invalid header format for FormData part.");
                return null;
            }
            MediaType partContentType = null;
            String partContentTypeStr = headers.get(CONTENT_TYPE_HEADER_NAME);
            if (partContentTypeStr != null) {
                partContentType = MediaType.parse(partContentTypeStr);
                // Remove the content-type header because MultipartBuilder gets it explicitly as an
                // argument and doesn't expect it in the headers array.
                headers = headers.newBuilder().removeAll(CONTENT_TYPE_HEADER_NAME).build();
            }

            if (bodyPart.hasKey(REQUEST_BODY_KEY_STRING)) {
                String bodyValue = bodyPart.getString(REQUEST_BODY_KEY_STRING);
                multipartBuilder.addPart(headers, RequestBody.create(partContentType, bodyValue));
            } else if (bodyPart.hasKey(REQUEST_BODY_KEY_URI)) {
                if (partContentType == null) {
                    onRequestError(requestId, "Binary FormData part needs a content-type header.");
                    return null;
                }
                String fileContentUriStr = bodyPart.getString(REQUEST_BODY_KEY_URI);
                InputStream fileInputStream =
                        RequestBodyUtil.getFileInputStream(getReactApplicationContext(), fileContentUriStr);
                if (fileInputStream == null) {
                    onRequestError(requestId, "Could not retrieve file for uri " + fileContentUriStr);
                    return null;
                }
                multipartBuilder.addPart(headers, RequestBodyUtil.create(partContentType, fileInputStream));
            } else {
                onRequestError(requestId, "Unrecognized FormData part.");
            }
        }
        return multipartBuilder;
    }

    private void onRequestError(int requestId, String error) {
        WritableArray args = Arguments.createArray();
        args.pushInt(requestId);
        args.pushString(error);

        getEventEmitter().emit("didCompleteNetworkResponse", args);
    }

    private void onRequestSuccess(int requestId) {
        WritableArray args = Arguments.createArray();
        args.pushInt(requestId);
        args.pushNull();
        getEventEmitter().emit("didCompleteNetworkResponse", args);
    }

    private void onResponseReceived(int requestId, Response response) {
        WritableMap headers = translateHeaders(response.headers());

        WritableArray args = Arguments.createArray();
        args.pushInt(requestId);
        args.pushInt(response.code());
        args.pushMap(headers);

        getEventEmitter().emit("didReceiveNetworkResponse", args);
    }

    private void onDataReceived(int requestId, String data) {
        WritableArray args = Arguments.createArray();
        args.pushInt(requestId);
        args.pushString(data);

        getEventEmitter().emit("didReceiveNetworkData", args);
    }

    private static WritableMap translateHeaders(Headers headers) {
        WritableMap responseHeaders = Arguments.createMap();
        for (int i = 0; i < headers.size(); i++) {
            String headerName = headers.name(i);
            // multiple values for the same header
            if (responseHeaders.hasKey(headerName)) {
                responseHeaders.putString(
                        headerName,
                        responseHeaders.getString(headerName) + ", " + headers.value(i));
            } else {
                responseHeaders.putString(headerName, headers.value(i));
            }
        }
        return responseHeaders;
    }

    private void readWithProgress(int requestId, ResponseBody responseBody) throws IOException {
        Reader reader = responseBody.charStream();
        try {
            StringBuilder sb = new StringBuilder(getBufferSize(responseBody));
            char[] buffer = new char[MIN_BUFFER_SIZE];
            int read;
            long last = System.nanoTime();

            while( (read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
                long now = System.nanoTime();
                if (shouldDispatch(now, last)) {
                    onDataReceived(requestId, sb.toString());
                }
            }
        } finally {
            reader.close();
        }
    }

    private static int getBufferSize(ResponseBody responseBody) throws IOException {
        long length = responseBody.contentLength();
        if (length == -1) {
            return MIN_BUFFER_SIZE;
        } else{
            return (int) Math.min(length, MAX_BUFFER_SIZE);
        }
    }

    private static boolean shouldDispatch(long now, long last) {
        return last + CHUNK_TIMEOUT_NS < now;
    }

    @ReactMethod
    public void abortRequest(final int requestId) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) {
            @Override
            protected void doInBackgroundGuarded(Void... params) {
                mClient.cancel(requestId);
            }
        }.execute();
    }

    public void clearCookies(Callback callback) {
        mCookieHandler.clearCookies(callback);
    }
}
