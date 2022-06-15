package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_input_user_favorite extends StringRequest {
    //서버 url 설정(php파일 연동)
    //final static  private String URL="http://192.168.75.151:9090/input_user_favorite.php";
    final static  private String URL="http://218.236.123.14:9090/input_user_favorite.php";

    private Map<String,String> map;

    public Request_input_user_favorite(String userid,int storeid, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);

        map=new HashMap<>();
        System.out.println("유저아이디보내ㅑ지냐:"+userid);
        System.out.println("스토어아디보내지냐"+storeid);
        map.put("user_id",userid);
        map.put("store_id",String.valueOf(storeid));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}