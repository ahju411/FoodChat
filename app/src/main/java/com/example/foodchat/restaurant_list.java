package com.example.foodchat;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class restaurant_list extends AppCompatActivity implements AbsListView.OnScrollListener  {

    private ListView listView;                      // 리스트뷰
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private List<String> list;                      // String 데이터를 담고있는 리스트
    private ListViewAdapter adapter;                // 리스트뷰의 아답터
    private int page = 0;                           // 페이징변수. 초기 값은 0 이다.
    private final int OFFSET = 20;                  // 한 페이지마다 로드할 데이터 갯수.
    private ProgressBar progressBar;                // 데이터 로딩중을 표시할 프로그레스바
    private boolean mLockListView = false;// 데이터 불러올때 중복안되게 하기위한 변수a
    ImageView menubtn;
    Button LV;
    ImageView IV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);



        listView = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        list = new ArrayList<String>();
        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);


        listView.setOnScrollListener(this);
        getItem();

        menubtn = (ImageView) findViewById(R.id.menubtn);
        menubtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //나의 메뉴로 이동
                Intent intent = new Intent(view.getContext(), UserMenuHomeActivity.class);
                startActivity(intent);
            }
        });

        // 임시 식당 상세페이지 들어가기
        LV = (Button) findViewById(R.id.address);
        LV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //임시 식당 상세페이지 들어가기
                Intent intent = new Intent(view.getContext(), Restaurant_Info.class);
                startActivity(intent);
            }
        });

        // 임시 웹서버 DB연결테스트 삭제 예정
        IV = (ImageView) findViewById(R.id.searchbtn);
        IV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //임시 웹 서버DB연결테스트 삭제 예정
                Intent intent = new Intent(view.getContext(), ManageInputStoreActivity.class);
                startActivity(intent);
            }
        });

        // 임시 웹서버 DB연결테스트2 삭제 예정
        IV = (ImageView) findViewById(R.id.rangebtn);
        IV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //임시 웹 서버DB연결테스트2 삭제 예정
                Intent intent = new Intent(view.getContext(), dbtest_loda_image.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            // 화면이 바닦에 닿을때 처리
            // 로딩중을 알리는 프로그레스바를 보인다.
            progressBar.setVisibility(View.VISIBLE);

            // 다음 데이터를 불러온다.
            getItem();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
        // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
        // totalItemCount : 리스트 전체의 총 갯수
        // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    private void getItem(){

        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        mLockListView = true;

        // 다음 20개의 데이터를 불러와서 리스트에 저장한다.
        for(int i = 0; i < 20; i++){
            String label = "Label " + ((page * OFFSET) + i);
            System.out.println(page);
            list.add(label);
        }

        // 1초 뒤 프로그레스바를 감추고 데이터를 갱신하고, 중복 로딩 체크하는 Lock을 했던 mLockListView변수를 풀어준다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mLockListView = false;
            }
        },1000);
    }
}