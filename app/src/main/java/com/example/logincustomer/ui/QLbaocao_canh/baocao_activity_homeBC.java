package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.baocao_phongAdapter;
import com.example.logincustomer.data.Adapter.khachthueAdapter;
import com.example.logincustomer.data.DAO.PhongTroDAO;
import com.example.logincustomer.data.DAO.khachthueDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_dstt;
import com.github.mikephil.charting.charts.BarChart;

import java.util.List;

public class baocao_activity_homeBC extends AppCompatActivity {
    private TabHost tabHost;
    private TextView tvTitle_baocao;

    // DT
    private BarChart barChartDT;
    private ListView lv_namDT;
    private Button btnThuChi;

    // Phong
    private ListView lv_Phong;

    //Khach
    private ListView lv_Khach;
    private khachthueDAO khachDAO;
    private khachthueAdapter ktadapter;

    //Hop dong
    private ListView lv_Hopdong;
    private PhongTroDAO ptDAO;
    private baocao_phongAdapter ptAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activity_home_baocao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainbc), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxaID();
        back_manager();
        //tab doanh thu
        gotoThuchi();
        //tab phong
        listviewphong();
        //tab khach
        listviewkhach();
        //tab hop dong
    }




    private void listviewphong() {
        ptDAO = new PhongTroDAO(baocao_activity_homeBC.this);
        List<PhongTro> listPT = ptDAO.getAllPhongTro();
        ptAdapter =new baocao_phongAdapter(baocao_activity_homeBC.this,listPT,ptDAO);
        lv_Phong.setAdapter(ptAdapter);
    }

    private void listviewkhach() {
        khachDAO= new khachthueDAO(baocao_activity_homeBC.this);
        List<khachthue> listKhach = khachDAO.getAllKhachThue();
        ktadapter = new khachthueAdapter(baocao_activity_homeBC.this,listKhach,khachDAO);
        lv_Khach.setAdapter(ktadapter);
    }
    private void gotoThuchi() {
        btnThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baocao_activity_homeBC.this, baocao_activity_homedsthuchi.class);
                startActivity(intent);
            }
        });

    }
    private void back_manager() {
        tvTitle_baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhxaID() {
        //chung
        tvTitle_baocao = findViewById(R.id.baocao_tvTitle);
        tabHost = findViewById(R.id.baocao_tabhost_DT);
        tabHost.setup();

        // 4 Tab
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("Doanh thu");
        tab1.setContent(R.id.tab_DT);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("Phòng");
        tab2.setContent(R.id.tab_phong);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setIndicator("Khách thuê");
        tab3.setContent(R.id.tab_khach);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4");
        tab4.setIndicator("Hợp đồng");
        tab4.setContent(R.id.tab_HD);
        tabHost.addTab(tab4);
        //set tab mac dinh
        tabHost.setCurrentTab(0);

        // Tab Doanh thu
        barChartDT = findViewById(R.id.baocao_barChartDT);
        lv_namDT = findViewById(R.id.baocao_doanhthu_listnamdt);
        btnThuChi = findViewById(R.id.baocao_btn_thuchi);

        // Tab Phòng
        lv_Phong = findViewById(R.id.baocao_phong_listview);

        // Tab Khách
        lv_Khach = findViewById(R.id.baocao_khach_listview);

        // Tab Hợp đồng
        lv_Hopdong = findViewById(R.id.baocao_hopdong_listview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listviewkhach();
        listviewphong();
    }
}
