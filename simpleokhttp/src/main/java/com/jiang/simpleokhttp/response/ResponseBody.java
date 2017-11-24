package com.jiang.simpleokhttp.response;

import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


/**
 * Created by knowing on 2017/11/22.
 */

public abstract class ResponseBody implements Closeable {


    public final static String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    private Reader reader;

    public abstract @Nullable
    String contentType();

    public abstract long contentLength();

    public abstract InputStream source();

    public final String string() throws UnsupportedEncodingException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = source().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            closeQuietly(source());
        }
        return result.toString("UTF-8");
    }

    @Override
    public void close() throws IOException {
        closeQuietly(source());
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    public static ResponseBody create(byte[] content) {
        InputStream buffer = new ByteArrayInputStream(content);
        return create(content.length, buffer);
    }

    public static ResponseBody create(final long contentLength, final InputStream content) {
        if (content == null) throw new NullPointerException("source == null");
        return new ResponseBody() {

            @Nullable
            @Override
            public String contentType() {
                return CONTENT_TYPE;
            }

            @Override
            public long contentLength() {
                return contentLength;
            }

            @Override
            public InputStream source() {
                return content;
            }
        };
    }

}
