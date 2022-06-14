package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserMenuReservationActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ManageReservationAcceptAdapter adpt;
    private ArrayList<ItemManageReservation> reser_item;
    ImageView back_btn;
    private String logining_user_id,logining_user_nickname;
    private RequestQueue queue;
    private LoadingDialogBar loadingDialogBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_reservation);
        Intent getintent = getIntent();
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("유저아이디:"+logining_user_id);
        System.out.println("유저닉네임:"+logining_user_nickname);


        //////////// 액티비티 뒤로가기
        back_btn= (ImageView) findViewById(R.id.backimg_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        ////////////

        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();;
        }

        getItem();


        adpt = new ManageReservationAcceptAdapter();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.my_reser_recycle);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);




    }

    private void getItem() { // 식당 리스트 UI수정하는거

        reser_item = new ArrayList<>();

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
                            String item = jsonObject.getString("reservation_storeaddress");
                            String item2 = jsonObject.getString("reservation_date");
                            String item3 = jsonObject.getString("reservation_time");
                            String item4 = jsonObject.getString("reservation_people");
                            String item5 = jsonObject.getString("reservation_check");
                            String item6 = jsonObject.getString("user_id");
                            String item7 = jsonObject.getString("user_nickname");
                            String item8 = jsonObject.getString("reservation_storename");
                            int item9 = jsonObject.getInt("reservation_id");

                            reser_item.add(new ItemManageReservation(item, item2, item3, item4,
                                    item5, item6, item7, item8, item9));
                            adpt.notifyDataSetChanged();

                            adpt.setItemManageReservations(reser_item);
                            loadingDialogBar.HideDialog();


                            loadingDialogBar.HideDialog();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                            loadingDialogBar.HideDialog();;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();;
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_user_reservation requestRegister = new Request_get_user_reservation(logining_user_id, responseListener);
        queue = Volley.newRequestQueue(UserMenuReservationActivity.this);
        queue.add(requestRegister);


    }
















}





