package com.example.foodchat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private LoadingDialogBar loadingDialogBar;
    private String logining_ceo_id,logining_ceo_pw;
    private int logining_store_id;
    private RequestQueue queue;
    private Button accept_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);
        accept_btn = findViewById(R.id.Accept_btn1);
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManageReservationAcceptActivity.class);
                intent.putExtra("logining_ceo_id", logining_ceo_id);
                intent.putExtra("logining_ceo_pw", logining_ceo_pw);
                intent.putExtra("logining_store_id", logining_store_id);
                startActivity(intent);
            }
        });

        // ProgressDialog ??????
        //????????? ?????? ??????
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("???????????? ???.");

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();;
        }


        rv = findViewById(R.id.store_reserva_recycle);
        backbtn = findViewById(R.id.store_reservation_backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reser_item = new ArrayList<>();
        getItem();



        adpt = new ManageReservationAdapter();
//???????????? ?????? ??????
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.store_reserva_recycle);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);
        adpt.setOnAcceptClickListener(new ManageReservationListener() {


            @Override
            public void onAcceptClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
                System.out.println("????????????????????????");
                show_1(position);

            }

            @Override
            public void onRejectClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
                System.out.println("????????????????????????");
                show_2(position);
            }
        });
    }


    void show_1(int position) // ????????????
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("????????????");
        builder.setMessage("?????? ????????? ?????????????????????????");
        builder.setPositiveButton("???",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        show_1_met(position);

                    }
                });
        builder.setNegativeButton("?????????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"?????????????????????..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    void show_2(int position) // ???????????? ?????? ?????? ????????? ?????????
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("????????????");
        builder.setMessage("?????? ????????? ?????? ???????????????????");
        builder.setPositiveButton("???",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        show_2_met(position);

                    }
                });
        builder.setNegativeButton("?????????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"?????????????????????..",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void show_1_met(int position) { // ?????? > ???????????? > ????????? , ?????? ?????????????????? ??????
        ItemManageReservation item = adpt.getItem(position);
        reser_item.remove(position);
        adpt.notifyDataSetChanged();
        System.out.println("???????????????:"+item.getReservation_id());
        int clicked_reservation_id = item.getReservation_id();
        String changeCheck = "?????? ?????? ??????";


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // ????????? ????????? ??????
                        Toast.makeText(getApplicationContext(),"?????? ?????? ?????????????????????.",Toast.LENGTH_SHORT).show();

                    } else { // ????????? ????????? ??????
                        Toast.makeText(getApplicationContext(),"?????? ?????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                        return;
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_update_ceo_reservation requestRegister = new Request_update_ceo_reservation(clicked_reservation_id,
                changeCheck,
                responseListener);
        queue = Volley.newRequestQueue(ManageReservationActivity.this);
        queue.add(requestRegister);

    }


    public void show_2_met(int position) { // ???????????? ????????? ?????? > ??? > ????????????????????????
        ItemManageReservation item = adpt.getItem(position);
        reser_item.remove(position);
        adpt.notifyDataSetChanged();
        System.out.println("???????????????:"+item.getReservation_id());
        int clicked_reservation_id = item.getReservation_id();
        String changeCheck = "?????? ?????? ??????";


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // ????????? ????????? ??????
                        Toast.makeText(getApplicationContext(),"?????? ?????? ?????????????????????.",Toast.LENGTH_SHORT).show();

                    } else { // ????????? ????????? ??????
                        Toast.makeText(getApplicationContext(),"?????? ?????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                        return;
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_update_ceo_reservation_reject requestRegister = new Request_update_ceo_reservation_reject(clicked_reservation_id,
                changeCheck,
                responseListener);
        queue = Volley.newRequestQueue(ManageReservationActivity.this);
        queue.add(requestRegister);

    }








    private void getItem() { // ?????? ????????? UI???????????????

        reser_item = new ArrayList<>();

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
                            String item = jsonObject.getString("reservation_storeaddress");
                            String item2 = jsonObject.getString("reservation_date");
                            String item3 = jsonObject.getString("reservation_time");
                            String item4 = jsonObject.getString("reservation_people");
                            String item5 = jsonObject.getString("reservation_check");
                            String item6 = jsonObject.getString("user_id");
                            String item7 = jsonObject.getString("user_nickname");
                            String item8 = jsonObject.getString("reservation_storename");
                            int item9 = jsonObject.getInt("reservation_id");
                            if(item5.equals("?????????")) {
                                reser_item.add(new ItemManageReservation(item, item2, item3, item4,
                                        item5, item6, item7, item8, item9));
                                adpt.notifyDataSetChanged();

                                adpt.setItemManageReservations(reser_item);
                                loadingDialogBar.HideDialog();

                            }
                            loadingDialogBar.HideDialog();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("????????????","????????????");
                            loadingDialogBar.HideDialog();;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();;
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_get_CEO_reservation requestRegister = new Request_get_CEO_reservation(logining_store_id, responseListener);
        queue = Volley.newRequestQueue(ManageReservationActivity.this);
        queue.add(requestRegister);


    }














}