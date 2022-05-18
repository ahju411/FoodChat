package com.example.foodchat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
    LoadingDialogBar loadingDialogBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list2);
        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는중입니다.");


        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        getItem(); // 리사이클러뷰 아이템넣기

//
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                loadingDialogBar.HideDialog();
//            }
//        }, 2000);// 2초 딜레이 준후 로딩창 종료
//



//어댑터 세팅
        adpt = new Restaurant_ListAdapter_test();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.recycler_restaurant);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);



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

    private void getItem() { // 식당 리스트 UI수정하는거

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

                            res_items.add(new Restaurant_List_Item_test(item,item2,bit, R.drawable.chat, R.drawable.starimg)); //리스트 식당호출
                            adpt.notifyDataSetChanged();

                            adpt.setRes_list_item(res_items);
                            loadingDialogBar.HideDialog();;
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

    }



    public static Bitmap StringToBitmaps(String image) { // 서버에서 이미지 가져온거 비트맵으로 전환하는 함수
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

