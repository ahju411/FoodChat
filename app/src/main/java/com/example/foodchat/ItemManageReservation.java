package com.example.foodchat;

public class ItemManageReservation {
    String reservation_date,reservation_address,reservation_time,userid
            ,usernickname,storename,reservation_people,reservation_check;
    int reservation_id;

    public String getUsernickname() {
        return usernickname;
    }

    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname;
    }

    public ItemManageReservation(String reservation_address, String reservation_date, String reservation_time,
                                 String reservation_people, String reservation_check, String userid, String usernickname
    ,String storename,int reservation_id){
        this.reservation_date = reservation_date;
        this.reservation_address = reservation_address;
        this.reservation_time = reservation_time;
        this.reservation_people = reservation_people;
        this.reservation_check = reservation_check;
        this.userid = userid;
        this.usernickname = usernickname;
        this.storename=storename;
        this.reservation_id=reservation_id;

    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getReservation_people() {
        return reservation_people;
    }

    public void setReservation_people(String reservation_people) {
        this.reservation_people = reservation_people;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }



    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getReservation_address() {
        return reservation_address;
    }

    public void setReservation_address(String reservation_address) {
        this.reservation_address = reservation_address;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }



    public String getReservation_check() {
        return reservation_check;
    }

    public void setReservation_check(String reservation_check) {
        this.reservation_check = reservation_check;
    }
}
