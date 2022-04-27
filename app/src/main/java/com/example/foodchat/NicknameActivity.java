package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class NicknameActivity extends AppCompatActivity {
    private Button start;
    private EditText nickname;
    private int userid;
    private String usernick;
    private UserDao mUserDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname);
        Intent nicknameintent = getIntent();
        String firstnick = nicknameintent.getStringExtra("닉네임");
        userid = nicknameintent.getIntExtra("id",0);


        UserDB database = Room.databaseBuilder(getApplicationContext(), UserDB.class, "FoodChat_db")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() // Main Thread에서 DB에 IO를 가능하게 함
                .build();

        mUserDao = database.userDao();

        start = findViewById(R.id.nickstart);
        nickname = findViewById(R.id.setnickname);

        nickname.setText(firstnick);



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                usernick = nickname.getText().toString();
                Log.d("id", String.valueOf(userid));
                Log.d("wantnick",usernick);
                mUserDao.UserChangeNick(usernick,userid);
                Intent intent = new Intent(view.getContext(), restaurant_list.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
