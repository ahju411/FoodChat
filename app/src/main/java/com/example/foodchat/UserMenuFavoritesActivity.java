package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UserMenuFavoritesActivity extends AppCompatActivity {
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_favorites);

        //////////// 액티비티 뒤로가기
        back_btn= (ImageView) findViewById(R.id.backimg_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        ////////////





    }
}





