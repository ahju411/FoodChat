package com.example.foodchat;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

public class dbtest extends Activity {
    static RequestQueue requestQueue;
    TextView textview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbtest);

        textview1 = findViewById(R.id.tv_outPut);

        //요청 큐가 없으면 요청 큐 생성하기
        //나중에 여기다가 데이터 담으면 알아서!!!!!!! 통신함 ㅋ
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        login();

    }

    public void login() {
        //php url 입력
        String URL = "http://foodchat.dothome.co.kr/load_UserDB.php";

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
                            String item = jsonObject.getString("Data1");
                            String item2 = jsonObject.getString("Data2");
                            String item3 = jsonObject.getString("Data3");

                            Log.v("불러왔니?",item);
                            Log.v("불러왓니?2",item2);
                            Log.v("불러왔니?3",item3);
                            textview1.setText("불러왔니 첫번째 : "+item+" 두번째는? :"+ item2 + " 세번째는? " + item3);
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

}