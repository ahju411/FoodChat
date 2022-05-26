package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_get_menu extends StringRequest {
    //서버 url 설정(php파일 연동)
    //final static  private String URL="http://192.168.75.151:9090/load_get_menu.php";
    final static  private String URL="http://218.236.123.14:9090/load_get_menu.php";

    private Map<String,String> map;

    public Request_get_menu(int storeid, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("store_id",String.valueOf(storeid));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}