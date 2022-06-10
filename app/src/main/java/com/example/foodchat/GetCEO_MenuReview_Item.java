package com.example.foodchat;

import android.graphics.Bitmap;

public class GetCEO_MenuReview_Item {
    String User_nickname,date,review,ceo_review;
    int like_img;
    Bitmap photo_img1,photo_img2,photo_img3;


    public String getUser_nickname() {
        return User_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        User_nickname = user_nickname;
    }

    public String getDate() {
        return date;
    }

    public String getCeo_review() {
        return ceo_review;
    }

    public void setCeo_review(String ceo_review) {
        this.ceo_review = ceo_review;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getLike_img() {
        return like_img;
    }


    public void setLike_img(int like_img) {
        this.like_img = like_img;
    }

    public Bitmap getPhoto_img1() {
        return photo_img1;
    }

    public void setPhoto_img1(Bitmap photo_img1) {
        this.photo_img1 = photo_img1;
    }

    public Bitmap getPhoto_img2() {
        return photo_img2;
    }

    public void setPhoto_img2(Bitmap photo_img2) {
        this.photo_img2 = photo_img2;
    }

    public Bitmap getPhoto_img3() {
        return photo_img3;
    }

    public void setPhoto_img3(Bitmap photo_img3) {
        this.photo_img3 = photo_img3;
    }

    public GetCEO_MenuReview_Item(String User_nickname, String date, String review, int like_img, Bitmap photo_img1, Bitmap photo_img2,
                                  Bitmap photo_img3, String ceo_review){
        this.User_nickname = User_nickname;
        this.date = date;
        this.review = review;
        this.like_img = like_img;
        this.photo_img1 = photo_img1;
        this.photo_img2=photo_img2;
        this.photo_img3=photo_img3;
        this.ceo_review = ceo_review;

    }

// public int getRevisitdecision_img() {
// return revisitdecision_img;
// }
//
// public void setRevisitdecision_img(int revisitdecision_img) {
// this.revisitdecision_img = revisitdecision_img;
// }


}