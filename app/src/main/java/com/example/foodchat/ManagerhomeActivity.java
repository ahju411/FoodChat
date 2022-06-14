package com.example.foodchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManagerhomeActivity extends AppCompatActivity {
    private Button store_registerbtn,manage_reviewbtn,manage_reservationbtn,manage_faqbtn;
    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;
    private String store_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        System.out.println("아이디 :"+logining_ceo_id +"비번 :"+logining_ceo_pw);
        getData();




        store_registerbtn = findViewById(R.id.store_register);
        manage_reservationbtn = findViewById(R.id.manage_reservation);
        manage_reviewbtn = findViewById(R.id.manage_review);

        manage_faqbtn=findViewById(R.id.manage_fa);
        manage_faqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Menu_faq_activity_ceo.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                intent.putExtra("logining_store_id", logining_store_id);
                startActivity(intent);
            }
        });

        store_registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageInputStoreActivity2.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                intent.putExtra("logining_store_id", logining_store_id);
                startActivity(intent);
            }
        });

        manage_reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManagereviewActivity.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                intent.putExtra("logining_store_id", logining_store_id);
                startActivity(intent);
            }
        });

        manage_reservationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageReservationActivity.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                intent.putExtra("logining_store_id", logining_store_id);
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
                            int item = jsonObject.getInt("store_id");
                            String item2 = jsonObject.getString("store_name");
                            store_name = item2;
                            System.out.println("스토어네임:"+store_name);
                            System.out.println("상점아이디:"+item);
                            logining_store_id=item;








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
        Request_Store_ID requestRegister = new Request_Store_ID(logining_ceo_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ManagerhomeActivity.this);
        queue.add(requestRegister);


    }






}
