package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Chatting_Window extends AppCompatActivity {
    RecyclerView chatting_Recycler;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ChatData> chatting_message = new ArrayList<>();
    ChattingAdapter chattingAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Chat_room_info Chatting_room_info;
    EditText input_message;
    int Currentposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_window);


        Toolbar toolbar = findViewById(R.id.cw_toolbar);
        setSupportActionBar(toolbar);
        //ActionBar actionBar = getSupportActionBar();
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chattingAdapter = new ChattingAdapter(chatting_message, new ChattingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postition) {
                Intent intent = new Intent(getApplicationContext(), Restaurant_List_test.class);
                intent.putExtra("Friend_Id",chatting_message.get(postition).getUserName());
                startActivity(intent);
            }
        });



        input_message = findViewById(R.id.Chatting_message);
        Chatting_room_info = (Chat_room_info) getIntent().getSerializableExtra("Chatting_room_id");
        Currentposition = getIntent().getIntExtra("currentposition", 0);

          // 그룹채팅방이 아닐때
            if (Chatting_room_info.id1.equals(Restaurant_List_test.logining_user_nickname)) {
               this.getSupportActionBar().setTitle(Chatting_room_info.id2);
            } else {
                this.getSupportActionBar().setTitle(Chatting_room_info.id1);
            }


        set_RecyclerView();

        databaseReference.child("message").child(Chatting_room_info.Chatting_room_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                Log.i("채팅 윈도우", "저장된 데이터 : " + chatData.getUserName() + ": " + chatData.getMessage());

                chatting_message.add(chatData);
                chatting_Recycler.scrollToPosition(chattingAdapter.getItemCount() -1);
                chattingAdapter.notifyItemChanged(chatting_message.size()-1);
                //chatting_Recycler.setAdapter(chattingAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), Restaurant_List_test.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                intent.putExtra("currentpositon", Currentposition);
                if(chatting_message.size() != 0) {
                    intent.putExtra("Lastmessage", chatting_message.get(chatting_message.size() - 1).getMessage());
                    intent.putExtra("LastmessageTime", chatting_message.get(chatting_message.size() - 1).sending_time);
                }

                startActivity(intent);
                //setResult(RESULT_OK, intent);
                finish();
                return true;

                //그룹대화인지 보는 설정이다 우린 필요없다.
//            case R.id.ch_menu:
//                Intent intent2 = new Intent(getApplicationContext(), Chat_menu.class);
//                intent2.putExtra("Chat_member", Chatting_room_info);
//                startActivity(intent2);
//
//                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void set_RecyclerView() {

        chatting_Recycler = findViewById(R.id.Chatting_recycler);
        chatting_Recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        chatting_Recycler.setLayoutManager(layoutManager);
        chatting_Recycler.setAdapter(chattingAdapter);
    }

    public void Sending_message(View view) {
        ChatData chatData = new ChatData(Restaurant_List_test.logining_user_nickname, input_message.getText().toString());
        Calendar calendar = Calendar.getInstance();
        String sending_time = String.format("%d.%d %d시 %d분", calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        Log.d("time",sending_time);
        chatData.sending_time = sending_time;
        databaseReference.child("message").child(Chatting_room_info.Chatting_room_id).child(String.valueOf(System.currentTimeMillis())).setValue(chatData);
        Log.d("채팅룸 Sending_message",Chatting_room_info.Chatting_room_id);
        Chatting_room_info.last_message_time = sending_time;
        Chatting_room_info.last_message = input_message.getText().toString();
        Chatting_room_info.last_message_id = Restaurant_List_test.logining_user_nickname;

        // 그룹채팅방이 아닌 경우
        //databaseReference.child("MemberData").child(Restaurant_List_test.logining_user_nickname).child("Chat_room_list").child("채팅룸 아이디").setValue( Restaurant_List_test.logining_user_nickname); // 내 아이디에 채팅방정보 저장
        //databaseReference.child("MemberData").child(Restaurant_List_test.logining_user_nickname).child("Chat_room_list").child(Chatting_room_info.Chatting_room_id).setValue(Chatting_room_info); // 내 아이디에 채팅방정보 저장

        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("Chatting_room_id").setValue(Chatting_room_info.Chatting_room_id); // 내 아이디에 채팅방 정보 저장
        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("id1").setValue(Chatting_room_info.id1); // 내 아이디에 채팅방 정보 저장
        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("id2").setValue(Chatting_room_info.id2); // 내 아이디에 채팅방 정보 저장
        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message").setValue(Chatting_room_info.last_message); // 내 아이디에 채팅방 정보 저장
        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message_id").setValue(Chatting_room_info.last_message_id); // 내 아이디에 채팅방 정보 저장
        databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message_time").setValue(Chatting_room_info.last_message_time); // 내 아이디에 채팅방 정보 저장

        if (Chatting_room_info.id1.equals(Restaurant_List_test.logining_user_nickname)) {
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("Chatting_room_id").setValue(Chatting_room_info.Chatting_room_id); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("id1").setValue(Chatting_room_info.id1); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("id2").setValue(Chatting_room_info.id2); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("last_message").setValue(Chatting_room_info.last_message); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("last_message_id").setValue(Chatting_room_info.last_message_id); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id2).child("Chat_room_list").child("last_message_time").setValue(Chatting_room_info.last_message_time); // 내 아이디에 채팅방 정보 저장


        } else {
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("Chatting_room_id").setValue(Chatting_room_info.Chatting_room_id); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("id1").setValue(Chatting_room_info.id1); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("id2").setValue(Chatting_room_info.id2); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message").setValue(Chatting_room_info.last_message); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message_id").setValue(Chatting_room_info.last_message_id); // 내 아이디에 채팅방 정보 저장
            databaseReference.child("MemberData").child(Chatting_room_info.id1).child("Chat_room_list").child("last_message_time").setValue(Chatting_room_info.last_message_time); // 내 아이디에 채팅방 정보 저장
        }


        input_message.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Restaurant_List_test.now_watching_chat_room_id = Chatting_room_info.Chatting_room_id;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Restaurant_List_test.now_watching_chat_room_id = null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chatmenu, menu);
        return true;
    }


}