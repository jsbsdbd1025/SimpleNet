package com.jiang.simpleokhttp.call;

import com.jiang.simpleokhttp.EventListener;
import com.jiang.simpleokhttp.Interceptor;
import com.jiang.simpleokhttp.OkHttpClient;
import com.jiang.simpleokhttp.request.Request;
import com.jiang.simpleokhttp.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by knowing on 2017/11/23.
 */

public class RealCall implements Call {

    final OkHttpClient client;
    private EventListener eventListener;

    final Request originalRequest;

    public RealCall(OkHttpClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    public static RealCall newRealCall(OkHttpClient client, Request originalRequest) {
        // Safely publish the Call instance to the EventListener.
        RealCall call = new RealCall(client, originalRequest);
        call.eventListener = client.eventListenerFactory().create(call);
        return call;
    }

    @Override
    public Request request() {
        return originalRequest;
    }

    @Override
    public Response execute() throws IOException {
        eventListener.callStart(this);
        client.dispatcher().executed(this);
        Response result = getResponseWithInterceptorChain();
        return null;
    }

    @Override
    public void enqueue(Callback responseCallback) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call clone() {
        return null;
    }

    Response getResponseWithInterceptorChain() throws IOException {
        // Build a full stack of interceptors.
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.interceptors());
//        interceptors.add(new BridgeInterceptor(client.cookieJar()));
//        interceptors.add(new CacheInterceptor(client.internalCache()));
//        interceptors.add(new ConnectInterceptor(client));

//        interceptors.add(new CallServerInterceptor(forWebSocket));

//        Interceptor.Chain chain = new RealInterceptorChain(interceptors, null, null, null, 0,
//                originalRequest, this, eventListener, client.connectTimeoutMillis(),
//                client.readTimeoutMillis(), client.writeTimeoutMillis());

        return null;
    }
}
