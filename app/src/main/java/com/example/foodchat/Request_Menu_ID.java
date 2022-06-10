package com.example.foodchat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_Menu_ID extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://192.168.75.151:9090/load_menu_id.php";
    //final static  private String URL="http://218.236.123.14:9090/load_menu_id.php";

    private Map<String,String> map;

    public Request_Menu_ID(String a, String b,String d,String e, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("store_id",a);
        map.put("menu_name", b);
        map.put("menu_price",d);
        map.put("menu_info", e);
        System.out.println("작동하냐구요ㅊ");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}