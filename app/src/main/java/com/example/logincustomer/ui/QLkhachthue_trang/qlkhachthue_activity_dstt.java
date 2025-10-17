package com.example.logincustomer.ui.QLkhachthue_trang;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlkhachthueAdapter;
import com.example.logincustomer.data.DAO.qlkhachthue_khachthueDAO;
import com.example.logincustomer.data.Model.khachthue;

import java.util.List;

public class qlkhachthue_activity_dstt extends AppCompatActivity {

     EditText edtTK;
     ListView lvkhach;
     AppCompatButton btnThem;
     ImageView imgback;
     private qlkhachthue_khachthueDAO khachDAO;
     private qlkhachthueAdapter ktadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlkhachthue_activity_dstt);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // anh xa id
        anhxaid();
        //cac ham
        buttonthem();
        backHome();

        //listview
        listdshienthi();
        findbyName();

    }

    private void findbyName() {
        edtTK.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyw= s.toString().trim();
                khachDAO = new qlkhachthue_khachthueDAO(qlkhachthue_activity_dstt.this);
                List<khachthue> list;
                if(keyw.isEmpty()){
                    list= khachDAO.getAllKhachThue();
                }else{
                    list = khachDAO.getfindbyname(keyw);
                }
                ktadapter = new qlkhachthueAdapter(qlkhachthue_activity_dstt.this,list,khachDAO);
                lvkhach.setAdapter(ktadapter);
            }
        });
    }



    private void listdshienthi() {
        khachDAO= new qlkhachthue_khachthueDAO(qlkhachthue_activity_dstt.this);
        List<khachthue> listKhach = khachDAO.getAllKhachThue();
        ktadapter = new qlkhachthueAdapter(qlkhachthue_activity_dstt.this,listKhach,khachDAO);
        lvkhach.setAdapter(ktadapter);
    }

    private void backHome() {
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxaid() {
        edtTK = findViewById(R.id.khachthue_edt_timkiem);
        lvkhach = findViewById(R.id.khachthue_list_khachthue);
        btnThem = findViewById(R.id.khachthue_btn_themkhachthue);
        imgback = findViewById(R.id.khachthue_imgbtn_back);
    }

    private void buttonthem() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(qlkhachthue_activity_dstt.this, qlkhachthue_activity_chucnang.class);
                intent.putExtra("check",1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listdshienthi();
    }
}
