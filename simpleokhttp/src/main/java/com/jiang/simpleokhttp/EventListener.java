package com.jiang.simpleokhttp;

import android.support.annotation.Nullable;

import com.jiang.simpleokhttp.call.Call;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

/**
 * Created by knowing on 2017/11/22.
 */

public abstract class EventListener {

    public static final EventListener NONE = new EventListener() {
    };

    static EventListener.Factory factory(final EventListener listener) {
        return new EventListener.Factory() {
            public EventListener create(Call call) {
                return listener;
            }
        };
    }

    public void callStart(Call call) {
    }

    public void callEnd(Call call) {
    }

    public void callFailed(Call call, IOException ioe) {
    }


    public void dnsStart(Call call, String domainName) {
    }

    public void dnsEnd(Call call, String domainName, @Nullable List<InetAddress> inetAddressList) {
    }

    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
    }

    public void connectEnd(Call call, InetSocketAddress inetSocketAddress,
                           @Nullable Proxy proxy, @Nullable Protocol protocol) {
    }

    public void connectFailed(Call call, InetSocketAddress inetSocketAddress,
                              @Nullable Proxy proxy, @Nullable Protocol protocol, @Nullable IOException ioe) {
    }

    public interface Factory {

        EventListener create(Call call);
    }
}
