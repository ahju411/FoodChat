package com.example.foodchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMenuFavoritesActivity extends AppCompatActivity {
    ImageView back_btn;
    private RecyclerView rv;
    private RequestQueue queue,queue2;
    private Restaurant_ListAdapter_test adpt;
    private int clicked_store_id;
    private String logining_user_id;
    private String logining_user_nickname;
    private List<Integer> favorite_store_id= new ArrayList<Integer>();
    private ArrayList<Restaurant_List_Item_test> res_items2;
    private LoadingDialogBar loadingDialogBar;
    private String clicked_store_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_favorites);

        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("상점아디값:"+clicked_store_id);
        System.out.println("유저아이디:"+logining_user_id);
        System.out.println("유저닉네임:"+logining_user_nickname);
        res_items2 = new ArrayList<>();

        //////////// 액티비티 뒤로가기
        back_btn= (ImageView) findViewById(R.id.backimg_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        ////////////


        // ProgressDialog 생성ㅅ
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");
        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        if(queue2 == null){
            queue2 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();
        }




        getFavorite();// 유저 즐겨찾기 가져오기
        getItem(); // 리사이클러뷰 아이템넣기


        //어댑터 세팅
        adpt = new Restaurant_ListAdapter_test();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.my_favorite_);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        // ============어댑터 클릭리스너 구현 ===========
        adpt.setRes_list_item(res_items2);
        adpt.setListClickListener(new Restaurant_listListener() {

            // 뭐 이름이나 사진등 누르면
            @Override
            public void onListClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position) {
                Restaurant_List_Item_test item= adpt.getItem(position);
                clicked_store_id = item.getRes_id();

                //해당 식당페이지로 이동
                Intent intent = new Intent(view.getContext(), Restaurant_Info.class);
                intent.putExtra("clicked_store_id", clicked_store_id);
                intent.putExtra("logining_user_id", logining_user_id);
                intent.putExtra("logining_user_nickname", logining_user_nickname);
                startActivity(intent);


                //상세페이지 띄워서 item에서 get을 통해 상세페이지로 넘겨서 띄우면 끝

            }

            // 즐겨찾기 클릭시
            public void onStarClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position) {
                System.out.println("즐겨찾기클릭22");
                Restaurant_List_Item_test item= adpt.getItem(position);
                clicked_store_id = item.getRes_id();
                // 즐겨찾기되어있는거 클릭했다면? >> 즐겨찾기 삭제할건지 물어보기
                int check_favorite=0;
                int total = favorite_store_id.size();// arrayList의 요소의 갯수를 구한다.
                for (int index = 0; index < total; index++) {
                    if(clicked_store_id==favorite_store_id.get(index)){
                        show_1(position);
                        check_favorite=1;
                    }
                }

                if(check_favorite==0){
                    show_2(position);
                }


            }
            // 채팅 클릭시
            @Override
            public void onChatClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position) {
                System.out.println("채팅클릭22");
                Restaurant_List_Item_test item = adpt.getItem(position);
                clicked_store_name = item.getRes_name();
                String ChattingRoom_id;
                //으으으음 사용자 입장에서 누른거니까 목록은 필요가 없을 것 같다.
                Intent intent = new Intent(view.getContext(),Chatting_Window.class);

                //채팅방 이름을 상대방 아이디+내아이디의 조합으로 만드는데, 알파벳 순서로 만든다.

                //여기서 fi_id는 식당 이름을 뜻한다. 오류나면 수정할 것 해당 포지션의 식당 이름 가져와야하는데



                //여기서 login id가 사실상 내 닉네임이다. 그걸 가져오면 된다.
                //유은철이 수정해야지
                if(logining_user_id.compareTo(clicked_store_name)>0){ // A가 B보다 큰경우
                    ChattingRoom_id = logining_user_nickname + clicked_store_name;
                }else {
                    ChattingRoom_id =clicked_store_name + logining_user_nickname;
                }



                // 채팅방 기본 정보를 담는 부분
                Chat_room_info chat_room_info = new Chat_room_info();
                chat_room_info.id1 = logining_user_nickname; // 대화상대 1
                chat_room_info.id2 = clicked_store_name; // 대화상대 2
                chat_room_info.last_message = ""; // 초기 대화메세지값은 없음
                chat_room_info.last_message_time = ""; // 공백으로 채운다.
                chat_room_info.last_message_id = null;
                chat_room_info.Chatting_room_id = ChattingRoom_id; // 채팅방 아이디를 정해준다.
                intent.putExtra("Chatting_room_id", chat_room_info);
        /*databaseReference.child("MemberData").child(MainActivity.Login_id).child("Chat_room_list").child(ChattingRoom_id).setValue(chat_room_info); // 내 아이디에 채팅방정보 저장
        databaseReference.child("MemberData").child(fi_id.getText().toString()).child("Chat_room_list").child(ChattingRoom_id).setValue(chat_room_info); // 대화 상대 아이디에 채팅방 정보 저장*/
                startActivity(intent);





            }

        });




    }


    private void getFavorite(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            int item = jsonObject.getInt("store_id");
                            int item2 = jsonObject.getInt("favorite_check");
                            System.out.println("겟페브리함수작동?ㅅ");
                            favorite_store_id.add(item);
                            System.out.println("겟페르비;추가한거:"+item);






                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_favorite requestRegister = new Request_get_favorite(logining_user_id,
                responseListener);
        queue = Volley.newRequestQueue(UserMenuFavoritesActivity.this);
        queue.add(requestRegister);
    }

    private void getItem() { // 식당 리스트 UI수정하는거


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("작동대냐","응?");


                    JSONArray jsonArray = jsonObject.getJSONArray("data");



                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            int checkFavorite = 0;
                            String item = jsonObject.getString("store_name");

                            String item2 = jsonObject.getString("store_address");


                            String item4 = jsonObject.getString("store_images");
                            int item5 = jsonObject.getInt("store_id");

                            Bitmap bit = StringToBitmaps(item4);

                            int total = favorite_store_id.size();// arrayList의 요소의 갯수를 구한다.
                            for (int index = 0; index < total; index++) {
                                if(item5==favorite_store_id.get(index)){
                                    res_items2.add(new Restaurant_List_Item_test(item,item2,item5,bit, R.drawable.chat, R.drawable.staryellowimg));
                                    checkFavorite =1;
                                    adpt.notifyDataSetChanged();

                                    adpt.setRes_list_item(res_items2);
                                    loadingDialogBar.HideDialog();
                                    ;
                                    System.out.println("data1 :" + item);

                                    Log.v("여긴작동하나용", "네에");
                                }
                            }

                            if(checkFavorite==0) {
                                res_items2.add(new Restaurant_List_Item_test(item, item2, item5, bit, R.drawable.chat, R.drawable.starimg)); //리스트 식당호출
                                adpt.notifyDataSetChanged();

                                adpt.setRes_list_item(res_items2);
                                loadingDialogBar.HideDialog();
                                ;
                                System.out.println("data1 :" + item);

                                Log.v("여긴작동하나용", "네에");
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                            loadingDialogBar.HideDialog();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();

                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_user_store_favorite requestRegister = new Request_get_user_store_favorite(logining_user_id,
                responseListener);
        queue2 = Volley.newRequestQueue(UserMenuFavoritesActivity.this);
        queue2.add(requestRegister);

    }

    public static Bitmap StringToBitmaps(String image) { // 서버에서 이미지 가져온거 비트맵으로 전환하는 함수
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            // Base64 코드를 디코딩하여 바이트 형태로 저장
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            // 바이트 형태를 디코딩하여 비트맵 형태로 저장
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }




    void show_1(int position) // 즐겨찾기 된놈의 버튼을 클릭시
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("즐겨찾기");
        builder.setMessage("해당 가게를 즐겨찾기 해제를 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        show_1_met(position);

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    void show_2(int position) // 즐겨찾기 안된 놈의 버튼을 클릭시
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("즐겨찾기");
        builder.setMessage("해당 가게를 즐겨찾기 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        show_2_met(position);

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void show_1_met(int position) { // 즐겨찾기 된놈 버튼 > 예 > 즐겨찾기 해제기능 수행
        Restaurant_List_Item_test item= adpt.getItem(position);
        res_items2.remove(position);
        adpt.notifyDataSetChanged();
        favorite_store_id.remove(new Integer(clicked_store_id));

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"즐겨찾기 해제 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"즐겨찾기 해제 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_delete_favorite requestRegister = new Request_delete_favorite(logining_user_id,clicked_store_id,
                responseListener);
        queue = Volley.newRequestQueue(UserMenuFavoritesActivity.this);
        queue.add(requestRegister);

    }


    public void show_2_met(int position) { // 즐겨찾기 안된놈 버튼 > 예 > 즐겨찾기기능수행
        Restaurant_List_Item_test item= adpt.getItem(position);
        String s_name = item.getRes_name();
        String s_info = item.getRes_info();
        int s_id= item.getRes_id();
        Bitmap s_bit = item.getLogo_img();
        int s_chatimg = item.getChat_img();
        res_items2.set(position,new Restaurant_List_Item_test(s_name,s_info,s_id,s_bit,s_chatimg,R.drawable.staryellowimg));
        item.setStar_img(R.drawable.staryellowimg);
        adpt.notifyDataSetChanged();
        favorite_store_id.add(clicked_store_id);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"즐겨찾기 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"즐겨찾기 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_input_user_favorite requestRegister = new Request_input_user_favorite(logining_user_id,clicked_store_id,
                responseListener);
        queue = Volley.newRequestQueue(UserMenuFavoritesActivity.this);
        queue.add(requestRegister);

    }












}





