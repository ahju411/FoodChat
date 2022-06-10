package com.example.foodchat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_delete_menu extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://192.168.75.151:9090/input_delete_menu.php";
    //final static  private String URL="http://218.236.123.14:9090/input_delete_menu.php";

    private Map<String,String> map;

    public Request_delete_menu(String userID, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("menu_id",userID);
        System.out.println("보낸 메뉴아이디는?"+userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}