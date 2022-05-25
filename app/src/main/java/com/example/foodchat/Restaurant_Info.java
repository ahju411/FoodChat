package com.example.foodchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant_Info extends AppCompatActivity {
    Button go_review_btn,go_reservation_btn,go_map_btn;
    private int clicked_store_id;
    private TextView res_name,res_address,res_time,res_image,res_mension;
    private ImageView imageView;
    LoadingDialogBar loadingDialogBar;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);


        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");


        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        res_name = findViewById(R.id.store_name);
        res_address = findViewById(R.id.store_address);
        res_time = findViewById(R.id.store_time);
        res_mension = findViewById(R.id.store_mension);
        imageView = findViewById(R.id.imageView2);

        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        System.out.println("상점아디값:"+clicked_store_id);


        getData();




















        go_review_btn = (Button) findViewById(R.id.go_review_btn);
        go_review_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //리뷰하기로 이동
                Intent intent = new Intent(view.getContext(), Restaurant_Review.class);
                startActivity(intent);
            }
        });

        go_reservation_btn = (Button) findViewById(R.id.go_reservation_btn);
        go_reservation_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //예약하기로 이동
                Intent intent = new Intent(view.getContext(), Restaurant_Reservation.class);
                startActivity(intent);
            }
        });

        go_map_btn = (Button) findViewById(R.id.go_map_btn);
        go_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Restaurant_map.class);
                intent.putExtra("map_address",res_address.getText().toString());
                Log.d("식당 주소: info",res_address.getText().toString());
                startActivity(intent);
            }
        });





    }

    public void getData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("store_name");
                            String item2 = jsonObject.getString("store_address");
                            String item3 = jsonObject.getString("store_time");
                            String item4 = jsonObject.getString("store_images");
                            Bitmap bit2 = StringToBitmaps(item4);
                            imageView.setImageBitmap(bit2); // 액티비티에 이미지 표시

                            String item5 = jsonObject.getString("store_mension");

                            res_name.setText(item);
                            res_address.setText(item2);
                            res_time.setText(item3);
                            res_mension.setText(item5);
                            loadingDialogBar.HideDialog();;

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
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_store_info requestRegister = new Request_store_info(clicked_store_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Restaurant_Info.this);
        queue.add(requestRegister);


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





