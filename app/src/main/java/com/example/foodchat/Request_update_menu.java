package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_update_menu extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.75.151:9090/input_update_menu.php";
    //final static  private String URL="http://218.236.123.14:9090/input_update_menu.php";
    private Map<String, String> map;


    public Request_update_menu(String a, String b, String c,String d,String e, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("menu_id",a);
        map.put("menu_name", b);
        map.put("menu_image", c);
        map.put("menu_price",String.valueOf(d));
        map.put("menu_info", e);





    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}