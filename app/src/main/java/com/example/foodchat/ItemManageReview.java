package com.example.foodchat;

public class ItemManageReview {
     String nickname_review,review_date,review_user;
     int review_img,revisitdecision_img;
     public ItemManageReview(String nickname_review,String review_date, String review_user, int review_img, int revisitdecision_img){
          this.nickname_review = nickname_review;
          this.review_date = review_date;
          this.review_user = review_user;
          this.review_img = review_img;
          this.revisitdecision_img = revisitdecision_img;
     }

     public int getRevisitdecision_img() {
          return revisitdecision_img;
     }

     public void setRevisitdecision_img(int revisitdecision_img) {
          this.revisitdecision_img = revisitdecision_img;
     }

     public String getNickname_review() {
          return nickname_review;
     }

     public void setNickname_review(String nickname_review) {
          this.nickname_review = nickname_review;
     }

     public String getReview_date() {
          return review_date;
     }

     public void setReview_date(String review_date) {
          this.review_date = review_date;
     }

     public String getReview_user() {
          return review_user;
     }

     public void setReview_user(String review_user) {
          this.review_user = review_user;
     }

     public int getReview_img() {
          return review_img;
     }

     public void setReview_img(int review_img) {
          this.review_img = review_img;
     }
}
