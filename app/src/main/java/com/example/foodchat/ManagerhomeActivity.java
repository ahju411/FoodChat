package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerhomeActivity extends AppCompatActivity {
    private Button store_registerbtn,manage_reviewbtn,manage_reservationbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home);
        Intent getintent = getIntent();
        String logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        String logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        System.out.println("아이디 :"+logining_ceo_id +"비번 :"+logining_ceo_pw);




        store_registerbtn = findViewById(R.id.store_register);
        manage_reservationbtn = findViewById(R.id.manage_reservation);
        manage_reviewbtn = findViewById(R.id.manage_review);

        store_registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageInputStoreActivity2.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                startActivity(intent);
            }
        });

        manage_reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManagereviewActivity.class);
                startActivity(intent);
            }
        });

        manage_reservationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageReservationActivity.class);
                startActivity(intent);
            }
        });
    }
}
