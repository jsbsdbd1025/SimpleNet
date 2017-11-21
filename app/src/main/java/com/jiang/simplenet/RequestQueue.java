package com.jiang.simplenet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by knowing on 2017/11/21.
 */

public class RequestQueue {

    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<>();

    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    private int mDispatcherNums = DEFAULT_CORE_NUMS;


}
