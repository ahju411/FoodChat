package com.example.foodchat;

import android.view.View;

public interface Restaurant_listListener {
    public void onListClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position);
    public void onStarClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position);
    public void onChatClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position);
}
