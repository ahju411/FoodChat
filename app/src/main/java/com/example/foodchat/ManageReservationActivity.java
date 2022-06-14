package com.example.foodchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageReservationActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ManageReservationAdapter adpt;
    private ArrayList<ItemManageReservation> reser_item;
    private ImageButton backbtn;
    private ArrayList<CalendarDay> callist = new ArrayList<>();

    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);



        rv = findViewById(R.id.store_reserva_recycle);
        backbtn = findViewById(R.id.store_reservation_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        adpt = new ManageReservationAdapter();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.store_reserva_recycle);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);


    }





//
//
//    private void getItem() { // 식당 리스트 UI수정하는거
//
//        reser_item = new ArrayList<>();
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    for (int i=0; i < jsonArray.length(); i++)
//                    {
//                        try {
//                            jsonObject = jsonArray.getJSONObject(i);
//                            // Pulling items from the array
//                            String item = jsonObject.getString("user_nickname");
//                            String item2 = jsonObject.getString("review_image1");
//                            Bitmap bit1 = StringToBitmaps(item2);
//
//                            String item3 = jsonObject.getString("review_image2");
//                            Bitmap bit2 = StringToBitmaps(item3);
//
//                            String item4 = jsonObject.getString("review_image3");
//                            Bitmap bit3 = StringToBitmaps(item4);
//
//                            int item5 = jsonObject.getInt("review_like");
//                            if(item5==1){ // 가져온게 1일경우 1은 따봉
//                                item5 = R.drawable.goodimg;
//                            }
//                            if(item5==-1){ // 가져온게 -1일경우 -1은 재방문없다는거임
//                                item5 = R.drawable.badimg;
//                            }
//                            String item6 = jsonObject.getString("review_mension");
//                            String item7 = jsonObject.getString("review_datetime");
//                            String item8 = jsonObject.getString("review_ceo_mension");
//                            if(item8.equals("null")){
//                                item8 = "사장님의 답변이 아직 없어요.";
//                            }
//
//
//
//
//                            reser_item.add(new UserMenuReview_Item(item,item7,item6,item5,
//                                    bit1,bit2,bit3,item8));
//                            adpt.notifyDataSetChanged();
//
//                            adpt.setReview_item(Review_item);
//                            loadingDialogBar.HideDialog();;
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.v("작동실패","안들어옴");
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }; // 서버로 Volley를 이용해서 요청을 함.
//        Request_User_Review requestRegister = new Request_User_Review(logining_user_id, responseListener);
//        RequestQueue queue = Volley.newRequestQueue(ManageReservationActivity.this);
//        queue.add(requestRegister);
//
//
//    }














}