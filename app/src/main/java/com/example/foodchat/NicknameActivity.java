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

import java.util.HashMap;
import java.util.Map;

public class NicknameActivity extends AppCompatActivity {
    private Button start;
    private EditText nickname;
    private int userid;
    private String usernick;
    private UserDao mUserDao;
    String url = "http://218.236.123.14:9090/input_users.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname);
        Intent getintent = getIntent();
        String id = getintent.getStringExtra("id");
        String pw = getintent.getStringExtra("pw");
        System.out.println(id+pw);


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
//                User user = new User();
//                usernick = nickname.getText().toString();
//                Log.d("id", String.valueOf(userid));
//                Log.d("wantnick",usernick);
//                mUserDao.UserChangeNick(usernick,userid);
//                Intent intent = new Intent(view.getContext(), restaurant_list.class);
//                startActivity(intent);
//                finish();
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplication(),"회원가입 성공!",Toast.LENGTH_SHORT).show();
                        Log.v("들어감","들어감");
                        Intent intent = new Intent(view.getContext(), Restaurant_List_test.class);
                        intent.putExtra("id", id);
                        intent.putExtra("nickname",String.valueOf(nickname.getText()));
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                        Toast.makeText(getApplication(),"안들어감",Toast.LENGTH_SHORT).show();
                        Log.v("안들어감","안들어감");
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> map = new HashMap<>();
                        // 1번 인자는 PHP 파일의 $_POST['']; 부분과 똑같이 해줘야 한다
                        map.put("user_id", String.valueOf(id));
                        map.put("user_pw", String.valueOf(pw));
                        map.put("user_nickname", String.valueOf(nickname.getText()));
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
            }
        });
    }
}
