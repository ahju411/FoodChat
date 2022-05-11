package com.example.foodchat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddmenuActivity extends AppCompatActivity {
    private ImageButton backbtn;
    private AddmenuAdapter adpt;
    private ArrayList<ItemManageMenu> itemManageMenus;
    private RecyclerView rv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_addmenu);
        rv = findViewById(R.id.recycler_menu);

        backbtn = findViewById(R.id.store_menu_backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getItem();

        //어댑터 세팅
        adpt = new AddmenuAdapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);



        adpt.setItemManageMenus(itemManageMenus);



    }

    private void getItem() {
        itemManageMenus = new ArrayList<>();

        itemManageMenus.add(new ItemManageMenu("짜장면","누구나 즐길 수 있는 짜장면","5000원",R.drawable.imgfood));

    }
}
