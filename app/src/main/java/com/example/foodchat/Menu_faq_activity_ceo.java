package com.example.foodchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Menu_faq_activity_ceo extends AppCompatActivity implements Menu_dialog_faq.Menu_faq_dialogListener,Menu_faq_fix_dialog.Menu_faq_dialogListener {
    private Button plus_btn;
    private ImageButton backbtn,addmenubtn;
    private Menu_faq_Adapter adpt;
    private ArrayList<Menu_faq_item> itemManageMenus;
    private RecyclerView rv;
    private String logining_ceo_id,logining_ceo_pw;
    private String string_q, string_a; // 서버에 보낼것들
    private int string_id;
    private String[] menu = new String[10];
    private int logining_store_id,menu_id=999999;
    public static String menu_image2="aa";
    private RequestQueue queue,queue2,queue3,queue4;
    LoadingDialogBar loadingDialogBar;
    private String send_q,send_a;
    private int send_id;
    private int posi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);



        System.out.println("아이디:"+logining_ceo_id+"비번"+logining_ceo_pw+"상점아이디22:"+logining_store_id);

        rv = findViewById(R.id.recycle_faq);


        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("");

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue2 == null){
            queue2 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue3 == null){
            queue3 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue4 == null){
            queue3 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }

        plus_btn = findViewById(R.id.faq_plus_btn);
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        itemManageMenus = new ArrayList<>();
        getData();


        //어댑터 세팅
        adpt = new Menu_faq_Adapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        adpt.setItemManageMenus(itemManageMenus);
        adpt.setOnItemClickListener(new Menu_faq_Listener() {


            @Override
            public void onItemClick(Menu_faq_Adapter.ViewHolder holder, View view, int position) {
                Menu_faq_item item = adpt.getItem(position);
                posi=position;
                send_id=item.getFaq_id();
                System.out.println("클릭후 아이디값:"+item.getFaq_id());
                Menu_faq_fix_dialog menu_dialog = new Menu_faq_fix_dialog();
                menu_dialog.setStrmenuname(item.getFaq_Q());
                menu_dialog.setStrmenuprice(item.getFaq_A());

                menu_dialog.show(getSupportFragmentManager(),"dialog");
                Log.v("여기뜨는건가","여기뜨는건가");



            }

        });





    }

    private void openDialog(){
        Menu_dialog_faq menu_dialog = new Menu_dialog_faq();
        menu_dialog.show(getSupportFragmentManager(),"dialog");
    }

    private void getItem() {
        itemManageMenus = new ArrayList<>();

    }





    public void getData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    System.out.println("제이슨어레이길이는?"+jsonArray.length());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("faq_q");
                            String item2 = jsonObject.getString("faq_a");
                            int item3 = jsonObject.getInt("faq_id");

                            itemManageMenus.add(new Menu_faq_item(item, item2,item3));
                            adpt.notifyDataSetChanged();
                            loadingDialogBar.HideDialog();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패", "안들어옴");
                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_faq requestRegister = new Request_get_faq(logining_store_id, responseListener);
        queue = Volley.newRequestQueue(Menu_faq_activity_ceo.this);
        queue.add(requestRegister);


    }



    @Override
    public void setText(String s, String strmenuname) {
        Menu_faq_item item = adpt.getItem(posi);
        System.out.println("첫번쨰 센드아이디"+send_id);
        itemManageMenus.set(posi,new Menu_faq_item(s,strmenuname,send_id));
        System.out.println("두번쨰 센드아이디"+send_id);
        send_q = s;
        send_a =strmenuname;
        System.out.println("이때 센드아이디:"+send_id);
        adpt.notifyDataSetChanged();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_update_faq requestRegister = new Request_update_faq(send_q,send_a,send_id, responseListener);
        queue3 = Volley.newRequestQueue(Menu_faq_activity_ceo.this);
        queue3.add(requestRegister);



    }

    @Override
    public void destroy() {
        itemManageMenus.remove(posi);
        adpt.notifyDataSetChanged();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_delete_faq requestRegister = new Request_delete_faq(send_id, responseListener);
        queue4 = Volley.newRequestQueue(Menu_faq_activity_ceo.this);
        queue4.add(requestRegister);



    }


    @Override
    public void applyText(String s, String strmenuname,int id) {
        itemManageMenus.add(new Menu_faq_item(s,strmenuname,id));
        adpt.notifyDataSetChanged();
        send_q = s;
        send_a =strmenuname;
        send_id = id;
        System.out.println(send_q+send_a+send_id);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_insert_faq requestRegister = new Request_insert_faq(send_q,send_a,logining_store_id, responseListener);
        queue2 = Volley.newRequestQueue(Menu_faq_activity_ceo.this);
        queue2.add(requestRegister);



        //여기서 메뉴 리사이클러뷰에 추가하기
    }


    }







