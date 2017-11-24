package com.jiang.simpleokhttp.response;

import android.support.annotation.Nullable;

import com.jiang.simpleokhttp.Headers;
import com.jiang.simpleokhttp.Protocol;
import com.jiang.simpleokhttp.request.Request;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by knowing on 2017/11/22.
 */

public final class Response implements Closeable {

    final Request request;
    final Protocol protocol;

    final int code;
    final String message;

    final Headers headers;

    final ResponseBody body;
    final Response networkResponse;


    Response(Builder builder) {

        this.request = builder.request;
        this.protocol = builder.protocol;

        this.code = builder.code;
        this.message = builder.message;

        this.headers = builder.headers.build();

        this.body = builder.body;
        this.networkResponse = builder.networkResponse;
    }


    @Override
    public String toString() {
        return "Response{protocol="
                + protocol
                + ", code="
                + code
                + ", message="
                + message
                + ", url="
                + request.getUrl()
                + '}';
    }

    public static class Builder {
        Request request;
        Protocol protocol;
        int code = -1;
        String message;
        Headers.Builder headers;

        ResponseBody body;
        Response networkResponse;

        public Builder() {
            headers = new Headers.Builder();
        }

        public Builder request(Request request) {
            this.request = request;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder body(@Nullable ResponseBody body) {
            this.body = body;
            return this;
        }

        public Builder networkResponse(@Nullable Response networkResponse) {
            if (networkResponse != null) checkSupportResponse("networkResponse", networkResponse);
            this.networkResponse = networkResponse;
            return this;
        }

        private void checkSupportResponse(String name, Response response) {
            if (response.body != null) {
                throw new IllegalArgumentException(name + ".body != null");
            } else if (response.networkResponse != null) {
                throw new IllegalArgumentException(name + ".networkResponse != null");
            }
        }

        public Response build() {
            if (request == null) throw new IllegalStateException("request == null");
            if (protocol == null) throw new IllegalStateException("protocol == null");
            if (code < 0) throw new IllegalStateException("code < 0: " + code);
            if (message == null) throw new IllegalStateException("message == null");
            return new Response(this);
        }
    }

    @Override
    public void close() throws IOException {

    }
}
