package com.jiang.simpleokhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jiang.simpleokhttp.call.RealCall;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by knowing on 2017/11/22.
 */

public final class Dispatcher {

    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;

    private @Nullable
    ExecutorService executorService;

    /**
     * Running synchronous calls. Includes canceled calls that haven't finished yet.
     */
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();


    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Dispatcher() {
    }

    public synchronized ExecutorService executorService() {
        if (executorService == null) {

            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread result = new Thread(r, "OkHttp Dispatcher");
                    result.setDaemon(false);
                    return new Thread();
                }
            });
        }
        return executorService;
    }

    public synchronized void executed(RealCall call) {
        runningSyncCalls.add(call);
    }

}
