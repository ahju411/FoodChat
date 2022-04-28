package com.example.foodchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, registerbtn, guestloginbtn, registerstorebtn;
    ImageButton kakaologin;
    private UserDao mUserDao;
    private final static String TAG = "유저";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        //일단 버튼 누르면 이동하게 정의하기

        loginbtn = findViewById(R.id.login);
        kakaologin = findViewById(R.id.kakao);
        registerbtn = findViewById(R.id.goregister);
        guestloginbtn = findViewById(R.id.guestlogin);
        registerstorebtn = findViewById(R.id.registstore);

        //DB파트
        UserDB database = Room.databaseBuilder(getApplicationContext(), UserDB.class, "FoodChat_db")
                .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                .allowMainThreadQueries() // Main Thread에서 DB에 IO를 가능하게 함
                .build();

        mUserDao = database.userDao(); //인터페이스 객체 할당

         //데이터 삽입
//        User user = new User(); //객체 인스턴스 생성
//        user.setId(1234);
//        user.setName("은철");
//        user.setAge("24");
//        user.setPhone("01012341234");
//        mUserDao.InsertUser(user);


        List<User> userList = mUserDao.getUserAll();
        //데이터 조회
        for (int i = 0; i < userList.size(); i++) {
            Log.d("Test", userList.get(i).getName() + "\n"
                    + userList.get(i).getPhone() + "\n");
        }

        //데이터 수정
        User user2 = new User(); //객체 인스턴스 생성
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

                //유은철 식당목록 테스트좀할려고 넣음 삭제예정
                Intent intent = new Intent(view.getContext(), restaurant_list.class);
                startActivity(intent);

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





