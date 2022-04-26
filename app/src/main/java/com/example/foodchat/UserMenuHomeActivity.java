package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UserMenuHomeActivity extends AppCompatActivity {
    ImageView back_btn,favorite_btn,reservation_btn,review_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_home);

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
                startActivity(intent);
            }
        });
        ////////

        // 예약으로 이동
        reservation_btn = (ImageView) findViewById(R.id.reservation_btn);
        reservation_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserMenuReservationActivity.class);
                startActivity(intent);
            }
        });
        ////////

        // 리뷰로 이동
        review_btn = (ImageView) findViewById(R.id.review_btn);
        review_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), UserMenuReviewActivity.class);
                startActivity(intent);
            }
        });
        ////////

    }
}





