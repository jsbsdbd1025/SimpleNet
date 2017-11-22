package com.jiang.simplenet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by knowing on 2017/11/21.
 */

public class StringRequest extends Request<String> {


    public StringRequest(HttpMethod mHttpMethod, String mUrl, Map<String, String> mBodyParams, RequestListener requestListener) {
        super(mHttpMethod, mUrl, mBodyParams);
        this.mRequestListener = requestListener;
    }

    @Override
    public String parseReponse(Response response) {
        String string = new String(response.getRawData());



        return string;
    }
}
