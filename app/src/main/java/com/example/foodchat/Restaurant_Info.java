package com.example.foodchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Restaurant_Info extends AppCompatActivity {
    Button go_review_btn,go_reservation_btn,go_map_btn;
    private ArrayList<ItemManageMenu> itemManageMenus2;
    private AddmenuAdapter adpt2;
    private GetMenuReview_Adapter adpt3;
    private ArrayList<GetMenuReview_Item> Review_item;
    private RecyclerView rv2;
    private RecyclerView rv3;
    private int clicked_store_id;
    private TextView res_name,res_address,res_time,res_image,res_mension,res_revisit;
    private ImageView imageView;
    LoadingDialogBar loadingDialogBar;
    static RequestQueue requestQueue,queue,queue2,queue6;
    private String logining_user_id,logining_user_nickname,clicked_store_name,clicked_store_address;
    private Button go_faq_btn;
    private ScrollView sc;
    private TextView revisit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        rv2 = findViewById(R.id.recycle);
        rv3 = findViewById(R.id.recycle2);
        sc = (ScrollView) findViewById(R.id.scroll) ;
        sc.post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        

        // ProgressDialog ??????
        //????????? ?????? ??????
        loadingDialogBar = new LoadingDialogBar(this);
        loadingDialogBar.ShowDilaog("???????????? ???.");



        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue == null){
            queue = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue2 == null){
            queue2 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        if(queue6 == null){
            queue6 = Volley.newRequestQueue(getApplicationContext());
            loadingDialogBar.HideDialog();

        }
        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");
        System.out.println("???????????????:"+clicked_store_id);
        System.out.println("???????????????:"+logining_user_id);
        System.out.println("???????????????:"+logining_user_nickname);


        res_name = findViewById(R.id.store_name);
        res_address = findViewById(R.id.store_address);
        res_time = findViewById(R.id.store_time);
        res_mension = findViewById(R.id.store_mension);
        imageView = findViewById(R.id.imageView2);
        revisit = findViewById(R.id.store_revisit);
        go_faq_btn = findViewById(R.id.go_faq);
        go_faq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserFaqAcitivity.class);
                intent.putExtra("clicked_store_id",clicked_store_id);
                startActivity(intent);

            }
        });


        getData();
        getMenuData();
        getItem_Menu();
        getReviewData();
        getItem_Review();
        getRevisitData();






        //????????? ??????
        adpt2 = new AddmenuAdapter();
        //???????????? ?????? ??????
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv2.setAdapter(adpt2);
        rv2.setLayoutManager(manager);

        adpt2.setItemManageMenus(itemManageMenus2);

        adpt3 = new GetMenuReview_Adapter();
        //???????????? ?????? ??????
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);

        rv3.setAdapter(adpt3);
        rv3.setLayoutManager(manager2);

        adpt3.setReview_item(Review_item);






















        go_review_btn = (Button) findViewById(R.id.go_review_btn);
        go_review_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //??????????????? ??????
                Intent intent = new Intent(view.getContext(), Restaurant_Review.class);
                intent.putExtra("logining_user_id",logining_user_id);
                intent.putExtra("logining_user_nickname",logining_user_nickname);
                intent.putExtra("clicked_store_id",clicked_store_id);
                startActivity(intent);
            }
        });

        go_reservation_btn = (Button) findViewById(R.id.go_reservation_btn);
        go_reservation_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //??????????????? ??????
                Intent intent = new Intent(view.getContext(), Restaurant_Reservation.class);
                intent.putExtra("logining_user_id",logining_user_id);
                intent.putExtra("logining_user_nickname",logining_user_nickname);
                intent.putExtra("clicked_store_id",clicked_store_id);
                intent.putExtra("clicked_store_name",clicked_store_name);
                intent.putExtra("clicked_store_address",clicked_store_address);
                startActivity(intent);
            }
        });

        go_map_btn = (Button) findViewById(R.id.go_map_btn);
        go_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Restaurant_map.class);
                intent.putExtra("map_address",res_address.getText().toString());
                Log.d("?????? ??????: info",res_address.getText().toString());
                startActivity(intent);
            }
        });





    }

    private void getItem_Menu() {
        itemManageMenus2 = new ArrayList<>();

    }

    private void getItem_Review() {
        Review_item = new ArrayList<>();

    }


    public void getData() {

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
                            String item = jsonObject.getString("store_name");
                            String item2 = jsonObject.getString("store_address");
                            String item3 = jsonObject.getString("store_time");
                            String item4 = jsonObject.getString("store_images");
                            Bitmap bit2 = StringToBitmaps(item4);
                            imageView.setImageBitmap(bit2); // ??????????????? ????????? ??????

                            String item5 = jsonObject.getString("store_mension");

                            res_name.setText(item);
                            res_address.setText(item2);
                            res_time.setText(item3);
                            res_mension.setText(item5);
                            System.out.println("?????????????????????"+res_name.getText().toString());
                            clicked_store_name = item;
                            clicked_store_address = item2;



                            Log.v("?????????????????????","??????");



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("????????????","????????????");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_store_info requestRegister = new Request_store_info(clicked_store_id, responseListener);
        queue = Volley.newRequestQueue(Restaurant_Info.this);
        queue.add(requestRegister);


    }

    public void getRevisitData() {

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
                            int item = jsonObject.getInt("count");
                            revisit.setText("??? ????????? : "+item+"???");
                            loadingDialogBar.HideDialog();




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("????????????","????????????");
                            loadingDialogBar.HideDialog();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_revisit requestRegister = new Request_revisit(clicked_store_id, responseListener);
        queue6 = Volley.newRequestQueue(Restaurant_Info.this);
        queue6.add(requestRegister);


    }

    public void getMenuData() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    System.out.println("????????????????????????????"+jsonArray.length());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            // Pulling items from the array
                            String item = jsonObject.getString("menu_name");
                            String item2 = jsonObject.getString("menu_image");
                            String item3 = jsonObject.getString("menu_price");
                            String item4 = jsonObject.getString("menu_info");
//                            Bitmap bit2 = StringToBitmaps(item2);
//                            imageView.setImageBitmap(bit2); // ??????????????? ????????? ??????
                            itemManageMenus2.add(new ItemManageMenu(item, item4, item3, StringToBitmaps(item2)));
                            adpt2.notifyDataSetChanged();
                            adpt2.setItemManageMenus(itemManageMenus2);
                            loadingDialogBar.HideDialog();



                            Log.v("?????????????????????", "??????");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("????????????", "????????????");
                            loadingDialogBar.HideDialog();
                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_get_menu requestRegister = new Request_get_menu(clicked_store_id, responseListener);
        queue = Volley.newRequestQueue(Restaurant_Info.this);
        queue.add(requestRegister);



    }

    public void getReviewData() {

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
                            String item = jsonObject.getString("user_nickname");

                            String item2 = jsonObject.getString("review_image1");
                            Bitmap bit1 = StringToBitmaps(item2);

                            String item3 = jsonObject.getString("review_image2");
                            Bitmap bit2 = StringToBitmaps(item3);

                            String item4 = jsonObject.getString("review_image3");
                            Bitmap bit3 = StringToBitmaps(item4);

                            int item5 = jsonObject.getInt("review_like");
                            if(item5==1){ // ???????????? 1????????? 1??? ??????
                                item5 = R.drawable.goodimg;
                            }
                            if(item5==-1){ // ???????????? -1????????? -1??? ????????????????????????
                                item5 = R.drawable.badimg;
                            }
                            String item6 = jsonObject.getString("review_mension");
                            String item7 = jsonObject.getString("review_datetime");
                            String item8 = jsonObject.getString("review_ceo_mension");
                            if(item8.equals("null")){
                                item8 = "???????????? ????????? ?????? ?????????.";
                            }

                            System.out.println("???????????????????????????");
                            System.out.println("??????????????????:"+item);




                            Review_item.add(new GetMenuReview_Item(item,item7,item6,item5,
                                    bit1,bit2,bit3,item8));
                            adpt3.notifyDataSetChanged();

                            adpt3.setReview_item(Review_item);
                            loadingDialogBar.HideDialog();;




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("????????????","????????????");
                            loadingDialogBar.HideDialog();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialogBar.HideDialog();
                }

            }
        }; // ????????? Volley??? ???????????? ????????? ???.
        Request_get_Review requestRegister = new Request_get_Review(clicked_store_id, responseListener);
        queue2 = Volley.newRequestQueue(Restaurant_Info.this);
        queue2.add(requestRegister);


    }





    public static Bitmap StringToBitmaps(String image) { // ???????????? ????????? ???????????? ??????????????? ???????????? ??????
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            // Base64 ????????? ??????????????? ????????? ????????? ??????
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            // ????????? ????????? ??????????????? ????????? ????????? ??????
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }









}





