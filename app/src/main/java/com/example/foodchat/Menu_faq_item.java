package com.example.foodchat;

import android.graphics.Bitmap;

public class Menu_faq_item {
    String faq_Q,faq_A;
    int faq_id;


    public Menu_faq_item(String faq_Q,String faq_A,int faq_id){
        this.faq_Q = faq_Q;
        this.faq_A = faq_A;
        this.faq_id= faq_id;
    }

    public String getFaq_Q() {
        return faq_Q;
    }

    public int getFaq_id() {
        return faq_id;
    }

    public void setFaq_id(int faq_id) {
        this.faq_id = faq_id;
    }

    public void setFaq_Q(String faq_Q) {
        this.faq_Q = faq_Q;
    }

    public String getFaq_A() {
        return faq_A;
    }

    public void setFaq_A(String faq_A) {
        this.faq_A = faq_A;
    }
}

