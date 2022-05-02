package com.example.foodchat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Manager {
    @PrimaryKey() //자동으로 id가 생성 카운팅된다.
    private int id; //하나의 사용자에 대한 고유 ID 값
    private String pw;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
