package com.example.foodchat;

import android.graphics.Bitmap;

public class ItemManageMenu {
    String menu_name, menu_explain, menu_price;
    Bitmap menu_img;


    public ItemManageMenu(String menu_name, String menu_explain, String menu_price, Bitmap menu_img) {
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.menu_explain = menu_explain;
        this.menu_img = menu_img;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_explain() {
        return menu_explain;
    }

    public void setMenu_explain(String menu_explain) {
        this.menu_explain = menu_explain;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public Bitmap getMenu_img() {
        return menu_img;
    }

    public void setMenu_img(Bitmap menu_img) {
        this.menu_img = menu_img;
    }
}

