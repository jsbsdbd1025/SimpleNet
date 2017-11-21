package com.jiang.simplenet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by knowing on 2017/11/21.
 */

public class HttpUrlConnStack implements HttpStack {

    private static final int DEFAULT_TIME_OUT = 10000;

    @NotNull
    @Override
    public Response performRequest(@NotNull Request<?> request) {
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = createUrlConnection(request.getUrl());

            setRequestHeaders(urlConnection, request);

            setRequestParams(urlConnection, request);

            return fetchResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response fetchResponse(HttpURLConnection urlConnection) throws IOException {

        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int requestCode = urlConnection.getResponseCode();

        if (requestCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection!");
        }

        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                urlConnection.getResponseCode(), urlConnection.getResponseMessage());

        Response response = new Response(responseStatus);

        response.setEntity(entityFromURLConnection(urlConnection));
        addHeadersToResponse(response, urlConnection);

        return response;

    }

    private void setRequestHeaders(HttpURLConnection urlConnection, Request<?> request) {
        Set<String> headerKeys = request.getHeaders().keySet();

        for (String headerName : headerKeys) {
            urlConnection.addRequestProperty(headerName, request.getHeaders().get(headerName));
        }

    }

    private void setRequestParams(HttpURLConnection connection, Request<?> request) throws IOException {

        HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());

        byte[] body = request.getBody();

        if (body != null) {
            connection.setDoOutput(true);

            connection.addRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }


    private HttpURLConnection createUrlConnection(String url) throws IOException {
        URL newURL = new URL(url);
        URLConnection urlConnection = newURL.openConnection();
        urlConnection.setConnectTimeout(DEFAULT_TIME_OUT);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return (HttpURLConnection) urlConnection;
    }

    private HttpEntity entityFromURLConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();

        InputStream inputStream = null;

        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }

        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());

        return entity;
    }

    private void addHeadersToResponse(BasicHttpResponse response, HttpURLConnection connection) {
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));

                response.addHeader(h);
            }
        }
    }
}
