package com.jiang.simplenet;


import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by knowing on 2017/11/21.
 */

public abstract class Request<T> implements Comparable<Request<T>> {

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public static final String HEADER_CONTENT_TYPE = "Content-type";

    protected int mSerialNumber = 0;

    protected Priority mPriority = Priority.NORMAL;

    protected boolean isCancel = false;

    protected boolean mShouldCache = true;

    protected RequestListener<T> mRequestListener;

    private String mUrl = "";

    HttpMethod mHttpMethod = HttpMethod.GET;

    private Map<String, String> mHeaders = new HashMap<>();

    private Map<String, String> mBodyParams = new HashMap<>();


    public Request(HttpMethod mHttpMethod, String mUrl, Map<String, String> mBodyParams) {
        this.mUrl = mUrl;
        this.mHttpMethod = mHttpMethod;
        this.mBodyParams = mBodyParams;
    }

    public abstract T parseReponse(Response response);

    public final void deliveryResponse(Response response) {
        T result = parseReponse(response);

        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusLine().getStatusCode() : -1;
            String msg = response != null ? response.getStatusLine().getReasonPhrase() : "unkonw error!";
            mRequestListener.onComplete(stCode, result, msg);
        }
    }

    public Priority getPriority() {
        return mPriority;
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public void setSerialNumber(int serialNumber) {
        this.mSerialNumber = mSerialNumber;
    }

    public int getSerialNumber() {
        return mSerialNumber;
    }

    protected String getBodyContentType() {
        return "application/x-www-form-urlencoded;charset=" + getParamsEncoding();
    }


    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {

        StringBuilder encoderParams = new StringBuilder();

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encoderParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encoderParams.append("=");
                encoderParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encoderParams.append("&");
            }
            return encoderParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding);
        }

    }

    public Map<String, String> getParams() {
        return mBodyParams;
    }

    @Override
    public int compareTo(@NonNull Request<T> o) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = o.getPriority();
        return myPriority.equals(anotherPriority) ? this.getSerialNumber() - o.getSerialNumber()
                : myPriority.ordinal() - anotherPriority.ordinal();
    }

    public static interface RequestListener<T> {
        public void onComplete(int stCode, T reponse, String errMsg);
    }

}
