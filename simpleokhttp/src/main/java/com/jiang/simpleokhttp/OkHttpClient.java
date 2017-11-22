package com.jiang.simpleokhttp;

import android.support.annotation.Nullable;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by knowing on 2017/11/22.
 */

public class OkHttpClient {

    //定义一个不可变的协议List
    static final List<Protocol> DEFAULT_PROTOCOLS = Collections.unmodifiableList(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1));

    //定义事件分发器
    final Dispatcher dispatcher;
    final List<Protocol> protocols;

    //定义拦截器列表
    final List<Interceptor> interceptors;
    final List<Interceptor> networkInterceptors;

    //事件监听 作用未知
    final EventListener.Factory eventListenerFactory;

    public OkHttpClient() {
        this(new Builder());
    }


    OkHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.protocols = builder.protocols;

        this.interceptors = Collections.unmodifiableList(builder.interceptors);

        this.networkInterceptors = Collections.unmodifiableList(builder.networkInterceptors);

        this.eventListenerFactory = builder.eventListenerFactory;
    }


    public static final class Builder {
        Dispatcher dispatcher;
        List<Protocol> protocols;

        final List<Interceptor> interceptors = new ArrayList<>();
        final List<Interceptor> networkInterceptors = new ArrayList<>();

        EventListener.Factory eventListenerFactory;

        public Builder() {
            dispatcher = new Dispatcher();
            protocols = DEFAULT_PROTOCOLS;

            eventListenerFactory = EventListener.factory(EventListener.NONE);

        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) throw new IllegalArgumentException("interceptor == null");
            interceptors.add(interceptor);
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            if (interceptor == null) throw new IllegalArgumentException("interceptor == null");
            networkInterceptors.add(interceptor);
            return this;
        }


        public OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }


}
