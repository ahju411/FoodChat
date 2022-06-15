package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_get_ReviewID_CEO extends StringRequest {
    //서버 url 설정(php파일 연동)
    //final static  private String URL="http://192.168.75.151:9090/load_get_ReviewID_CEO.php";
    final static  private String URL="http://218.236.123.14:9090/load_get_ReviewID_CEO.php";

    private Map<String,String> map;

    public Request_get_ReviewID_CEO(String usernickname,int storeid, String mension, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("user_nickname",usernickname);
        map.put("store_id",String.valueOf(storeid));
        map.put("review_mension",mension);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}