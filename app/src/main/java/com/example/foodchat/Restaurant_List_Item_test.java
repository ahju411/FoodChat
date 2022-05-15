package com.example.foodchat;

public class Restaurant_List_Item_test {

    String res_name,res_info;
    int logo_img,chat_img,star_img;




    public Restaurant_List_Item_test(String res_name,String res_info,int logo_img,int chat_img,int star_img){
        this.res_name = res_name;
        this.res_info = res_info;
        this.logo_img = logo_img;
        this.chat_img = chat_img;
        this.star_img = star_img;

    }

// public int getRevisitdecision_img() {
// return revisitdecision_img;
// }
//
// public void setRevisitdecision_img(int revisitdecision_img) {
// this.revisitdecision_img = revisitdecision_img;
// }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_info() {
        return res_info;
    }

    public void setRes_info(String res_info) {
        this.res_info = res_info;
    }

    public int getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(int logo_img) {
        this.logo_img = logo_img;
    }

    public int getChat_img() {
        return chat_img;
    }

    public void setChat_img(int chat_img) {
        this.chat_img = chat_img;
    }

    public int getStar_img() {
        return star_img;
    }

    public void setStar_img(int star_img) {
        this.logo_img = star_img;
    }

}