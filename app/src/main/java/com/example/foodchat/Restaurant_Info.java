package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Restaurant_Info extends AppCompatActivity {
    Button go_review_btn,go_reservation_btn,go_map_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);



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
                Intent intent = new Intent(view.getContext(),Testmap.class);
                startActivity(intent);
            }
        });





    }
}





