package com.jiang.simplenet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

        Map<String, String> params = new HashMap<>();
        params.put("pushregid", "empty");
        params.put("password", "123456");
        params.put("account", "15538307252");
        params.put("desc", "%7B%22client%22%3A%22android%22%7D");
        String url = "http://mobile.dev.idaqi.com/iext/gov/mobile/UserController/login.do";
        StringRequest request = new StringRequest(HttpMethod.GET, url, params, new Request.RequestListener() {
            @Override
            public void onComplete(int stCode, Object reponse, String errMsg) {
                tvHello.setText("Hello Net");
            }
        });

        mQueue.addRequest(request);
        mQueue.start();
    }
}
