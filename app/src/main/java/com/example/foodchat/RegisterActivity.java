package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button registerbtn;
    private EditText email,pwd,pwdconfirm;
    private TextView confirmemail,falsechkpwd,truechkpwd,confirmpwd;
    //나중에 회원 입력 정보 받아서 DB에 넣는 용
    private static String useremail,userpwd,userpwdconfirm;


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



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //실시간 이메일 형식 확인
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValidEmail(email.getText().toString())){
                    confirmemail.setVisibility(View.INVISIBLE);
                }else{
                    confirmemail.setVisibility(View.VISIBLE);
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
                }
                else{
                    confirmpwd.setVisibility(View.VISIBLE);
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

                }else{
                    falsechkpwd.setVisibility(View.VISIBLE);
                    truechkpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerbtn = findViewById(R.id.register);
        //가입하기 버튼 클릭시 이벤트
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NicknameActivity.class);
                startActivity(intent);


                //DB에 데이터를 넣기 구현할 것
                //if(success) 회원가입 성공시
                //else 실패시 Toast띄우기
                //회원가입 종료
                //finish();


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
}
