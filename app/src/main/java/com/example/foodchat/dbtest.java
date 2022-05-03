package com.example.foodchat;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class dbtest extends Activity {
    static RequestQueue requestQueue;
    TextView textview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbtest);

        textview1 = findViewById(R.id.tv_outPut);

        //요청 큐가 없으면 요청 큐 생성하기
        //나중에 여기다가 데이터 담으면 알아서!!!!!!! 통신함 ㅋ
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        login();

    }

    public void login() {
        //php url 입력
        String URL = "http://foodchat.dothome.co.kr/test3.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                textview1.setText(response);
                Toast.makeText(getApplicationContext(), "응답:" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴
                Toast.makeText(getApplicationContext(), "에러:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }

}