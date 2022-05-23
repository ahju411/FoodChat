package com.example.foodchat;

import android.content.Context;
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

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageReservationActivity extends AppCompatActivity {
    private MaterialCalendarView calendar;
    private RecyclerView rv;
    private ManageReservationAdapter adpt;
    private ArrayList<ItemManageReservation> itemManageReservations;
    private ImageButton backbtn;
    private ArrayList<CalendarDay> callist = new ArrayList<>();
    private CalendarDay stCalDay,endCalDay;
    private Calendar endCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);


        // ====================캘린더 파트 =====================
        calendar = findViewById(R.id.reservation_calendarView);
        stCalDay = CalendarDay.from(getCurrentYear(),getCurrentMonth(),getCurrentDay());
        endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH,getCurrentMonth()+6); //오늘로부터 6달까지만 달력에 표시
        endCalDay = CalendarDay.from(endCalendar.get(Calendar.YEAR),endCalendar.get(Calendar.MONTH),endCalendar.get(Calendar.DATE));




        //달력 시작이 일요일로 하는 것
        calendar.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(
                        CalendarDay.from(  //오늘 날 전은 클릭 안 되게 하는 함수들
                                getCurrentYear(),
                                getCurrentMonth(),
                                1
                        )).setMaximumDate(
                                CalendarDay.from(getCurrentYear(),
                                        getCurrentMonth()+3,
                                        endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        //달과 주 요일 이름 한글로 설정하기
        calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        //좌우 화살표 사이 연, 월 폰트 스타일 설정하기
        calendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        //하나만 눌렀을 때 사용
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String clickDay = date.getDate().toString();
                Log.d("날짜 확인","날짜: " + clickDay);
            }
        });
        //오래 눌렀을 때 다이얼로그 띄울 예정
        calendar.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
                Log.d("눌렀다","꾹 눌림");
            }
        });

        //범위 날짜 선택시 사용
        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(MaterialCalendarView widget, List<CalendarDay> dates) {
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size()-1).getDate().toString();
                Log.d("날짜 확인","시작: " + startDay + "종료: " + endDay);
            }
        });


        //캘린더의 꾸밈 함수들 쓰기
        calendar.addDecorators(new DayDecorator(this),
                new SaturdayDecorator(),
                new SundayDecorator(),
                new MinMaxDecorator(stCalDay,endCalDay));


        callist.add(CalendarDay.from(2022,5,25));
        callist.add(CalendarDay.from(2022,5,27));


        for (CalendarDay calDay : callist){
            calendar.addDecorators(new FullDayDecorator(this,calDay));
        }


        //좌우 화살표 가운데 연/월이 보이는 방식
        calendar.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                LocalDate inputText = day.getDate();
                String[] calendarHeader = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeader[0]).append(" ").append(calendarHeader[1]);
                return calendarHeaderBuilder.toString();
            }
        });




        rv = findViewById(R.id.recycler_storeReservation);
        backbtn = findViewById(R.id.store_reservation_backbtn);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        adpt = new ManageReservationAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        itemManageReservations = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            itemManageReservations.add(new ItemManageReservation("은철","010-1234-1234","2022-05-09-13:00","성인1명"));
        }
        adpt.setItemManageReservations(itemManageReservations);
    }

    // ====================캘린더 파트 =====================

    public int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    public int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    private static class DayDecorator implements DayViewDecorator {
        private final Drawable drawable;

        private DayDecorator(Context context) {
            drawable = context.getDrawable(R.drawable.calender_selector);
        }


        //true 리턴 시 모든 요일에 내가 설정한 drawable 적용됨
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        //일자 선택 시 내가 정의한 drawable 적용
        @Override
        public void decorate(DayViewFacade view) {

            view.setSelectionDrawable(drawable);
        }
    }




    //토요일 파란색
    private static class SaturdayDecorator implements DayViewDecorator{
        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator(){
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int weekDay = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
            return weekDay == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    //일요일 빨간색
    private static class SundayDecorator implements DayViewDecorator{
        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator(){
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int weekDay = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
            return weekDay == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }



    //예약 못 받을 떄 쓸 클래스 사장에선 안 쓸 예정 손님용
    private static class FullDayDecorator implements DayViewDecorator{

        private final Drawable drawable;
        private final CalendarDay myday;


        private FullDayDecorator(Context context,CalendarDay calendarDay) {
            drawable = context.getDrawable(R.drawable.cancelimg); // 취소 이미지 넣을 예정
            myday = calendarDay;

        }
       /* private FullDayDecorator(Context context,CalendarDay currenDay) {
            drawable = context.getDrawable(R.drawable.cancelimg); // 취소 이미지 넣을 예정
            myday = currenDay;

        }*/


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(myday);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(drawable);
            view.setDaysDisabled(true);
        }


    }
    //내 이전 이후 날짜는 회색으로 표시하기

    private static class MinMaxDecorator implements DayViewDecorator{
        private final CalendarDay minday,maxday;

        private MinMaxDecorator(CalendarDay min, CalendarDay max) {
            minday = min;
            maxday = max;
        }


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return (day.getMonth() == maxday.getMonth() && day.getDay() > maxday.getDay()) || (day.getMonth() == minday.getMonth() && day.getDay() < minday.getDay());
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.parseColor("#d2d2d2")));
            view.setDaysDisabled(true);
        }
    }

}