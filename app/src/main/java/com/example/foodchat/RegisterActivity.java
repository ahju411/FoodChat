package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    static RequestQueue requestQueue;
    private Button registerbtn, registeremailbtn, registerpwbtn, registerpwconfirmbtn;
    private EditText email,pwd,pwdconfirm;
    private TextView confirmemail,falsechkpwd,truechkpwd,confirmpwd;
    //나중에 회원 입력 정보 받아서 DB에 넣는 용
    private String user_id,user_pw;
    private String[] ids= new String[3];
    private int check;
    String URL = "http://218.236.123.14:9090/load_users.php";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = findViewById(R.id.emailregi);
        pwd = findViewById(R.id.pwdregi);
        pwdconfirm = findViewById(R.id.pwdconfirm);
        confirmemail = findViewById(R.id.confirmemail);
        falsechkpwd = findViewById(R.id.falsechkpwd);
        truechkpwd = findViewById(R.id.truechkpwd);
        confirmpwd = findViewById(R.id.confirmpwd);
        registerbtn = findViewById(R.id.register);
        registeremailbtn = findViewById(R.id.registeremail);
        registerpwbtn = findViewById(R.id.registerpw);
        registerpwconfirmbtn = findViewById(R.id.registerpwconfirm);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        System.out.println("체크체크");
        System.out.println("체크체크2");



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //실시간 이메일 형식 확인
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValidEmail(email.getText().toString())){
                    confirmemail.setVisibility(View.INVISIBLE);
                    registeremailbtn.setVisibility(View.INVISIBLE);
                }else{
                    confirmemail.setVisibility(View.VISIBLE);
                    registeremailbtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //비밀번호가 특수문자나 8자리 이상이 포함되어있는지 확인
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //이 부분이 변화를 확인하고 if문으로 체크해준다.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValidPwd(pwd.getText().toString())){
                    confirmpwd.setVisibility(View.INVISIBLE);
                    registerpwbtn.setVisibility(View.INVISIBLE);
                }
                else{
                    confirmpwd.setVisibility(View.VISIBLE);
                    registerpwbtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //실시간 비밀번호 일치 확인
        pwdconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //이 부분이 변화를 확인하고 if문으로 맞을 경우와 틀릴 경우 나눠준다.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(pwd.getText().toString().equals(pwdconfirm.getText().toString()))
                {
                    falsechkpwd.setVisibility(View.INVISIBLE);
                    truechkpwd.setVisibility(View.VISIBLE);
                    registerpwconfirmbtn.setVisibility(View.INVISIBLE);

                }else{
                    falsechkpwd.setVisibility(View.VISIBLE);
                    truechkpwd.setVisibility(View.INVISIBLE);
                    registerpwconfirmbtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //가입하기 버튼 클릭시 이벤트
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().length() > 0) {
                    user_id = email.getText().toString();
                    user_pw = pwd.getText().toString();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 중복인경우
                                    Toast.makeText(getApplicationContext(),"아이디중복입니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                } else { // 회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(),"아이디 중복 없음!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, NicknameActivity.class);
                                    intent.putExtra("user_id", user_id);
                                    intent.putExtra("user_pw", user_pw);





                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }; // 서버로 Volley를 이용해서 요청을 함.
                    Request_Validate requestValidate = new Request_Validate(user_id,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(requestValidate);




            }else{
                    Toast.makeText(getApplication(),"정보를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                //DB에 데이터를 넣기 구현할 것
                //if(success) 회원가입 성공시
                //else 실패시 Toast띄우기
                //회원가입 종료
                //finish();
            }
        });

        registeremailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"이메일을 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        });
        registerpwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        });
        registerpwconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"비밀번호가 일치하는지 확인해주세요",Toast.LENGTH_SHORT).show();
            }
        });
    }









    //이메일 체크 메소드
    public static boolean isValidEmail(CharSequence target){
        return(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //비밀번호 정규식
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile( "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$"); // 8자리 ~ 16자리까지 가능

    //비밀번호 체크 메소드
    public static boolean isValidPwd(CharSequence target){
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(target);
        return(!TextUtils.isEmpty(target) && matcher.matches());
    }


    public void login() {
        //php url 입력

        //http://218.236.123.14:9090 서버

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("작동대냐","응?22");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("작동대냐","응?");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("user_id");
                            ids[i]=item;


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }





}
