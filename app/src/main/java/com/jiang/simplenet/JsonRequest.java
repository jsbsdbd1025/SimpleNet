package com.jiang.simplenet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by knowing on 2017/11/21.
 */

public class JsonRequest extends Request<JSONObject> {
    public JsonRequest(HttpMethod mHttpMethod, String mUrl, Map<String, String> mBodyParams) {
        super(mHttpMethod, mUrl, mBodyParams);
    }

    @Override
    public JSONObject parseReponse(Response response) {
        String jsonString = new String(response.getRawData());

        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
