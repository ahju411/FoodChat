package com.example.foodchat;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Chat_room_info implements Serializable {

    public String id1;
    public String id2;
    public String Chatting_room_id;
    public String last_message_time;
    public String last_message;
    public String last_message_id;
    public ArrayList<String> group_id;
    public Chat_room_info(String id2,String last_message_time, String last_message){
        this.id2 = id2;
        this.last_message_time = last_message_time;
        this.last_message = last_message;
    }

    Chat_room_info(){
        this.id1 = null;
        this.id2 = null;
        this.last_message_time = null;
        this.last_message = null;
        this.last_message_id = null;
        this.group_id = null;
    }

    Chat_room_info(ArrayList<String> group_id){
        this.group_id = group_id;
        this.Chatting_room_id = make_chatroom_id(group_id);
        Log.d("채팅룸",Chatting_room_id);
    }

    private String make_chatroom_id(ArrayList<String> select_friend_list) {
        String maked_id = "";

        Collections.sort(select_friend_list, String.CASE_INSENSITIVE_ORDER);
        for(int i=0; i<select_friend_list.size(); i++){
            maked_id += select_friend_list.get(i);
        }

        //이 부분은 사용 안 할듯
        Log.d("그룹채팅초대", "정렬된 아이디 : " + maked_id);
        return maked_id;

    }
}