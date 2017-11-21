package com.jiang.simplenet;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by knowing on 2017/11/21.
 */

public class ResponseDelivery implements Executor {

    Handler mResponseHandler = new Handler(Looper.getMainLooper());


    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable respRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };

        execute(respRunnable);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mResponseHandler.post(command);
    }
}
