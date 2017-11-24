package com.jiang.simpleokhttp;

import android.support.annotation.Nullable;

import com.jiang.simpleokhttp.call.Call;
import com.jiang.simpleokhttp.request.Request;
import com.jiang.simpleokhttp.response.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by knowing on 2017/11/22.
 */

public interface Interceptor {
    Response intercept(Chain chain) throws IOException;

    interface Chain {
        Request request();

        Response proceed(Request request) throws IOException;

        /**
         * Returns the connection the request will be executed on. This is only available in the chains
         * of network interceptors; for application interceptors this is always null.
         */
//        @Nullable
//        Connection connection();

        Call call();

        int connectTimeoutMillis();

        Chain withConnectTimeout(int timeout, TimeUnit unit);

        int readTimeoutMillis();

        Chain withReadTimeout(int timeout, TimeUnit unit);

        int writeTimeoutMillis();

        Chain withWriteTimeout(int timeout, TimeUnit unit);
    }
}
