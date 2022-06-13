package com.example.foodchat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

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

    private final Calendar cldr = Calendar.getInstance();
    private int hour = cldr.get(Calendar.HOUR_OF_DAY);
    private int minutes = cldr.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_reservation);

        //이 텍스트뷰는 그냥 클릭하면 식당 위치랑 이름 가져오는 역할로 쓰기
        storename = (AutoCompleteTextView) findViewById(R.id.reservation_store_name);
        storelocation = (AutoCompleteTextView) findViewById(R.id.reservation_store_location);

        btn_reservation_date = (Button) findViewById(R.id.reservation_date_pick);
        btn_reservation_time = (Button) findViewById(R.id.reservation_time);
        btn_reservation_num = (Button) findViewById(R.id.reservation_number);
        btn_reservation = (Button) findViewById(R.id.reservation_btn);

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

            }
        };
    }
    public DatePickerDialog datePickerDialog(int datePickerId) {

        switch (datePickerId) {
            case RESERVATION_DATE:

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
                        //get number of traveller here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

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
                numChild.setText(String.valueOf(AdultCount));
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





