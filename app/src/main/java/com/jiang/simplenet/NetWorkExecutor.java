package com.jiang.simplenet;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.LruCache;

import java.util.concurrent.BlockingQueue;

/**
 * Created by knowing on 2017/11/21.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
public class NetWorkExecutor extends Thread {

    private BlockingQueue<Request<?>> mRequestQueue;

    private HttpStack mHttpStack;

    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();


    private static LruCache<String, Response> mReqCache;

    private boolean isStop = false;

    public NetWorkExecutor(BlockingQueue<Request<?>> requestQueue, HttpStack httpStack) {
        this.mRequestQueue = requestQueue;
        this.mHttpStack = httpStack;


        long maxMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (maxMemory / 8);

        mReqCache = new LruCache<>(cacheSize);
    }

    @Override
    public void run() {
        try {

            while (!isStop) {
                final Request<?> request = mRequestQueue.take();

                if (request.isCancel) {
                    continue;
                }

                Response response = null;

                if (isUseCache(request)) {
                    response = mReqCache.get(request.getUrl());
                } else {
                    response = mHttpStack.performRequest(request);

                    if (request.isShouldCache() && isSuccess(response)) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }

                mResponseDelivery.deliveryResponse(request, response);
            }


        } catch (InterruptedException e) {

        }
    }

    private boolean isSuccess(Response response) {
        return response != null && response.getStatusLine().getStatusCode() == 200;
    }

    private boolean isUseCache(Request<?> request) {
        return request.isShouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
