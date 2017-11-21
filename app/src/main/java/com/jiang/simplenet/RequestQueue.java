package com.jiang.simplenet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by knowing on 2017/11/21.
 */

public final class RequestQueue {

    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<>();

    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    private int mDispatcherNums = DEFAULT_CORE_NUMS;

    private NetWorkExecutor[] mDispatchers = null;

    private HttpStack mHttpStack;


    protected RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    private final void startNetworkExecutors() {
        mDispatchers = new NetWorkExecutor[mDispatcherNums];

        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetWorkExecutor(mRequestQueue, mHttpStack);

            mDispatchers[i].start();
        }
    }

    public void start() {
        stop();
        startNetworkExecutors();
    }

    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }


    public void addRequest(Request<?> request) {
        if (!mRequestQueue.contains(request)) {
            request.setSerialNumber(this.generateSerialNumber());
            mRequestQueue.add(request);
        } else {

        }
    }

    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
