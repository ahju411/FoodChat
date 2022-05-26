package com.example.foodchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Restaurant_List_test extends AppCompatActivity {
    static RequestQueue requestQueue;
    private RecyclerView rv;
    private Restaurant_ListAdapter_test adpt;
    private ArrayList<Restaurant_List_Item_test> res_items;
    private ImageButton backbtn;
    private ImageView menubtn;
    private Button LV;
    private ImageView IV;
    static String[] data1 = new String[20];
    static String[] data2 = new String[20];
    static Bitmap[] data3 = new Bitmap[20];
    private Context ct;
    private double latitude,longtitue;//위도 경도
    private GpsTracker gpsTracker;
    LoadingDialogBar loadingDialogBar;

    private String logining_user_id,logining_user_pw,logining_user_nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ct = getApplicationContext();
        setContentView(R.layout.restaurant_list2);
        Intent getintent = getIntent();
        String logining_user_id = getintent.getStringExtra("logining_user_id");
        String logining_user_pw = getintent.getStringExtra("logining_user_pw");
        String logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("아이디 :"+logining_user_id +"비번 :"+logining_user_pw+"닉네임:"+logining_user_nickname);
        setAddress();


        // ProgressDialog 생성
        //로딩창 객체 생성
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("불러오는 중.");


        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        getItem(); // 리사이클러뷰 아이템넣기

//
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                loadingDialogBar.HideDialog();
//            }
//        }, 2000);// 2초 딜레이 준후 로딩창 종료
//



//어댑터 세팅
        adpt = new Restaurant_ListAdapter_test();
//레이아웃 방식 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = findViewById(R.id.recycler_restaurant);
        rv.setAdapter(adpt);
        rv.setLayoutManager(manager);

        // ============어댑터 클릭리스너 구현 ===========
        adpt.setRes_list_item(res_items);
        adpt.setListClickListener(new Restaurant_listListener() {
            @Override
            public void onListClick(Restaurant_ListAdapter_test.ViewHolder holder, View view, int position) {
                Restaurant_List_Item_test item= adpt.getItem(position);
                int clicked_store_id = item.getRes_id();

                //해당 식당페이지로 이동
                Intent intent = new Intent(view.getContext(), Restaurant_Info.class);
                intent.putExtra("clicked_store_id", clicked_store_id);
                intent.putExtra("logining_user_id", logining_user_id);
                intent.putExtra("logining_user_nickname", logining_user_nickname);
                startActivity(intent);


                //상세페이지 띄워서 item에서 get을 통해 상세페이지로 넘겨서 띄우면 끝

            }
        });



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

    private void getItem() { // 식당 리스트 UI수정하는거

        res_items = new ArrayList<>();

       // String URL = "http://192.168.75.151:9090/load_store_info.php";
        String URL = "http://218.236.123.14:9090/load_store_info.php";


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("작동대냐","응?22");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.v("작동대냐","응?");


                    JSONArray jsonArray = jsonObject.getJSONArray("data");



                    for (int i=0; i < jsonArray.length(); i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("store_name");

                            String item2 = jsonObject.getString("store_address");


                            String item4 = jsonObject.getString("store_images");
                            int item5 = jsonObject.getInt("store_id");

                            Bitmap bit = StringToBitmaps(item4);

                            res_items.add(new Restaurant_List_Item_test(item,item2,item5,bit, R.drawable.chat, R.drawable.starimg)); //리스트 식당호출
                            adpt.notifyDataSetChanged();

                            adpt.setRes_list_item(res_items);
                            loadingDialogBar.HideDialog();;
                            System.out.println("data1 :"+item);

                            Log.v("여긴작동하나용","네에");



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("작동실패","안들어옴");
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

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


    private void setAddress(){
        gpsTracker = new GpsTracker(ct);

        latitude = gpsTracker.getLatitude();
        longtitue = gpsTracker.getLongitude();
        String city = getCurrentAddress(latitude,longtitue);
        LV = findViewById(R.id.address);
        LV.setText(city);
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더 GPS를 주소로 변환함.
        Geocoder geocoder = new Geocoder(ct, Locale.KOREA);

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            Toast.makeText(ct, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(ct, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(ct, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        String add = address.getAddressLine(0).toString();
        Log.d("주소",add);
        String[] city = address.getAddressLine(0).split(" ");
        for (int i = 0; i < city.length; i++) {
            Log.d("주소들 확인",city[i]);
        }

        String add2 = add.substring(4); //대한민국 문자열 자르기
        Log.d("주소",add2);
        return  add2;

    }











}

