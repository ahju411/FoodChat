package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

import java.util.ArrayList;

public class UserFaqAcitivity extends AppCompatActivity {
    private Button store_registerbtn,manage_reviewbtn,manage_reservationbtn,manage_faqbtn;
    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;
    private int clicked_store_id;
    private String store_name;
    private Menu_faq_Adapter adpt;
    private ArrayList<Menu_faq_item> itemManageMenus;
    private RequestQueue queue;
    private RecyclerView rv;
    LoadingDialogBar loadingDialogBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_user);
        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        System.out.println("상점아디값:"+clicked_store_id);
        rv = findViewById(R.id.recycle_faq_user);


        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("");
        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }

        itemManageMenus = new ArrayList<>();
        getData();

        adpt = new Menu_faq_Adapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        adpt.setItemManageMenus(itemManageMenus);

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
        Request_get_faq requestRegister = new Request_get_faq(clicked_store_id, responseListener);
        queue = Volley.newRequestQueue(UserFaqAcitivity.this);
        queue.add(requestRegister);


    }






}
