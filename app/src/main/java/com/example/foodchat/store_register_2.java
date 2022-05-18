package com.example.foodchat;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class store_register_2 extends AppCompatActivity {
    private EditText res_name, res_address, res_time,res_mension;
    private String name,address,time,mension;
    String res_image;
    private Button btn_submit,btn_uploadMENU;
    private ImageButton btn_uploadIMG;

    private static final int REQUEST_CODE = 0;
    private ImageView imageView;




    //http://218.236.123.14:9090 웹서버입니다 (외부접속전용)
    String url = "http://218.236.123.14:9090/input_res.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_register);
        dbNetworkUtil.setNetworkPolicy();
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



                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                        Log.v("들어감","들어감");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                        Toast.makeText(getApplication(),"안들어감",Toast.LENGTH_SHORT).show();
                        Log.v("안들어감","안들어감");
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> map = new HashMap<>();
                        // 1번 인자는 PHP 파일의 $_POST['']; 부분과 똑같이 해줘야 한다
                        map.put("res_name", String.valueOf(res_name.getText()));
                        map.put("res_address", String.valueOf(res_address.getText()));
                        map.put("res_time", String.valueOf(res_time.getText()));
                        map.put("res_image", res_image);
                        map.put("res_mension", String.valueOf(res_mension.getText()));


                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
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







}










