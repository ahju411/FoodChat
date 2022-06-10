package com.example.foodchat;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_InputStore extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.75.151:9090/input_store_info.php";
    //final static  private String URL="http://218.236.123.14:9090/input_store_info.php";
    private Map<String, String> map;


    public Request_InputStore(String a, String b, String c,String d,String e,String f, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("store_name",a);
        map.put("store_address", b);
        map.put("store_time", c);
        map.put("store_images",String.valueOf(d));
        System.out.println("여기기록되나");
        System.out.println("길이:"+d.length());
        map.put("store_mension", e);
        map.put("ceo_id",f);




    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}