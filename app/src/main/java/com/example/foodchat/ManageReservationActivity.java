package com.example.foodchat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageReservationActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ManageReservationAdapter adpt;
    private ArrayList<ItemManageReservation> itemManageReservations;
    private ImageButton backbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);
        rv = findViewById(R.id.recycler_storeReservation);
        backbtn = findViewById(R.id.store_reservation_backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        adpt = new ManageReservationAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        itemManageReservations = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            itemManageReservations.add(new ItemManageReservation("은철","010-1234-1234","2022-05-09-13:00","성인1명"));
        }
        adpt.setItemManageReservations(itemManageReservations);
    }
}
