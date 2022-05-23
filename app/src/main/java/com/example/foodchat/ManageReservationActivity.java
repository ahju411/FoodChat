package com.example.foodchat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class ManageReservationActivity extends AppCompatActivity {
    private MaterialCalendarView calendar;
    private RecyclerView rv;
    private ManageReservationAdapter adpt;
    private ArrayList<ItemManageReservation> itemManageReservations;
    private ImageButton backbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_reservation);


        calendar.findViewById(R.id.reservation_calendarView);
        //달력 시작이 일요일로 하는 것
        calendar.state().edit().setFirstDayOfWeek(DayOfWeek.SUNDAY).commit();
        //달과 주 요일 이름 한글로 설정하기
        calendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        //좌우 화살표 사이 연, 월 폰트 스타일 설정하기
        calendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        //요일 선택시 내가 정의한 거 되는 지
        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(MaterialCalendarView widget, List<CalendarDay> dates) {
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size()-1).getDate().toString();
                Log.d("날짜 확인","시작: " + startDay + "종료: " + endDay);
            }
        });
        calendar.addDecorators(new DayDecorator(this));

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



        /*calendar.set(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int days) {
                selectYear = year;
                selectMonth = month +1;
                selectDays = days;
            }
        });

*/


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
    private static class DayDecorator implements DayViewDecorator {
        private final Drawable drawable;

        private DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calender_selector);
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
}