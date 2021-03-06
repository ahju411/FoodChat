package com.example.foodchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    Button loginbtn, registerbtn, guestloginbtn, registerstorebtn;
    ImageButton kakaologin;
    private String checkNickname;
    private UserDao mUserDao;
    private ManagerDao mManagerDao;
    private final static String TAG = "유저";
    private EditText ed_id,ed_pw;
    private List<String>  user_nickname= new ArrayList<String>();
    private List<String>  user_id_list= new ArrayList<String>();
    private List<String>  store_name= new ArrayList<String>();
    private RequestQueue queue5;
    private String kakaoId;
    private LoadingDialogBar loadingDialogBar;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.home);
        loadingDialogBar = new LoadingDialogBar(this);

        getUserAlldata();
        getCEOAlldata();




        //일단 버튼 누르면 이동하게 정의하기



        loginbtn = findViewById(R.id.login);
        kakaologin = findViewById(R.id.kakao);
        registerbtn = findViewById(R.id.goregister);
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
                            if (success) { // 유저로그인에 성공한 경우
                                String user_id = jsonObject.getString("user_id");
                                String user_pw = jsonObject.getString("user_pw");
                                String user_nickname = jsonObject.getString("user_nickname");

                                Toast.makeText(getApplicationContext(),"유저 로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Restaurant_List_test.class);
                                intent.putExtra("logining_user_id", user_id);
                                intent.putExtra("logining_user_pw", user_pw);
                                intent.putExtra("logining_user_nickname", user_nickname);


                                SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = mPref.edit();
                                editor.putString("LoginId", user_nickname);
                                editor.commit();


                                startActivity(intent);
                            } else { // 유저로그인에 실패한 경우 사장로그인 체크
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            // 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                                            System.out.println("hongchul" + response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success) { // 사장로그인에 성공한 경우
                                                String ceo_id = jsonObject.getString("ceo_id");
                                                String ceo_pw = jsonObject.getString("ceo_pw");
                                                Restaurant_List_test.logining_user_nickname = ceo_id;


                                                Toast.makeText(getApplicationContext(),"사장 로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, ManagerhomeActivity.class);
                                                intent.putExtra("logining_ceo_id", ceo_id);
                                                intent.putExtra("logining_ceo_pw", ceo_pw);
                                                SharedPreferences mPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = mPref.edit();
                                                editor.putString("LoginId", ceo_id);
                                                startActivity(intent);
                                            } else { // 사장로그인에 실패한 경우
                                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                                return;

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                Request_CEO_LoginRequest loginRequest = new Request_CEO_LoginRequest(userID, userPass, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                queue.add(loginRequest);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Request_User_Login requestUserLogin = new Request_User_Login(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(requestUserLogin);



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



        //식당 등록 버튼 클릭시 이벤트 설정
        registerstorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), RegisterManagerActivity.class);
                startActivity(intent);

            }
        });

    }

    public void getUserAlldata() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    System.out.println("길이"+jsonArray.length());

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("user_nickname");
                            user_nickname.add(item);
                            String item2 = jsonObject.getString("user_id");
                            user_id_list.add(item2);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");

                        }
                    }
//                    databaseReference.child("MemberData").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                            for(DataSnapshot data : snapshot.getChildren()){
//                                for(String user: user_nickname){
//                                    String str = data.child(user).getValue().toString();
//                                    if(!str.equals(user)){
//                                        databaseReference.child("MemberData").child(user).setValue(user);
//
//                                    }
//
//                                }
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                    System.out.println(user_nickname);


                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_User_AllData requestRegister = new Request_User_AllData(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(requestRegister);

    }

    public void getCEOAlldata() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    System.out.println("길이"+jsonArray.length());

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("store_name");
                            store_name.add(item);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");

                        }
                    }


                    System.out.println(store_name);

                } catch (JSONException e) {
                    e.printStackTrace();


                }


//                databaseReference.child("MemberData").addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        for(DataSnapshot data : snapshot.getChildren()){
//                            for(String user: store_name){
//                                String str = data.child(user).getValue().toString();
//                                Log.d("자자 뭐하지",str);
//                                if(!str.equals(user)){
//                                    databaseReference.child("MemberData").child(user).setValue(user);
//
//                                }
//
//                            }
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


            }

        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_Ceo_AllData requestRegister = new Request_Ceo_AllData(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(requestRegister);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                updateKakaoLoginUI();
                return null;
            }
        });
    }

    private void updateKakaoLoginUI() {
        //카카오 UI 가져오는 메소드
        UserApiClient.getInstance().me(new Function2<com.kakao.sdk.user.model.User, Throwable, Unit>() {
            @Override
            public Unit invoke(com.kakao.sdk.user.model.User user, Throwable throwable) {
                if (user != null) {
                    System.out.println("찍혔냐아아아아아아");


                    //잘 전달된 경우(로그인이 된 경우)
                    int checkUser = 0;
                    int checkIndex = 0;
                    kakaoId = String.valueOf(user.getId());
                    Log.i(TAG, "id" + user.getId()); //유저의 고유 아이디 불러오기
                    Log.i(TAG, "nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    int total = user_id_list.size();// arrayList의 요소의 갯수를 구한다.
                    for (int index = 0; index < total; index++) {
                        if(String.valueOf(user.getId()).equals(user_id_list.get(index))){
                            checkUser = 1;
                            checkIndex = index;
                            System.out.println("체크유저1이 됬써요");

                        }
                    }
                    //데이터 삽입 if문으로 이미 있으면 다시 안 되게 만들기
                    if (checkUser==1) {//이미 id가 있다면

                        Log.d("Test", "id가 있다면");
                        checkNickname = user_nickname.get(checkIndex);
                        int check_nickname = 0;


                        Intent intent = new Intent(MainActivity.this, Restaurant_List_test.class);
                        intent.putExtra("logining_user_id", kakaoId);
                        intent.putExtra("logining_user_pw", "1234");
                        intent.putExtra("logining_user_nickname",checkNickname);

                        startActivity(intent);
                    }
                    else {
                            Intent intent = new Intent(MainActivity.this, NicknameActivity.class);
//                            intent.putExtra("user_nickname", user.getKakaoAccount().getProfile().getNickname());
                            System.out.println("카카오아디보내기전11"+kakaoId);
                            intent.putExtra("user_id", kakaoId);
                            intent.putExtra("user_pw", "1234");
                            Log.d("Test", "닉네임 설정 안 했을 때");


                            startActivity(intent);
                        }



//                        //Boolean nick = mUserDao.UserNick(Long.valueOf(user.getId()).intValue());
//                        int nick = mUserDao.UserNick((Long.valueOf(user.getId()).intValue()));
//                        Log.d("TEst",String.valueOf(nick));

                    }
                else {

                    System.out.println("띠용");
                    }
                    //후에 어떤 일을 할지 적기
                    //if문으로 db파서 이 유저가 이미 가입되어있으면 스킵하고 바로 메인으로 가기.


                if (throwable != null) {
                    //오류 났을 때
                    Log.w(TAG, "invoke:" + throwable.getLocalizedMessage());
                }

                return null;
            }
        });
}




    private void getUserNickname(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("user_nickname");

                            System.out.println("체크닉네임있나요"+checkNickname);



                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        };
        Request_get_user_nickname loginRequest = new Request_get_user_nickname(
                responseListener);
        queue5 = Volley.newRequestQueue(MainActivity.this);
        queue5.add(loginRequest);
    }
}

//
//    private void updateKakaoLoginUI() {
//        //카카오 UI 가져오는 메소드
//        UserApiClient.getInstance().me(new Function2<com.kakao.sdk.user.model.User, Throwable, Unit>() {
//            @Override
//            public Unit invoke(com.kakao.sdk.user.model.User user, Throwable throwable) {
//                if (user != null) {
//                    //잘 전달된 경우(로그인이 된 경우)
//                    System.out.println("찍혔냐아아아아아아");
//                    Log.i(TAG, "id" + user.getId()); //유저의 고유 아이디 불러오기
//                    Log.i(TAG, "nickname=" + user.getKakaoAccount().getProfile().getNickname());
//
//                    //데이터 삽입 if문으로 이미 있으면 다시 안 되게 만들기
//                    if (mUserDao.SelectId(Long.valueOf(user.getId()).intValue())) {//이미 id가 있다면
//                        Log.d("Test","id가 있다면");
//                        //닉네임까지 설정한 경우
//                        if (mUserDao.UserNick(Long.valueOf(user.getId()).intValue()) > 0) {
//                            Log.d("Test","닉네임 설정 했을 때");
//                            Intent intent = new Intent(MainActivity.this, restaurant_list.class);
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(MainActivity.this, NicknameActivity.class);
//                            intent.putExtra("닉네임", user.getKakaoAccount().getProfile().getNickname());
//                            intent.putExtra("id",Long.valueOf(user.getId()).intValue());
//                            Log.d("Test","닉네임 설정 안 했을 때");
//
//
//                            startActivity(intent);
//                        }
//                        //Boolean nick = mUserDao.UserNick(Long.valueOf(user.getId()).intValue());
//                        int nick = mUserDao.UserNick((Long.valueOf(user.getId()).intValue()));
//                        Log.d("TEst",String.valueOf(nick));
//
//                    } else {
//                        //처음 카카오 로그인한 경우
//                        User userdb = new User(); //객체 인스턴스 생성
//                        userdb.setId(Long.valueOf(user.getId()).intValue());
//                        //userdb.setName(user.getKakaoAccount().getProfile().getNickname());
//                        mUserDao.InsertUser(userdb);
//                    }
//                    //후에 어떤 일을 할지 적기
//                    //if문으로 db파서 이 유저가 이미 가입되어있으면 스킵하고 바로 메인으로 가기.
//
//
//                } else {//로그인 실패
//                    Log.d("loginfail","로그인 실패");
//                }
//                if (throwable != null) {
//                    //오류 났을 때
//                    Log.w(TAG, "invoke:" + throwable.getLocalizedMessage());
//                }
//
//                return null;
//            }
//        });
//    }



