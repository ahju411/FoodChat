package com.example.foodchat;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
        String URL = "http://foodchat.dothome.co.kr/load_image.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("작동대냐","응?");

                    JSONArray jsonArray = jsonObject.getJSONArray("webnautes");
                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("res_name");
                            String item2 = jsonObject.getString("res_address");
                            String item3 = jsonObject.getString("res_time");
                            String image = jsonObject.getString("res_image");
                            byte[] item4 = image.getBytes();
                            String item5 = jsonObject.getString("res_mension");

                            Log.v("불러왔니?",item);
                            Log.v("불러왓니?2",item2);
                            Log.v("불러왔니?3",item3);
                            System.out.println("불러왔니 이미지 바이트:"+item4);
                            Log.v("불러왔니?5",item5);
                            textview1.setText(item);
                            textview2.setText(item2);
                            textview3.setText(item3);
                            textview4.setText(item5);

                            Bitmap img = byteArrayToBitmap(item4);
                            System.out.println("불러왔니 이미지 바이트:"+img);
                            ImageView1.setImageBitmap(img); // 액티비티에 이미지 표시
                        } catch (JSONException e) {
                            e.printStackTrace();
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

}