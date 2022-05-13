package com.example.foodchat;

public class ItemManageReservation {
    String reservation_name,reservation_phone,reservation_number,reservation_date;
    public ItemManageReservation(String reservation_name,String reservation_phone,String reservation_number,String reservation_date){
        this.reservation_date = reservation_date;
        this.reservation_name = reservation_name;
        this.reservation_number = reservation_number;
        this.reservation_phone = reservation_phone;

    }

    public String getReservation_name() {
        return reservation_name;
    }

    public void setReservation_name(String reservation_name) {
        this.reservation_name = reservation_name;
    }

    public String getReservation_phone() {
        return reservation_phone;
    }

    public void setReservation_phone(String reservation_phone) {
        this.reservation_phone = reservation_phone;
    }

    public String getReservation_number() {
        return reservation_number;
    }

    public void setReservation_number(String reservation_number) {
        this.reservation_number = reservation_number;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }
}
