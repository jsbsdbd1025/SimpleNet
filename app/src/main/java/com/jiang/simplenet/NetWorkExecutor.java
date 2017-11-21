package com.jiang.simplenet;

import java.util.concurrent.BlockingQueue;

/**
 * Created by knowing on 2017/11/21.
 */

public class NetWorkExecutor extends Thread {

    private BlockingQueue<Request<?>> mRequestQueue;

    private HttpStack mHttpStack;


}
