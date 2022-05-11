package com.example.foodchat;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagereviewActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ManageReviewAdapter adpt;
    private ArrayList<ItemManageReview> itemManageReviews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_review);
        rv = findViewById(R.id.recycler_storeReview);

        //어댑터 세팅
        adpt = new ManageReviewAdapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);


        itemManageReviews = new ArrayList<>();
        for(int i=1; i<=10; i++){
            itemManageReviews.add(new ItemManageReview("어쩔","2022-05-03","노맛이에요",R.drawable.logo,R.drawable.goodimg));
        }
        adpt.setItemManageReviews(itemManageReviews);
    }
}
