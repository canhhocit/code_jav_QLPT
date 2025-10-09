package com.example.logincustomer.ui.QLhopdong_y;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlhopdong_dsAdapter;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;
import com.example.logincustomer.data.Model.hopdong_display;

import java.util.List;

public class hopdong_activity_dshopdong extends AppCompatActivity {
    private TextView tvTitle;
    private ListView lvHopDong;
    private qlhopdong_dsAdapter adapter;
    private qlhopdong_hopdongDAO hdDAO;
    private List<hopdong_display> listHopDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlhopdong_activity_dshopdong);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainhopdong), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        listviewShow();
        listControl();
    }

    private void anhXa() {
        lvHopDong = findViewById(R.id.hopdong_listphong);
        hdDAO = new qlhopdong_hopdongDAO(this);
        tvTitle= findViewById(R.id.hopdong_home_tvhd);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hopdong_activity_dshopdong.this,hopdong_activity_showphong.class);
                startActivity(intent);
            }
        });
    }

    private void listviewShow() {
        listHopDong = hdDAO.getAllHopDongDisplay();
        if(adapter==null){
            adapter = new qlhopdong_dsAdapter(this, listHopDong);
            lvHopDong.setAdapter(adapter);
        }else{
            adapter.updateData(listHopDong);
        }

    }

    private void listControl() {
        lvHopDong.setOnItemClickListener((parent, view, position, id) -> {
            hopdong_display hd = (hopdong_display) parent.getItemAtPosition(position);
            int idhd = hdDAO.getIdHDbytenphong(hd.getTenphong());
            Intent intent = new Intent(hopdong_activity_dshopdong.this, hopdong_activity_chucnang.class);
            intent.putExtra("check",3);
            intent.putExtra("idhd",idhd);
            intent.putExtra("tenp",hd.getTenphong());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listviewShow();
    }
}
