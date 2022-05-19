package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NicknameActivity extends AppCompatActivity {
    private Button start;
    private EditText nickname;
    private int userid;
    private String usernick;
    private UserDao mUserDao;
    private String user_id,user_pw,user_nickname,user_favorites;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname);
        Intent getintent = getIntent();
        user_id = getintent.getStringExtra("user_id");
        user_pw = getintent.getStringExtra("user_pw");
        System.out.println(user_id+user_pw);


//        UserDB database = Room.databaseBuilder(getApplicationContext(), UserDB.class, "FoodChat_db")
//                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
//                .allowMainThreadQueries() // Main Thread에서 DB에 IO를 가능하게 함
//                .build();

//        mUserDao = database.userDao();
//
        start = findViewById(R.id.nickstart);
        nickname = findViewById(R.id.setnickname);





        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                user_nickname = nickname.getText().toString();
                user_favorites="";
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NicknameActivity.this, Restaurant_List_test.class);
                                intent.putExtra("logining_user_id", user_id);
                                intent.putExtra("logining_user_pw", user_pw);
                                intent.putExtra("logining_user_nickname", user_nickname);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }; // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(user_id,user_pw,user_nickname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(NicknameActivity.this);
                queue.add(registerRequest);



            }
        });
    }
}
