package com.example.foodchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Restaurant_List_test extends AppCompatActivity {
    static RequestQueue requestQueue;
    private RecyclerView rv;
    private Restaurant_ListAdapter_test adpt;
    private ArrayList<Restaurant_List_Item_test> res_items;
    private ImageButton backbtn;
    private ImageView menubtn;
    private Button LV;
    private ImageView IV;
    static String[] data1 = new String[20];
    static String[] data2 = new String[20];
    static Bitmap[] data3 = new Bitmap[20];
    ProgressDialog customProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list2);
        // ProgressDialog 생성
        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customProgressDialog.show();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        getItem();
//어댑터 세팅
        adpt = new Restaurant_ListAdapter_test();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.recycler_restaurant);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);


        adpt.setRes_list_item(res_items);

        for(int d = 0 ; d< data1.length;d++) {
            res_items.add(new Restaurant_List_Item_test(data1[d],data2[d],data3[d], R.drawable.chat, R.drawable.starimg));
        }
        menubtn = (ImageView) findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//나의 메뉴로 이동
                Intent intent = new Intent(view.getContext(), UserMenuHomeActivity.class);
                startActivity(intent);
            }
        });

// 임시 식당 상세페이지 들어가기
        LV = (Button) findViewById(R.id.address);
        LV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//임시 식당 상세페이지 들어가기
                Intent intent = new Intent(view.getContext(), Restaurant_Info.class);
                startActivity(intent);
            }
        });

// 임시 웹서버 DB연결테스트 삭제 예정
        IV = (ImageView) findViewById(R.id.searchbtn);
        IV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//임시 웹 서버DB연결테스트 삭제 예정
                Intent intent = new Intent(view.getContext(), store_register_2.class);
                startActivity(intent);
            }
        });

// 임시 웹서버 DB연결테스트2 삭제 예정
        IV = (ImageView) findViewById(R.id.rangebtn);
        IV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//임시 웹 서버DB연결테스트2 삭제 예정
                Intent intent = new Intent(view.getContext(), dbtest_loda_image.class);
                startActivity(intent);
            }
        });
    }

    private void getItem() {

        res_items = new ArrayList<>();

        String URL = "http://218.236.123.14:9090/load_res.php";
        //http://218.236.123.14:9090 서버

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

//                              String item3 = jsonObject.getString("res_time");
                            String item4 = jsonObject.getString("res_image");
                            Bitmap bit = StringToBitmaps(item4);

                            Log.v("item값:",item);
                            Log.v("item값2:",item2);

                            data1[i] = item;
                            data2[i] = item2;
                            data3[i] = bit;

//                            res_items.add(new Restaurant_List_Item_test(item,item2,bit, R.drawable.chat, R.drawable.starimg));

                            System.out.println("data1 :"+item);

                            Log.v("여긴작동하나용","네에");



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






//        res_items.add(new Restaurant_List_Item_test("홍콩반점","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점2","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점3","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점4","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점5","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점6","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점7","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점88","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점9","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점10","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점11","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
//        res_items.add(new Restaurant_List_Item_test("홍콩반점12","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
    }

    public void get_store_data() {
        //php url 입력
        String URL = "http://218.236.123.14:9090/load_res.php";
        //http://218.236.123.14:9090 서버

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
                            System.out.println("jsonArray 길이:" + jsonArray.length());

//                            textview1.setText(item);
//                            textview2.setText(item2);
//                            textview3.setText(item3);
//                            textview4.setText(item5);
//
//                            ImageView1.setImageBitmap(bit);

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

