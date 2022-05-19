package com.example.foodchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, registerbtn, guestloginbtn, registerstorebtn;
    ImageButton kakaologin;
    private UserDao mUserDao;
    private ManagerDao mManagerDao;
    private final static String TAG = "유저";
    private EditText ed_id,ed_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.home);


        //일단 버튼 누르면 이동하게 정의하기

        loginbtn = findViewById(R.id.login);
        kakaologin = findViewById(R.id.kakao);
        registerbtn = findViewById(R.id.goregister);
        guestloginbtn = findViewById(R.id.guestlogin);
        registerstorebtn = findViewById(R.id.registstore);
        ed_id = findViewById(R.id.id);
        ed_pw = findViewById(R.id.pwd);

        //DB파트
        UserDB database = Room.databaseBuilder(getApplicationContext(), UserDB.class, "FoodChat_db")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() // Main Thread에서 DB에 IO를 가능하게 함
                .build();

        mUserDao = database.userDao(); //인터페이스 객체 할당
        mManagerDao = database.managerDao();




         //데이터 삽입
//        User user = new User(); //객체 인스턴스 생성
//        user.setId(1234);
//        user.setName("은철");
//        user.setAge("24");
//        user.setPhone("01012341234");
//        mUserDao.InsertUser(user);
        Manager manager = new Manager();
        manager.setId(1234);
        manager.setPw("1234");
        mManagerDao.InsertManager(manager);


        List<User> userList = mUserDao.getUserAll();
        //데이터 조회
        for (int i = 0; i < userList.size(); i++) {
            Log.d("Test", userList.get(i).getName() + "\n"
                    + userList.get(i).getPhone() + "\n");
        }

        //데이터 수정
//        User user2 = new User(); //객체 인스턴스 생성
//        user2.setId(1234);
//        user2.setName("ㅇㅇ");
//        user2.setAge("22");
//        user2.setPhone("01045671234");
//        mUserDao.UpdateUser(user2);

        //데이터 삭제
//        User user3 = new User();
//        user3.setId(5);
//        mUserDao.DeleteUser(user3);


        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {

                }
                if (throwable != null) {

                }
                updateKakaoLoginUI();
                return null;
            }
        };

        //카카오 로그인 버튼 클릭
        kakaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }
            }
        });
        updateKakaoLoginUI();

        //로그인 버튼 클릭시 이벤트 설정
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = ed_id.getText().toString();
                String userPass = ed_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String user_id = jsonObject.getString("user_id");
                                String user_pw = jsonObject.getString("user_pw");
                                String user_nickname = jsonObject.getString("user_nickname");

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Restaurant_List_test.class);
                                intent.putExtra("logining_user_id", user_id);
                                intent.putExtra("logining_user_pw", user_pw);
                                intent.putExtra("logining_user_nickname", user_nickname);
                                startActivity(intent);
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);



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

                Intent intent = new Intent(view.getContext(), RegisterManagerActivity.class);
                startActivity(intent);

            }
        });

    }






    private void updateKakaoLoginUI() {
        //카카오 UI 가져오는 메소드
        UserApiClient.getInstance().me(new Function2<com.kakao.sdk.user.model.User, Throwable, Unit>() {
            @Override
            public Unit invoke(com.kakao.sdk.user.model.User user, Throwable throwable) {
                if (user != null) {
                    //잘 전달된 경우(로그인이 된 경우)
                    Log.i(TAG, "id" + user.getId()); //유저의 고유 아이디 불러오기
                    Log.i(TAG, "nickname=" + user.getKakaoAccount().getProfile().getNickname());

                    //데이터 삽입 if문으로 이미 있으면 다시 안 되게 만들기
                    if (mUserDao.SelectId(Long.valueOf(user.getId()).intValue())) {//이미 id가 있다면
                        Log.d("Test","id가 있다면");
                        //닉네임까지 설정한 경우
                        if (mUserDao.UserNick(Long.valueOf(user.getId()).intValue()) > 0) {
                            Log.d("Test","닉네임 설정 했을 때");
                            Intent intent = new Intent(MainActivity.this, restaurant_list.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, NicknameActivity.class);
                            intent.putExtra("닉네임", user.getKakaoAccount().getProfile().getNickname());
                            intent.putExtra("id",Long.valueOf(user.getId()).intValue());
                            Log.d("Test","닉네임 설정 안 했을 때");


                            startActivity(intent);
                        }
                        //Boolean nick = mUserDao.UserNick(Long.valueOf(user.getId()).intValue());
                        int nick = mUserDao.UserNick((Long.valueOf(user.getId()).intValue()));
                        Log.d("TEst",String.valueOf(nick));

                    } else {
                        //처음 카카오 로그인한 경우
                        User userdb = new User(); //객체 인스턴스 생성
                        userdb.setId(Long.valueOf(user.getId()).intValue());
                        //userdb.setName(user.getKakaoAccount().getProfile().getNickname());
                        mUserDao.InsertUser(userdb);
                    }
                    //후에 어떤 일을 할지 적기
                    //if문으로 db파서 이 유저가 이미 가입되어있으면 스킵하고 바로 메인으로 가기.


                } else {//로그인 실패
                    Log.d("loginfail","로그인 실패");
                }
                if (throwable != null) {
                    //오류 났을 때
                    Log.w(TAG, "invoke:" + throwable.getLocalizedMessage());
                }

                return null;
            }
        });
    }
}





