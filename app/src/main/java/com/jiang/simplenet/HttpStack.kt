package com.jiang.simplenet

/**
 * Created by knowing on 2017/11/21.
 */

interface HttpStack {

    fun performRequest(request: Request<*>): Response
}
