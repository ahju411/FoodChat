package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NicknameActivity extends AppCompatActivity {
    private Button start;
    private EditText nickname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname);
        Intent nicknameintent = getIntent();
        String firstnick = nicknameintent.getStringExtra("닉네임");

        start = findViewById(R.id.nickstart);
        nickname = findViewById(R.id.setnickname);

        nickname.setText(firstnick);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), restaurant_list.class);
                startActivity(intent);
            }
        });
    }
}
