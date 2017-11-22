package com.jiang.simpleokhttp.request;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by knowing on 2017/11/22.
 */

public abstract class RequestBody {

    public final static String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    public long contentLength() throws IOException {
        return -1;
    }

    public static RequestBody create(final byte[] content) {
        return create(content, content.length);
    }

    public static RequestBody create(final byte[] content, final int byteCount) {
        if (content == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public long contentLength() {
                return byteCount;
            }

        };
    }

}
