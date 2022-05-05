package com.example.foodchat;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;

public class store_register extends AppCompatActivity {
    private EditText res_name, res_address, res_time,res_mension;
    private Button btn_submit;
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
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://foodchat.dothome.co.kr/input_res.php"); // 주소
                    String result = request.res_inputDB(String.valueOf(res_name.getText()),
                            String.valueOf(res_address.getText()),String.valueOf(res_time.getText())
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
}


