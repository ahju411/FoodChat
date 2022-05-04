package com.example.foodchat;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;

public class dbinputtest extends AppCompatActivity {
    private EditText data1, data2, data3;
    private Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbinputtest);
        dbNetworkUtil.setNetworkPolicy();
        data1 = (EditText)findViewById(R.id.editText);
        data2 = (EditText)findViewById(R.id.editText2);
        data3 = (EditText)findViewById(R.id.editText3);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://foodchat.dothome.co.kr/input_db.php");
                    String result = request.PhPtest(String.valueOf(data1.getText()),String.valueOf(data2.getText()),String.valueOf(data3.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}


