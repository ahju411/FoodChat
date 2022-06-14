package com.example.foodchat;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_input_reservation extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://192.168.75.151:9090/input_reservation.php";
    //final static  private String URL="http://218.236.123.14:9090/input_reservation.php";

    private Map<String,String> map;

    public Request_input_reservation(String userid,String usernickname,int storeid,String date,String time,
                                     int adult,int child,int check,String storename,String storeaddress,Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);

        map=new HashMap<>();
        System.out.println("유저아이디보내ㅑ지냐:"+userid);
        map.put("user_id",userid);
        map.put("user_nickname",usernickname);
        map.put("store_id",String.valueOf(storeid));
        map.put("reservation_date",date);
        map.put("reservation_time",time);
        map.put("reservation_adult",String.valueOf(adult));
        map.put("reservation_child",String.valueOf(child));
        map.put("reservation_check",String.valueOf(check));
        map.put("reservation_storename",storename);
        map.put("reservation_storeaddress",storeaddress);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}