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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class store_register extends AppCompatActivity {
    private EditText res_name, res_address, res_time,res_mension;
    byte[] res_image;
    private Button btn_submit;
    private ImageButton btn_uploadIMG;
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
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


        btn_submit.setOnClickListener(new View.OnClickListener() { // 등록하기 누르면 서버로 입력한값 전달하기 (이미지 제외)
            @Override
            public void onClick(View v) {


                try {
                    PHPRequest request = new PHPRequest("http://foodchat.dothome.co.kr/input_resDBNEW.php"); // 주소
                    String result = request.res_inputDB(String.valueOf(res_name.getText()),
                            String.valueOf(res_address.getText()),String.valueOf(res_time.getText())
                            ,"test"
                            ,String.valueOf(res_mension.getText())); // 전달할 데이터들
                    if(result.equals("1")){ // DB에 들어갔다면
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                    }
                    else{ // DB에 안들어갔다면
                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    res_image = BitmapToByteArray(img);
                    imageView.setImageBitmap(img); // 액티비티에 이미지 표시
                    System.out.println("바이트값:"+res_image);





                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }
//    public static Bitmap StringToBitmap(String encodedString) {
//        try {
//            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        } catch (Exception e) {
//            e.getMessage();
//            return null;
//        }
//    }

    public static byte[] BitmapToByteArray(Bitmap bitmap) { // 이미지 > Byte 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        return baos.toByteArray();
    }

    public Bitmap byteArrayToBitmap( byte[] byteArray ) { // Byte >> 이미지 변환
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }







}










