package com.example.foodchat;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KaKaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"a7dd5271a9e9de5da9ffaf556ef49f18");
    }
}
