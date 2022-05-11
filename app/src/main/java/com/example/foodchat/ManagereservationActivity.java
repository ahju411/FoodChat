package com.example.foodchat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagereservationActivity extends AppCompatActivity {
    private RecyclerView rv;
    //private ManageReviewAdapter adpt;
    private ArrayList<ItemManageReservation> items;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);
       // init();
       // getData();

    }
//    private void init(){
//        rv = findViewById(R.id.recycler_storeReview);
//
//        //어댑터 세팅
//        adpt = new ManageReviewAdapter(ViewValue.MANAGERESERVATION);
//        //레이아웃 방식 지정
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        rv.setAdapter(adpt);
//        rv.setLayoutManager(manager);
//    }
//    private void getData(){
//        ItemManageReservation items = new ItemManageReservation("어쩔","010-1234-5678","성인0명|아동0명","2022-05-09-13:00");
//        adpt.setItems(items);
//    }

}
