package com.example.foodchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StoreregisterActivity extends AppCompatActivity {
    private Button addmenubtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_register);

        addmenubtn = findViewById(R.id.addmenu);
        addmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddmenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
