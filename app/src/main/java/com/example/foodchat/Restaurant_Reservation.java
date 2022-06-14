package com.example.foodchat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.List;

public class Restaurant_Reservation extends AppCompatActivity {

    private Button btn_reservation_date,btn_reservation_time,btn_reservation_num,btn_reservation;
    private AutoCompleteTextView storename,storelocation;
    private String reservationdate;
    private ImageButton imgBtnAdd_adult,imgBtnRemove_adult,imgBtnAdd_child,imgBtnRemove_child;
    private TextView numAdult,numChild;
    private final int RESERVATION_DATE = R.id.reservation_date_pick;
    private final int RESERVATION_TIME = R.id.reservation_time;
    private DatePickerDialog datePickerDialog1;
    private TimePickerDialog timePickerDialog1;
    private final static int TIME_PICKER_INTERVAL = 5;
    private int year;
    private int month;
    private int day;
    //custom dialog view
    private View dialogLayout;
    //예약인원 count
    private int AdultCount = 0;
    private int ChildCount = 0;
    private int click_year,click_month,click_day;
    private String click_date,click_time;
    private RequestQueue queue;
    private String people,check;

    private final Calendar cldr = Calendar.getInstance();
    private int hour = cldr.get(Calendar.HOUR_OF_DAY);
    private int minutes = cldr.get(Calendar.MINUTE);
    private String logining_user_id,logining_user_nickname,clicked_store_name,clicked_store_address;
    private int clicked_store_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_reservation);
        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        clicked_store_name = getintent.getStringExtra("clicked_store_name");
        clicked_store_address = getintent.getStringExtra("clicked_store_address");
        System.out.println("상점아디값:"+clicked_store_id);
        System.out.println("유저아이디:"+logining_user_id);
        System.out.println("유저닉네임:"+logining_user_nickname);
        System.out.println("상점이름:"+clicked_store_name);
        System.out.println("상점주소:"+clicked_store_address);

        //이 텍스트뷰는 그냥 클릭하면 식당 위치랑 이름 가져오는 역할로 쓰기
        storename =  findViewById(R.id.reservation_store_name);
        storelocation = findViewById(R.id.reservation_store_location);

        storename.setText(clicked_store_name);
        storelocation.setText(clicked_store_address);
        storename.setClickable(false);
        storename.setFocusable(false);
        storelocation.setClickable(false);
        storelocation.setFocusable(false);

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
        }


        btn_reservation_date = (Button) findViewById(R.id.reservation_date_pick);
        btn_reservation_time = (Button) findViewById(R.id.reservation_time);
        btn_reservation_num = (Button) findViewById(R.id.reservation_number);
        btn_reservation = (Button) findViewById(R.id.reservation_btn);
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check="대기중";
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
                Request_input_reservation requestRegister = new Request_input_reservation(logining_user_id,
                        logining_user_nickname,clicked_store_id,click_date,click_time,people,check,
                        storename.getText().toString(),storelocation.getText().toString(),
                        responseListener);
                queue = Volley.newRequestQueue(Restaurant_Reservation.this);
                queue.add(requestRegister);
            }
        });


        year = HelperUtilities.currentYear();
        month = HelperUtilities.currentMonth();
        day = HelperUtilities.currentDay();

        btn_reservation_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog(RESERVATION_DATE).show();
                Log.d("test click","데이트 클릭");

            }
        });

        //여기엔 시간 버튼 설정
        btn_reservation_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timePickerDialog(RESERVATION_TIME).show();
                Log.d("test click","시간 클릭");

            }
        });



        //이건 인원 설정
        btn_reservation_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test click","인원 클릭");
                reservationnumberDialog().show();

            }
        });


    }
    public TimePickerDialog.OnTimeSetListener getReservationTimeListener(){
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
                btn_reservation_time.setText(Hour + "시 " + Minute + "분");
                click_time = Hour+"시"+Minute+"분";
                System.out.println("클릭한시간"+click_time);
            }
        };
    }


    public TimePickerDialog timePickerDialog(int timePickerid){
        switch (timePickerid){
            case RESERVATION_TIME:
                if (timePickerDialog1 == null){
                    timePickerDialog1 = new TimePickerDialog(this, getReservationTimeListener(),hour,minutes,true);
                    timePickerDialog1.setTitle("대여반납시간");
                }
                return timePickerDialog1;
        }

        return null;

    }



    public DatePickerDialog.OnDateSetListener getReservationDateListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int startYear, int startMonth, int startDay) {

                //get one way departure date here

                reservationdate = startYear + "-" + (startMonth + 1) + "-" + startDay;
                btn_reservation_date.setText(HelperUtilities.formatDate(startYear, startMonth, startDay));
                click_year = startYear;
                click_month = (startMonth+1);
                click_day = startDay;
                click_date = ""+click_year+"-"+click_month+"-"+click_day;
                System.out.println("select date22: "+click_year+"-"+click_month+"-"+click_day);
                System.out.println("select date22: "+click_date);

            }
        };
    }
    public DatePickerDialog datePickerDialog(int datePickerId) {

        switch (datePickerId) {
            case RESERVATION_DATE:
                System.out.println("끼야호호호호호ㅅㄷ");
                if (datePickerDialog1 == null) {
                    datePickerDialog1 = new DatePickerDialog(this, getReservationDateListener(), year, month, day);
                }
                datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return datePickerDialog1;

        }
        return null;
    }

    //number of reservation
    public Dialog reservationnumberDialog() {


        dialogLayout = getLayoutInflater().inflate(R.layout.reservation_number_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("예약인원")
                .setView(dialogLayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("예약인원 OK클릭댐 성인:"+AdultCount+"명"+" "+"아동:"+ChildCount+"명");
                        people = "성인:"+AdultCount+"명"+" "+"아동:"+ChildCount+"명";
                        System.out.println("피플값"+people);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("예약인원 취소클릭댐");
                    }
                });

        imgBtnRemove_adult = (ImageButton) dialogLayout.findViewById(R.id.imgBtnRemove_adult);
        imgBtnAdd_adult = (ImageButton) dialogLayout.findViewById(R.id.imgBtnAdd_adult);
        numAdult = (TextView) dialogLayout.findViewById(R.id.txtNumber_adult);

        imgBtnRemove_child = (ImageButton) dialogLayout.findViewById(R.id.imgBtnRemove_child);
        imgBtnAdd_child = (ImageButton) dialogLayout.findViewById(R.id.imgBtnAdd_child);
        numChild = (TextView) dialogLayout.findViewById(R.id.txtNumber_child);

        imgBtnAdd_adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdultCount++;
                numAdult.setText(String.valueOf(AdultCount));
                btn_reservation_num.setText( String.valueOf(AdultCount) + "성인, " + String.valueOf(ChildCount) + "아동");
            }
        });

        imgBtnRemove_adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AdultCount > 0) {
                    AdultCount--;
                }
                numAdult.setText(String.valueOf(AdultCount));
                btn_reservation_num.setText(String.valueOf(AdultCount) + "성인, " + String.valueOf(ChildCount) + "아동");
            }
        });


        imgBtnAdd_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChildCount++;
                numChild.setText(String.valueOf(ChildCount));
                btn_reservation_num.setText(String.valueOf(AdultCount) + "성인, " + String.valueOf(ChildCount) + "아동");
            }
        });

        imgBtnRemove_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ChildCount > 0) {
                    ChildCount--;
                }
                numChild.setText(String.valueOf(ChildCount));
                btn_reservation_num.setText(String.valueOf(AdultCount) + "성인, " + String.valueOf(ChildCount) + "아동");
            }
        });



        numChild.setText(String.valueOf(ChildCount));
        numAdult.setText(String.valueOf(AdultCount));

        return builder.create();
    }
}





