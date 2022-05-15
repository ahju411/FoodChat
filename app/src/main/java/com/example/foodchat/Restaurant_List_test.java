package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Restaurant_List_test extends AppCompatActivity {

    private RecyclerView rv;
    private Restaurant_ListAdapter_test adpt;
    private ArrayList<Restaurant_List_Item_test> res_items;
    private ImageButton backbtn;
    private ImageView menubtn;
    private Button LV;
    private ImageView IV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list2);
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
        res_items.add(new Restaurant_List_Item_test("홍콩반점","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점2","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점3","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점4","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점5","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점6","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점7","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점88","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점9","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점10","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점11","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
        res_items.add(new Restaurant_List_Item_test("홍콩반점12","리뷰및따봉순",R.drawable.hong,R.drawable.chat,R.drawable.starimg));
    }
}