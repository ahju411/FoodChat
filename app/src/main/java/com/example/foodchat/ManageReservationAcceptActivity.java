package com.example.foodchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageReservationAcceptActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ManageReservationAcceptAdapter adpt;
    private ArrayList<ItemManageReservation> reser_item;
    private ImageButton backbtn;
    private ArrayList<CalendarDay> callist = new ArrayList<>();
    private LoadingDialogBar loadingDialogBar;
    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);


        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();;
        }


        rv = findViewById(R.id.store_reserva_recycle);
        backbtn = findViewById(R.id.store_reservation_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reser_item = new ArrayList<>();
        getItem();



        adpt = new ManageReservationAcceptAdapter();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.store_reserva_recycle);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);
        adpt.setOnAcceptClickListener(new ManageReservationListener() {


            @Override
            public void onAcceptClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
                System.out.println("수락눌러짐데스까");


            }

            @Override
            public void onRejectClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
                System.out.println("거절눌러짐데스까");
            }
        });
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

                            if(item5.equals("예약 접수 완료")) {
                                reser_item.add(new ItemManageReservation(item, item2, item3, item4,
                                        item5, item6, item7, item8, item9));
                                adpt.notifyDataSetChanged();

                                adpt.setItemManageReservations(reser_item);
                                loadingDialogBar.HideDialog();
                                ;
                            }
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
        Request_get_CEO_reservation requestRegister = new Request_get_CEO_reservation(logining_store_id, responseListener);
        queue = Volley.newRequestQueue(ManageReservationAcceptActivity.this);
        queue.add(requestRegister);


    }














}