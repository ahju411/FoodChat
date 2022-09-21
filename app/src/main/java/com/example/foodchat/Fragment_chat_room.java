package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchat.dummy.DummyContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class Fragment_chat_room extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Chat_room_info> chatting_room_list = new ArrayList<>();
    Chat_Room_Adapter chat_room_adapter = new Chat_Room_Adapter(chatting_room_list);
    private GestureDetector gestureDetector; // 다양한 터치 이벤트를 처리하는 클래스, 길게누르기, 두번누르기 등등..

    //Firebase 를 이용합시다!
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_room);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.chat_room_re);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chat_room_adapter);



        // 리사이클러뷰 아이템 터치시 이벤트 구현
        chat_room_adapter.setOnItemClickListener(new AddChattingListener() {
            @Override
            public void onItemClick(Chat_Room_Adapter.ViewHolder holder, View view, int position) {
                Chat_room_info item = chat_room_adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), Chatting_Window.class);
                intent.putExtra("Chatting_room_id",chatting_room_list.get(position));
                intent.putExtra("currentposition",position);

                startActivity(intent);


            }
        });





//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                View child_view = rv.findChildViewUnder(e.getX(), e.getY());
//                if(child_view != null  && gestureDetector.onTouchEvent(e)){
//                    int currentposition = rv.getChildAdapterPosition(child_view);
//                    Log.i(getClass().toString(), "Touch position : " + currentposition);
//                    Intent intent = new Intent(getApplicationContext(), Chatting_Window.class);
//                    intent.putExtra("currentposition", currentposition);
//                    startActivity(intent);
//
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });


        //Firebase 에서 저장된 정보 가져오기

        databaseReference.child("MemberData").child(Restaurant_List_Home.logining_user_nickname).child("Chat_room_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Chat_room_info chat_room_info = snapshot.getValue(Chat_room_info.class);
                Log.d("아이디1",chat_room_info.id1);
                Log.d("아이디2",chat_room_info.id2);
                Log.d("마지막 메시지",chat_room_info.last_message);
                Log.d("아이디1",chat_room_info.id1);
                Log.d("아이디1",chat_room_info.id1);
                chatting_room_list.add(chat_room_info);
                chat_room_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        databaseReference.child("MemberData").child(Restaurant_List_test.logining_user_nickname).child("Chat_room_list").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded( DataSnapshot dataSnapshot,  String s) {
//                Log.i("채팅목록", "추가가 있어요");
//                Chat_room_info chat_room_info = dataSnapshot.getValue(Chat_room_info.class);
//
//
//
//                chatting_room_list.add(new Chat_room_info(chat_room_info.id2,chat_room_info.last_message_time,chat_room_info.last_message));
//
//                chat_room_adapter.notifyDataSetChanged();
//                //recyclerView.setAdapter(chat_room_adapter);
//
//
//                //Log.i("친구목록" , getData.getMember_id() + " / " + getData.getMember_password());
//
//            }
//
//            @Override
//            public void onChildChanged( DataSnapshot dataSnapshot,  String s) {
//                Log.i("채팅목록", "변화가 있어요");
//                Chat_room_info chat_room_info = dataSnapshot.getValue(Chat_room_info.class);
//                int i=0;
//                for(; i<chatting_room_list.size(); i++){
//                    if(chatting_room_list.get(i).Chatting_room_id.equals(chat_room_info.Chatting_room_id)){
//                        chatting_room_list.set(i, chat_room_info);
//                        break;
//                    }
//                }
//                chat_room_adapter.notifyItemChanged(i);
//                //recyclerView.setAdapter(chat_room_adapter);
//            }
//
//            @Override
//            public void onChildRemoved( DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {
//
//            }
//
//            @Override
//            public void onCancelled( DatabaseError databaseError) {
//
//            }
//        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if(requestCode == 333){
            //Log.i("채팅방 프레그먼트", "코드 333 억셉트 : " + data.getIntExtra("currentposition", 0) +" / "+ data.getStringExtra("Lastmessage") + " , " + data.getStringExtra("LastmessageTime"));
            /*chatting_room_list.get(data.getIntExtra("currentposition", 0)).last_message
                    = data.getStringExtra("Lastmessage");
            chatting_room_list.get(data.getIntExtra("currentposition", 0)).last_message_time
                    = data.getStringExtra("LastmessageTime");
            chat_room_adapter.notifyItemChanged(data.getIntExtra("currentposition", 0));*/


            if(resultCode == 112){

            }

        //}

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }



}
