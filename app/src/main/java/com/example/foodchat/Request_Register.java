package com.example.foodchat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_Register extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.75.151:9090/Register.php";
    //final static  private String URL="http://218.236.123.14:9090/Register.php";
    private Map<String, String> map;


    public Request_Register(String userID, String userPassword, String userName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",userID);
        map.put("user_pw", userPassword);
        map.put("user_nickname", userName);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}