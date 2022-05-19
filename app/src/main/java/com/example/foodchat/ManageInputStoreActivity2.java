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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageInputStoreActivity2 extends AppCompatActivity {
    private EditText res_name, res_address, res_time,res_mension;
    private String name,address,time,mension;
    String res_image;
    private Button btn_submit,btn_uploadMENU;
    private ImageButton btn_uploadIMG;

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private String store_name,store_address,store_time,store_image,store_mension,
            logining_ceo_pw,logining_ceo_id;
    static RequestQueue requestQueue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_register);
        dbNetworkUtil.setNetworkPolicy();

        Intent getintent = getIntent();
        logining_ceo_id = getintent.getStringExtra("logining_ceo_id");
        logining_ceo_pw = getintent.getStringExtra("logining_ceo_pw");
        System.out.println("아이디:"+logining_ceo_id+"비번"+logining_ceo_pw);

        res_name = (EditText)findViewById(R.id.rest_name);

        res_address = (EditText)findViewById(R.id.rest_addr);
        res_time = (EditText)findViewById(R.id.rest_time);
        res_mension = (EditText)findViewById(R.id.rest_mention);
        btn_submit = (Button)findViewById(R.id.store_submit);



        btn_uploadIMG = (ImageButton)findViewById((R.id.btn_loadIMG));
        btn_uploadMENU =(Button)findViewById((R.id.addmenu));
        btn_uploadMENU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddmenuActivity.class);
                startActivity(intent);
            }
        });

        imageView = (ImageView)findViewById((R.id.img1));



        getData();

        btn_uploadIMG.setOnClickListener(new View.OnClickListener(){ // + 버튼 누르면 갤러리 이미지 따오기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() { // 등록하기 누르면 서버로 입력한값 전달하기
            @Override
            public void onClick(View v) {

                store_name = res_name.getText().toString();
                store_address = res_address.getText().toString();
                store_time = res_time.getText().toString();
                store_image = res_image;
                System.out.println("길이선:"+store_image.length());

                store_mension = res_mension.getText().toString();

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
                Request_InputStore requestRegister = new Request_InputStore(store_name,store_address,store_time,res_image,store_mension,logining_ceo_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ManageInputStoreActivity2.this);
                queue.add(requestRegister);



            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK )
        {

            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                imageView.setImageBitmap(img); // 액티비티에 이미지 표시
                res_image = encodeBitmapImage(img);
                Log.v("값:",res_image);
                System.out.println("길이"+res_image.length());
            }   catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
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







    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static byte[] BitmapToByteArray(Bitmap bitmap) { // 이미지 > Byte 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        return baos.toByteArray();
    }

    public Bitmap byteArrayToBitmap( byte[] byteArray ) { // Byte >> 이미지 변환
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
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
                            imageView.setImageBitmap(bit2); // 액티비티에 이미지 표시
                            String item5 = jsonObject.getString("store_mension");

                            res_name.setText(item);
                            res_address.setText(item2);
                            res_time.setText(item3);
                            res_time.setText(item5);

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
        }; // 서버로 Volley를 이용해서 요청을 함.
        Request_get_CEO_storeInfo requestRegister = new Request_get_CEO_storeInfo(logining_ceo_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ManageInputStoreActivity2.this);
        queue.add(requestRegister);


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







}










