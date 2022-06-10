package com.example.foodchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.bumptech.glide.request.transition.BitmapTransitionFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserMenuReviewActivity extends AppCompatActivity {
    ImageView back_btn;
    static RequestQueue requestQueue;
    private int clicked_store_id;
    private String logining_user_id,logining_user_nickname;
    private RecyclerView rv;
    private UserMenuReview_Adapter adpt;
    private ArrayList<UserMenuReview_Item> Review_item;
    LoadingDialogBar loadingDialogBar;
    Bitmap bigPictureBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu_review);

        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("상점아디값:"+clicked_store_id);
        System.out.println("유저아이디:"+logining_user_id);
        System.out.println("유저닉네임:"+logining_user_nickname);


        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        getItem();




        adpt = new UserMenuReview_Adapter();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.myReview_recycler_menu);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);
        adpt.setOnItemClickListener(new UserMenuReview_Listener() {
            @Override
            public void onListClick(UserMenuReview_Adapter.ViewHolder holder, View view, int position) {
                UserMenuReview_Item item2 = adpt.getItem(position);
                System.out.println("1번체크"+item2.getUser_nickname());
                System.out.println("2번체크"+item2.getDate());


            }
        });





    }


    private void getItem() { // 식당 리스트 UI수정하는거

        Review_item = new ArrayList<>();

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




                            Review_item.add(new UserMenuReview_Item(item,item7,item6,item5,
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
        Request_User_Review requestRegister = new Request_User_Review(logining_user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserMenuReviewActivity.this);
        queue.add(requestRegister);


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





