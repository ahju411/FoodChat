package com.example.foodchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, registerbtn, guestloginbtn, registerstorebtn;
    ImageButton kakaologin;
    private UserDao mUserDao;

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

        // 데이터 삽입
//        User user = new User(); //객체 인스턴스 생성
//        user.setName("은철");
//        user.setAge("24");
//        user.setPhone("01012341234");
//        mUserDao.InsertUser(user);

        List<User> userList = mUserDao.getUserAll();
        //데이터 조회
        for (int i = 0; i < userList.size(); i++) {
            Log.d("Test", userList.get(i).getName() + "\n"
                    + userList.get(i).getAge() + "\n"
                    + userList.get(i).getPhone() + "\n");

        }

        //데이터 수정
//        User user2 = new User(); //객체 인스턴스 생성
//        user2.setId(1);
//        user2.setName("김치");
//        user2.setAge("22");
//        user2.setPhone("01045671234");
//        mUserDao.UpdateUser(user2);

        //데이터 삭제
        User user3 = new User();
        user3.setId(3);
        mUserDao.DeleteUser(user3);


        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {

                }
                if (throwable != null) {

                }
                updateKakaoLoginUi();
                return null;
            }
        };

        //카카오 로그인 버튼 클릭
        kakaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,callback);
                }
            }
        });

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

            }
        });


    }
}