package com.jiang.simplenet;

/**
 * Created by knowing on 2017/11/21.
 */

public interface HttpStack {

    public Response performRequest(Request<?> request);
}
