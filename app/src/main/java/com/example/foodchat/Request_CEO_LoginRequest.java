package com.example.foodchat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_CEO_LoginRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    //final static private String URL = "http://192.168.75.151:9090/ceo_login.php";
    final static  private String URL="http://218.236.123.14:9090/ceo_login.php";
    private Map<String, String> map;


    public Request_CEO_LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ceo_id",userID);
        map.put("ceo_pw", userPassword);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}