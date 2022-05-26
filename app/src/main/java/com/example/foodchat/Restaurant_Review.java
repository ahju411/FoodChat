package com.example.foodchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Restaurant_Review extends AppCompatActivity {
    EditText edt;
    ImageView imageView1,imageView2,imageView3,like_btn,dislike_btn;
    private String Str_img1="",Str_img2="",Str_img3="";
    Button pic_btn,upload_btn;
    private int count=0,check_like=0;
    private static final int REQUEST_CODE = 0;
    private String logining_user_id,logining_user_nickname;
    private int clicked_store_id;
    private String review_mension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_review);
        Intent getintent = getIntent();
        clicked_store_id = getintent.getIntExtra("clicked_store_id",0);
        logining_user_id = getintent.getStringExtra("logining_user_id");
        logining_user_nickname = getintent.getStringExtra("logining_user_nickname");

        System.out.println("가게아이디:"+clicked_store_id+"유저아이디:"+logining_user_id+"유저닉네임"+logining_user_nickname);



        edt = findViewById(R.id.review_text);
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);
        pic_btn = findViewById(R.id.picture_upload_btn);
        upload_btn = findViewById(R.id.review_upload_btn);
        like_btn = findViewById(R.id.likeView4);
        dislike_btn = findViewById(R.id.dislikeView3);


        pic_btn.setOnClickListener(new View.OnClickListener(){ //  갤러리 이미지 따오기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        like_btn.setOnClickListener(new View.OnClickListener(){ //  좋아요 이미지 누르면 이미지 바뀜
            @Override
            public void onClick(View v) {
                like_btn.setImageResource(R.drawable.goodimg_picked);
                dislike_btn.setImageResource(R.drawable.badimg);
                check_like=1;
            }
        });

        dislike_btn.setOnClickListener(new View.OnClickListener(){ //  싫어요 이미지 누르면 이미지 바뀜
            @Override
            public void onClick(View v) {
                like_btn.setImageResource(R.drawable.goodimg);
                dislike_btn.setImageResource(R.drawable.badimg_picked);
                check_like=-1;
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener(){ //  등록하기 누르면 서버로 전달
            @Override
            public void onClick(View v) {

                review_mension = edt.getText().toString();
                if(review_mension.length()<20){
                    Toast.makeText(getApplicationContext(),"리뷰는 최소 20자이상 작성해야합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_like==0){
                    Toast.makeText(getApplicationContext(),"리뷰 평가해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else {


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "리뷰 등록 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else { // 등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }; // 서버로 Volley를 이용해서 요청을 함.
                    Request_Insert_Review requestRegister = new Request_Insert_Review(logining_user_id,
                            String.valueOf(clicked_store_id),
                            Str_img1, Str_img2, Str_img3, String.valueOf(check_like), review_mension, logining_user_nickname, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Restaurant_Review.this);
                    queue.add(requestRegister);

                }




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

                if(count==0){
                    imageView1.setImageBitmap(img);
                    Str_img1 = encodeBitmapImage(img);
                    count++;
                }
                else if(count==1){
                    imageView2.setImageBitmap(img);
                    Str_img2 = encodeBitmapImage(img);
                    count++;
                }
                else if(count==2){
                    imageView3.setImageBitmap(img);
                    Str_img3 = encodeBitmapImage(img);
                }


            }   catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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













}





