package com.example.foodchat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class framelayout extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment_chat_room fragment_chat_room;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout);
        fragmentManager = getSupportFragmentManager();

        fragment_chat_room = new Fragment_chat_room();

        transaction = fragmentManager.beginTransaction();





        transaction.replace(R.id.frameLayout,fragment_chat_room);
    }
}
