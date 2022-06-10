package com.example.foodchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class ManagereviewActivity extends AppCompatActivity {

    private RecyclerView rv;
    private String New_Ceo_mension,u_date,u_review,u_nickname;
    private EditText ed;
    private GetCEO_MenuReview_Adapter adpt;
    private ArrayList<GetCEO_MenuReview_Item> Review_item;
    private ImageButton backbtn;
    private RequestQueue queue;
    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;
    private int reviewid;
    private RequestQueue queue3;
    LoadingDialogBar loadingDialogBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_review);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);

        System.out.println("아이디:"+logining_ceo_id+"비번"+logining_ceo_pw+"상점아이디22:"+logining_store_id);

        if(queue3 == null){
            queue3 = Volley.newRequestQueue(getApplicationContext());
        }

        rv = findViewById(R.id.recycler_storeReview);
        backbtn = findViewById(R.id.store_review_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");


        getReviewData();
        getItem();

        //어댑터 세팅
        adpt = new GetCEO_MenuReview_Adapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);



        adpt.setReview_item(Review_item);
        adpt.setListClickListener(new GetCEO_MenuReview_Listener(){
            @Override
            public void onListClick(GetCEO_MenuReview_Adapter.ViewHolder holder, View view, int position) {

                GetCEO_MenuReview_Item item2 = adpt.getItem(position);
                u_date = item2.getDate();
                u_review = item2.getReview();
                u_nickname = item2.getUser_nickname();
                System.out.println("아이디"+u_nickname);
                System.out.println("날짜"+u_date);
                System.out.println("멘션:"+u_review);
                New_Ceo_mension = adpt.getTest();   //CEO 답변작성한거 불러오기
                System.out.println(New_Ceo_mension);
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
                                    int item = jsonObject.getInt("review_id");
                                    reviewid=item;
                                    System.out.println("리뷰아이디 머임:"+item);




                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }; // 서버로 Volley를 이용해서 요청을 함.
                Request_get_ReviewID_CEO requestRegister = new Request_get_ReviewID_CEO(u_nickname,
                        logining_store_id,u_review,
                        responseListener);
                queue = Volley.newRequestQueue(ManagereviewActivity.this);
                queue.add(requestRegister);


                show(position);
            }

        });




    }

    void show(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("등록");
        builder.setMessage("해당 리뷰에 답변을 등록 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

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
                                            int item = jsonObject.getInt("review_id");
                                            reviewid=item;
                                            System.out.println("리뷰아이디 머임:"+item);




                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }

                            }
                        }; // 서버로 Volley를 이용해서 요청을 함.
                        Request_get_ReviewID_CEO requestRegister = new Request_get_ReviewID_CEO(u_nickname,
                                logining_store_id,u_review,
                                responseListener);
                        queue = Volley.newRequestQueue(ManagereviewActivity.this);
                        queue.add(requestRegister);


                        Insert_CEO_MENSION(position);











                        Toast.makeText(getApplicationContext(),"등록이 되었습니다..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"등록이 취소되었습니다..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void Insert_CEO_MENSION(int position) {
        Review_item.remove(position);
        adpt.notifyDataSetChanged();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"등록 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"등록 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_insert_ceo_mension requestRegister = new Request_insert_ceo_mension(reviewid,New_Ceo_mension,
                responseListener);
        queue3 = Volley.newRequestQueue(ManagereviewActivity.this);
        queue3.add(requestRegister);

    }



    public void getReviewData() {

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
                            String item = jsonObject.getString("user_nickname");

                            String item2 = jsonObject.getString("review_image1");
                            Bitmap bit1 = StringToBitmaps(item2);

                            String item3 = jsonObject.getString("review_image2");
                            Bitmap bit2 = StringToBitmaps(item3);

                            String item4 = jsonObject.getString("review_image3");
                            Bitmap bit3 = StringToBitmaps(item4);

                            int item5 = jsonObject.getInt("review_like");
                            if(item5==1){ // 가져온게 1일경우 1은 따봉
                                item5 = R.drawable.goodimg;
                            }
                            if(item5==-1){ // 가져온게 -1일경우 -1은 재방문없다는거임
                                item5 = R.drawable.badimg;
                            }
                            String item6 = jsonObject.getString("review_mension");
                            String item7 = jsonObject.getString("review_datetime");
                            String item8 = jsonObject.getString("review_ceo_mension");
                            if(item8.equals("null")){
                                item8 = "사장님의 답변이 아직 없어요.";
                            }

                            System.out.println("리뷰데이터가져오나");
                            System.out.println("유저닉네임값:"+item);




                            Review_item.add(new GetCEO_MenuReview_Item(item,item7,item6,item5,
                                    bit1,bit2,bit3,item8));
                            adpt.notifyDataSetChanged();

                            adpt.setReview_item(Review_item);
                            loadingDialogBar.HideDialog();;




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_Review_CEO requestRegister = new Request_get_Review_CEO(logining_store_id, responseListener);
        queue = Volley.newRequestQueue(ManagereviewActivity.this);
        queue.add(requestRegister);


    }

    private void getItem() {
        Review_item = new ArrayList<>();

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









}