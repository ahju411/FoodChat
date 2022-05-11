package com.example.foodchat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ImageButton backbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_review);
        rv = findViewById(R.id.recycler_storeReview);
        backbtn = findViewById(R.id.store_review_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getItem();

        //어댑터 세팅
        adpt = new ManageReviewAdapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);



        adpt.setItemManageReviews(itemManageReviews);
    }

    private void getItem() {
        itemManageReviews = new ArrayList<>();

        itemManageReviews.add(new ItemManageReview("혜성","2022-05-11","잘 먹었습니다 ^^*",R.drawable.imgfood,R.drawable.goodimg));
        itemManageReviews.add(new ItemManageReview("은철","2022-05-11","다음에 또 올게요",R.drawable.imgfood1,R.drawable.goodimg));
        itemManageReviews.add(new ItemManageReview("짱구","2022-05-10","맛있게 먹었어요",R.drawable.imgfood2,R.drawable.goodimg));
        itemManageReviews.add(new ItemManageReview("유리","2022-05-09","주차 공간이 아쉬웠어요",R.drawable.imgfood3,R.drawable.badimg));
        itemManageReviews.add(new ItemManageReview("철수","2022-05-08","다음에 또 올게요",R.drawable.imgfood4,R.drawable.goodimg));
        itemManageReviews.add(new ItemManageReview("맹구","2022-05-05","다시 오고 싶은 맛이에요",R.drawable.imgfood5,R.drawable.goodimg));
        itemManageReviews.add(new ItemManageReview("훈이","2022-05-03","직원이 불친절하네요",R.drawable.imgfood6,R.drawable.badimg));
    }
}
