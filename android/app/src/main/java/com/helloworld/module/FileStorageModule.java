package com.helloworld.module;

import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by kuangcheng01 on 2016/1/28.
 */
public class FileStorageModule extends ReactContextBaseJavaModule {
    private static final String NAME = "FileStorage";

    private static final String KEY_NAME = "name";
    private static final String KEY_REPLEACE = "isReplace";
    private static final String KEY_CONTENT = "content";

    public FileStorageModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constans = new HashMap<>();
        constans.put(KEY_NAME, KEY_NAME);
        constans.put(KEY_REPLEACE, KEY_REPLEACE);
        constans.put(KEY_CONTENT, KEY_CONTENT);
        return constans;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void isFileExist(String name, Callback callback) {
        File path = getReactApplicationContext().getFileStreamPath(name);
        if(path.exists()) {
            callback.invoke(true);
        } else {
            callback.invoke(false);
        }
    }

    @ReactMethod
    public void saveFile(ReadableMap map, Callback callback) {
        String name = map.getString(KEY_NAME);
        if (TextUtils.isEmpty(name)) {
            throw new JSApplicationIllegalArgumentException("path is null");
        }

        File outputFile = getReactApplicationContext().getFileStreamPath(name);

        Boolean reReplace = false;
        if (map.hasKey(KEY_REPLEACE)) {
            reReplace = map.getBoolean(KEY_REPLEACE);
        }
        if (!reReplace && outputFile.exists()) {
            callback.invoke("file exist");
            return;
        }

        String content = map.getString(KEY_CONTENT);
        if (!outputFile.exists()) {
            File parentDir = outputFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                callback.invoke("can not create file");
                return;
            }
        }

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(outputFile);
            fwriter.write(content);
        } catch (IOException ex) {
            callback.invoke("can not create file");
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                callback.invoke("can not create file");
            }
        }
    }

    @ReactMethod
    public void getFileData(String name, Callback callback) {
        if (TextUtils.isEmpty(name)) {
            throw new JSApplicationIllegalArgumentException("path is null");
        }

        File outputFile = getReactApplicationContext().getFileStreamPath(name);
        FileInputStream inputStream = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(outputFile);
            byte[] buffer = new byte[4096];
            int len = 0;
            while (( (len = inputStream.read(buffer, 0, 4096))) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new JSApplicationIllegalArgumentException("path is null");
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e1) {
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }

            callback.invoke(outStream.toString());
        }
    }

}
