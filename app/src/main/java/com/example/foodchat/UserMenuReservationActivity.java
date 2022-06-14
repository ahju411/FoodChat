package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UserMenuReservationActivity extends AppCompatActivity {
    ImageView back_btn;
    private String logining_user_id,logining_user_nickname;
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





    }
}





