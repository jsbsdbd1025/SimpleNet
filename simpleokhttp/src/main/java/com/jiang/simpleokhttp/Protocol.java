package com.jiang.simpleokhttp;

/**
 * Created by knowing on 2017/11/22.
 */

public enum Protocol {

    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2");

    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }
}
