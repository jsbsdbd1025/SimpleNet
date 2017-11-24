package com.jiang.simplenet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jiang.simpleokhttp.OkHttpClient;
import com.jiang.simpleokhttp.call.Call;
import com.jiang.simpleokhttp.call.Callback;
import com.jiang.simpleokhttp.request.*;
import com.jiang.simpleokhttp.request.Request;
import com.jiang.simpleokhttp.response.*;
import com.jiang.simpleokhttp.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TextView tvHello;

    RequestQueue mQueue = new RequestQueue(10, HttpStackFactory.createHttpStack());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHello = findViewById(R.id.tv_hello);

//        Map<String, String> params = new HashMap<>();
        String url = "https://www.baidu.com";
//        StringRequest request = new StringRequest(HttpMethod.POST, url, params, new Request.RequestListener() {
//            @Override
//            public void onComplete(int stCode, Object reponse, String errMsg) {
//                tvHello.setText("Hello Net");
//            }
//        });
//
//        mQueue.addRequest(request);
//        mQueue.start();

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        com.jiang.simpleokhttp.request.Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });

    }
}
