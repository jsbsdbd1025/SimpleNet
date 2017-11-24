package com.jiang.simpleokhttp.request;

import android.support.annotation.Nullable;

import com.jiang.simpleokhttp.Headers;
import com.jiang.simpleokhttp.HttpMethod;

/**
 * Created by knowing on 2017/11/22.
 */

public class Request {

    final String url;
    final String method;
    final Headers headers;
    final @Nullable
    RequestBody body;

    Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
    }

    public String getUrl() {
        return url;
    }

    public static class Builder {

        String url;
        String method;
        Headers.Builder headers;
        RequestBody body;


        public Builder() {
            this.method = HttpMethod.GET.getMethod();
            this.headers = new Headers.Builder();
        }

        public Request build() {
            if (url == null) throw new IllegalStateException("url == null");
            return new Request(this);
        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder get() {
            return method(HttpMethod.GET.getMethod(), null);
        }


        public Builder post(RequestBody body) {
            return method(HttpMethod.POST.getMethod(), body);
        }


        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");

            this.url = url;
            return this;
        }

        public Builder method(String method, @Nullable RequestBody body) {
            if (method == null) throw new NullPointerException("method == null");
            if (method.length() == 0) throw new IllegalArgumentException("method.length() == 0");
            this.method = method;
            this.body = body;
            return this;
        }

    }

}
