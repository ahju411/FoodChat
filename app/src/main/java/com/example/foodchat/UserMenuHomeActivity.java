package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserMenuHomeActivity extends AppCompatActivity {
    ImageView back_btn,favorite_btn,reservation_btn,review_btn,chatting_btn;
    private int clicked_store_id;
    private String logining_user_id,logining_user_nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_home);

        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("상점아디값:"+clicked_store_id);
        System.out.println("유저아이디:"+logining_user_id);
        System.out.println("유저닉네임:"+logining_user_nickname);





        //////////// 액티비티 뒤로가기
        back_btn= (ImageView) findViewById(R.id.backimg_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        ////////////

        // 즐겨찾기로 이동
        favorite_btn = (ImageView) findViewById(R.id.favorite_btn);
        favorite_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserMenuFavoritesActivity.class);
                intent.putExtra("clicked_store_id", clicked_store_id);
                intent.putExtra("logining_user_id", logining_user_id);
                intent.putExtra("logining_user_nickname", logining_user_nickname);
                startActivity(intent);
            }
        });
        ////////

        // 예약으로 이동
        reservation_btn = (ImageView) findViewById(R.id.reservation_btn);
        reservation_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserMenuReservationActivity.class);
                intent.putExtra("logining_user_id", logining_user_id);
                intent.putExtra("logining_user_nickname", logining_user_nickname);
                startActivity(intent);
            }
        });
        ////////

        // 리뷰로 이동
        review_btn = (ImageView) findViewById(R.id.review_btn);
        review_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserMenuReviewActivity.class);
                intent.putExtra("clicked_store_id", clicked_store_id);
                intent.putExtra("logining_user_id", logining_user_id);
                intent.putExtra("logining_user_nickname", logining_user_nickname);
                startActivity(intent);
            }
        });
        ////////

        //채팅으로 이동
        chatting_btn = (ImageView) findViewById(R.id.chatting_btn);
        chatting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Fragment_chat_room.class);
                startActivity(intent);

            }
        });


    }
}





