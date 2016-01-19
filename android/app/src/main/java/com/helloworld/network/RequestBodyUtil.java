package com.helloworld.network;

import android.content.Context;
import android.net.Uri;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/**
 * Created by kuangcheng01 on 2016/1/19.
 */
public class RequestBodyUtil {
    private  static final String CONTENT_ENCODING_GZIP = "gzip";

    public static RequestBody getEmptyBody(String method) {
        if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH")) {
            return RequestBody.create(null, ByteString.EMPTY);
        } else {
            return null;
        }
    }

    public static boolean isGzipEncoding(final String encodingType) {
        return CONTENT_ENCODING_GZIP.equalsIgnoreCase(encodingType);
    }

    public static RequestBody createGzip(MediaType mediaType, String body) {
        ByteArrayOutputStream gzipByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            OutputStream gzipOutputStream = new GZIPOutputStream(gzipByteArrayOutputStream);
            gzipOutputStream.write(body.getBytes());
            gzipOutputStream.close();
        } catch (IOException e) {
            return null;
        }
        return RequestBody.create(mediaType, gzipByteArrayOutputStream.toByteArray());
    }

    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            public long contentLength()  {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    public static InputStream getFileInputStream(Context context, String fileContentUriStr) {
        try {
            Uri fileContenturi = Uri.parse(fileContentUriStr);
            return context.getContentResolver().openInputStream(fileContenturi);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
