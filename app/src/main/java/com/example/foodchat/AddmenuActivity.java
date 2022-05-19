package com.example.foodchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddmenuActivity extends AppCompatActivity implements Menu_dialog.Menu_dialogListener {
    private Button finishbtn;
    private ImageButton backbtn,addmenubtn;
    private AddmenuAdapter adpt;
    private ArrayList<ItemManageMenu> itemManageMenus;
    private RecyclerView rv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_addmenu);
        rv = findViewById(R.id.recycler_menu);
        addmenubtn = findViewById(R.id.addmenubtn);
        addmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog 추가
                openDialog();
            }
        });

        backbtn = findViewById(R.id.store_menu_backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getItem();

        //어댑터 세팅
        adpt = new AddmenuAdapter();
        //레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        adpt.setItemManageMenus(itemManageMenus);
        adpt.setOnItemClickListener(new AddmenuListener() {
            @Override
            public void onItemClick(AddmenuAdapter.ViewHolder holder, View view, int position) {
                ItemManageMenu item = adpt.getItem(position);

                Menu_dialog menu_dialog = new Menu_dialog();
                menu_dialog.setStrmenuname(item.getMenu_name());
                menu_dialog.setStrmenuprice(item.getMenu_price());
                menu_dialog.setStrmenuexplain(item.getMenu_explain());
                menu_dialog.setBmmenuimg(item.getMenu_img());
                menu_dialog.setPosition(position);


                menu_dialog.show(getSupportFragmentManager(),"dialog");
                //openDialogFull(item.getMenu_name(),item.getMenu_price(),item.getMenu_explain(),item.getMenu_img());

                Toast.makeText(getApplicationContext(),"메뉴명 받기" + item.getMenu_name(),Toast.LENGTH_SHORT).show();
            }
        });




        finishbtn = findViewById(R.id.menu_finish);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adpt.notifyDataSetChanged();
                finish();
            }
        });


    }

    private void openDialog(){
        Menu_dialog menu_dialog = new Menu_dialog();
        menu_dialog.show(getSupportFragmentManager(),"dialog");
    }

    private void getItem() {
        itemManageMenus = new ArrayList<>();

        //itemManageMenus.add(new ItemManageMenu("짜장면","누구나 즐길 수 있는 짜장면","5000원",));
        //itemManageMenus.add(new ItemManageMenu("짬뽕","누구나 즐길 수 있는 짬뽕","6000원",R.drawable.jjambbong));
        //itemManageMenus.add(new ItemManageMenu("탕수육","누구나 즐길 수 있는 탕수육","9000원",R.drawable.tangsu));
        //itemManageMenus.add(new ItemManageMenu("볶음밥","누구나 즐길 수 있는 볶음밥","5000원",R.drawable.friedrice));
    }



    @Override
    public void applyText(String strmenuname, String strmenuprice, String strmenuexplain, String strmenuimg) {
        itemManageMenus.add(new ItemManageMenu(strmenuname,strmenuexplain,strmenuprice + "원",StringToBitmaps(strmenuimg)));
        adpt.notifyDataSetChanged();


        //여기서 메뉴 리사이클러뷰에 추가하기
    }

    @Override
    public void setText(String strmenuname, String strmenuprice, String strmenuexplain, String strmenuimg,int position) {
        itemManageMenus.set(position,new ItemManageMenu(strmenuname,strmenuexplain,strmenuprice + "원",StringToBitmaps(strmenuimg)));
        adpt.notifyDataSetChanged();
    }

    public static Bitmap StringToBitmaps(String image) {
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

