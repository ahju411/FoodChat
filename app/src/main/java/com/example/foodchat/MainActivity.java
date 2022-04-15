package com.example.foodchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, registerbtn, guestloginbtn, registerstorebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //일단 버튼 누르면 이동하게 정의하기

        loginbtn = findViewById(R.id.login);
        registerbtn = findViewById(R.id.goregister);
        guestloginbtn = findViewById(R.id.guestlogin);
        registerstorebtn = findViewById(R.id.registstore);


        //로그인 버튼 클릭시 이벤트 설정
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //회원가입 버튼 클릭시 이벤트 설정
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //게스트 로그인 버튼 클릭시 이벤트 설정
        guestloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //식당 등록 버튼 클릭시 이벤트 설정
        registerstorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}