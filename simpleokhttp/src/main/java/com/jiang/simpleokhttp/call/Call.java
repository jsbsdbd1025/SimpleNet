package com.jiang.simpleokhttp.call;

import com.jiang.simpleokhttp.request.Request;
import com.jiang.simpleokhttp.response.Response;

import java.io.IOException;

/**
 * Created by knowing on 2017/11/22.
 */

public interface Call extends Cloneable {

    Request request();

    Response execute() throws IOException;

    void enqueue(Callback responseCallback);

    void cancel();

    boolean isExecuted();

    boolean isCanceled();

    Call clone();

    interface Factory {
        Call newCall(Request request);
    }
}