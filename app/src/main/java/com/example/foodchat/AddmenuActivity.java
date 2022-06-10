package com.example.foodchat;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddmenuActivity extends AppCompatActivity implements Menu_dialog.Menu_dialogListener,Menu_fix_dialog.Menu_dialogListener {
    private Button finishbtn;
    private ImageButton backbtn,addmenubtn;
    private AddmenuAdapter adpt;
    private ArrayList<ItemManageMenu> itemManageMenus;
    private RecyclerView rv;
    private String logining_ceo_id,logining_ceo_pw;
    private String menu_name, menu_image, menu_price,menu_info; // 서버에 보낼것들
    private String[] menu = new String[10];
    private int logining_store_id,menu_id=999999;
    public static String menu_image2="aa";
    private RequestQueue queue;
    LoadingDialogBar loadingDialogBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_addmenu);
        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        logining_store_id = getintent.getIntExtra("logining_store_id",0);



        System.out.println("아이디:"+logining_ceo_id+"비번"+logining_ceo_pw+"상점아이디22:"+logining_store_id);

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
        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("");

        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        getData();

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

                Menu_fix_dialog menu_dialog = new Menu_fix_dialog();
                menu_dialog.setStrmenuname(item.getMenu_name());
                menu_dialog.setStrmenuprice(item.getMenu_price());
                menu_dialog.setStrmenuexplain(item.getMenu_explain());
                menu_dialog.setBmmenuimg(item.getMenu_img());
                menu_dialog.setPosition(position);

                menu_dialog.show(getSupportFragmentManager(),"dialog");
                //openDialogFull(item.getMenu_name(),item.getMenu_price(),item.getMenu_explain(),item.getMenu_img());
                Log.v("여기뜨는건가","여기뜨는건가");
                Toast.makeText(getApplicationContext(),"메뉴명 받기" + item.getMenu_name(),Toast.LENGTH_SHORT).show();

                // 메뉴아이디 서버에서 가져오기

                menu_name=item.getMenu_name();
                menu_price=item.getMenu_price();
                menu_info=item.getMenu_explain();
                menu_image=encodeBitmapImage(item.getMenu_img());


                System.out.println("이름 잘나오나?"+menu_name);
                System.out.println("가격 잘나오나?"+menu_price);
                System.out.println("정보 잘나오나?"+menu_info);
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
                                    int item = jsonObject.getInt("menu_id");
                                    String item2 = jsonObject.getString("menu_image");
                                    menu_id= item;
                                    System.out.println("메뉴아이디받음?"+menu_id);

                                    menu_image2 = item2;




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
                Request_Menu_ID requestRegister = new Request_Menu_ID(String.valueOf(logining_store_id),
                        menu_name,menu_price,menu_info, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddmenuActivity.this);
                queue.add(requestRegister);






            }
        });




        finishbtn = findViewById(R.id.menu_finish);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(),"메뉴등록이 되었습니다." ,Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void openDialog(){
        Menu_dialog menu_dialog = new Menu_dialog();
        menu_dialog.show(getSupportFragmentManager(),"dialog");
    }

    private void getItem() {
        itemManageMenus = new ArrayList<>();

    }



    @Override
    public void applyText(String strmenuname, String strmenuprice, String strmenuexplain, String strmenuimg) { // 추가부분
        itemManageMenus.add(new ItemManageMenu(strmenuname,strmenuexplain,strmenuprice ,StringToBitmaps(strmenuimg)));
        adpt.notifyDataSetChanged();
        menu_name = strmenuname;
        menu_image = strmenuimg;
        menu_price = strmenuprice;
        menu_info = strmenuexplain;


        System.out.println("(추가부분) 이름:"+menu_name+"이미지생략하고 가격:"+menu_price+"메뉴정보:"+menu_info);


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
        Request_insert_menu requestRegister = new Request_insert_menu(String.valueOf(logining_store_id),String.valueOf(menu_id),
                menu_name,menu_image,menu_price,menu_info, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddmenuActivity.this);
        queue.add(requestRegister);



        //여기서 메뉴 리사이클러뷰에 추가하기
    }



    //메뉴 수정

    @Override
    public void setText(String strmenuname, String strmenuprice, String strmenuexplain, String strmenuimg,int position) { // 수정부분
        itemManageMenus.set(position,new ItemManageMenu(strmenuname,strmenuexplain,strmenuprice,StringToBitmaps(strmenuimg)));
        adpt.notifyDataSetChanged();
        menu_name = strmenuname;
        menu_price = strmenuprice;
        menu_info = strmenuexplain;
        menu_image = strmenuimg;



        System.out.println("(수정부분) 이름:"+menu_name+"이미지생략하고 가격:"+menu_price+"메뉴정보:"+menu_info);

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
        Request_update_menu requestRegister = new Request_update_menu(String.valueOf(menu_id),
                menu_name,menu_image,menu_price,menu_info, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddmenuActivity.this);
        queue.add(requestRegister);

    }

    //메뉴 삭제
    @Override
    public void destroy(int position) {
        itemManageMenus.remove(position);
        System.out.println("뭐가 삭제됬니"+menu_name+"아이디는?"+menu_id);
        adpt.notifyDataSetChanged();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) { // 등록에 성공한 경우
                        Toast.makeText(getApplicationContext(),"삭제 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    } else { // 등록에 실패한 경우
                        Toast.makeText(getApplicationContext(),"삭제 ㅊ실패하였습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_delete_menu requestRegister = new Request_delete_menu(String.valueOf(menu_id),
                 responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddmenuActivity.this);
        queue.add(requestRegister);

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



    private String encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, b);

        byte[] bytesOfImage = b.toByteArray();
        System.out.println("바이트값"+bytesOfImage);
        String encodeImageString = Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;

    }



    public void getData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    System.out.println("제이슨어레이길이는?"+jsonArray.length());


                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                                // Pulling items from the array
                                String item = jsonObject.getString("menu_name");
                                String item2 = jsonObject.getString("menu_image");
                                String item3 = jsonObject.getString("menu_price");
                                String item4 = jsonObject.getString("menu_info");
//                            Bitmap bit2 = StringToBitmaps(item2);
//                            imageView.setImageBitmap(bit2); // 액티비티에 이미지 표시
                                itemManageMenus.add(new ItemManageMenu(item, item4, item3, StringToBitmaps(item2)));
                                adpt.notifyDataSetChanged();
                                loadingDialogBar.HideDialog();

                                Log.v("여긴작동하나용", "네에");


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.v("작동실패", "안들어옴");
                            }
                        }
                       



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_menu requestRegister = new Request_get_menu(logining_store_id, responseListener);
        queue = Volley.newRequestQueue(AddmenuActivity.this);
        queue.add(requestRegister);


    }

}

