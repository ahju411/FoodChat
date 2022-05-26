package com.example.foodchat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Request_Insert_Review extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    //final static private String URL = "http://192.168.75.151:9090/input_review.php";
    final static  private String URL="http://218.236.123.14:9090/input_review.php";
    private Map<String, String> map;


    public Request_Insert_Review(String a,String w, String b, String c,String d,String e,String f,String g, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id",a);
        map.put("store_id",w);
        map.put("review_image1", b);
        map.put("review_image2", c);
        map.put("review_image3",d);
        map.put("review_like", e);
        map.put("review_mension", f);
        map.put("user_nickname", g);




    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}