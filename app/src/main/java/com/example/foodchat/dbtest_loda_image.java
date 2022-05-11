package com.example.foodchat;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class dbtest_loda_image extends Activity {
    static RequestQueue requestQueue;
    TextView textview1,textview2,textview3,textview4;
    ImageView ImageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbtest);

        textview1 = findViewById(R.id.t1);
        textview2 = findViewById(R.id.t2);
        textview3 = findViewById(R.id.t3);
        textview4 = findViewById(R.id.t4);
        ImageView1 = findViewById(R.id.i1);

        //요청 큐가 없으면 요청 큐 생성하기
        //나중에 여기다가 데이터 담으면 알아서!!!!!!! 통신함 ㅋ
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        login();

    }

    public void login() {
        //php url 입력
        String URL = "http://10.200.42.99/load_res.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("작동대냐","응?22");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("작동대냐","응?");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("res_name");
                            String item2 = jsonObject.getString("res_address");
                            String item3 = jsonObject.getString("res_time");
                            String item4 = jsonObject.getString("res_image");
                            Bitmap bit = StringToBitmaps(item4);

                            String item5 = jsonObject.getString("res_mension");


                            textview1.setText(item);
                            textview2.setText(item2);
                            textview3.setText(item3);
                            textview4.setText(item5);

                            ImageView1.setImageBitmap(bit);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray ) { // Byte >> 이미지 변환
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public static Bitmap StringToBitmaps(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            // Base64 코드를 디코딩하여 바이트 형태로 저장
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            // 바이트 형태를 디코딩하여 비트맵 형태로 저장
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }



}