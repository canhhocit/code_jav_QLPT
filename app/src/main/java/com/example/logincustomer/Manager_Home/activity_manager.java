package com.example.logincustomer.Manager_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.QLbaocao_canh.baocao_activity_homebc;
import com.example.logincustomer.QLhopdong_y.hopdong_activity_home;
import com.example.logincustomer.QLkhachthue_trang.qlkhachthue_activity_dstt;
import com.example.logincustomer.QLphong_tam.qlphong_activity_home;
import com.example.logincustomer.QLthutien_nguyen.statusRoom;
import com.example.logincustomer.R;

public class activity_manager extends AppCompatActivity {
    Button btnphong,btnkhach,btnthutien,btnhopdong,btnbaocao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manager_activity_homemanager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnphong = findViewById(R.id.manager_btnphongtro);
        btnkhach = findViewById(R.id.manager_btnkhach);
        btnthutien = findViewById(R.id.manager_btnthutien);
        btnhopdong = findViewById(R.id.manager_btnhopdong);
        btnbaocao = findViewById(R.id.manager_btnbaocao);
        btnphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_manager.this, qlphong_activity_home.class);
                startActivity(intent);
            }
        });
        btnkhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(activity_manager.this, qlkhachthue_activity_dstt.class);
                startActivity(intent1);
            }
        });
        btnhopdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(activity_manager.this, hopdong_activity_home.class);
                startActivity(intent2);
            }
        });
        btnbaocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 =new Intent(activity_manager.this, baocao_activity_homebc.class);
                startActivity(intent3);
            }
        });
        btnthutien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 =new Intent(activity_manager.this, statusRoom.class);
                startActivity(intent4);
            }
        });
    }
}