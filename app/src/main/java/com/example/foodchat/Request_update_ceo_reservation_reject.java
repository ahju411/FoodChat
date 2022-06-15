package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_update_ceo_reservation_reject extends StringRequest {
    //서버 url 설정(php파일 연동)
    //final static  private String URL="http://192.168.75.151:9090/update_ceo_reservation_reject.php";
    final static  private String URL="http://218.236.123.14:9090/update_ceo_reservation_reject.php";

    private Map<String,String> map;

    public Request_update_ceo_reservation_reject(int reservation_id, String c, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("reservation_id",String.valueOf(reservation_id));
        map.put("reservation_check",c);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}