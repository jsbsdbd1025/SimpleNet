package com.jiang.simpleokhttp.call;

import com.jiang.simpleokhttp.response.Response;

import java.io.IOException;

/**
 * Created by knowing on 2017/11/22.
 */

public interface Callback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response) throws IOException;
}
