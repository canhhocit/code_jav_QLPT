package com.example.logincustomer.ui.QLphong_tam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.PersonInRoomAdapter;
import com.example.logincustomer.data.DAO.khachthueDAO;
import com.example.logincustomer.data.DAO.PhongTroDAO;
import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;

import java.util.List;

public class DetailInRoom extends AppCompatActivity {

    private ListView listView;
    private Button btnThem, btnBack;
    private TextView txtTenPhong;
    private khachthueDAO khachDAO;
    private PhongTroDAO phongDAO;
    private PersonInRoomAdapter adapter;
    private int idPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_layout_detail_room);

        txtTenPhong = findViewById(R.id.txtPhong_detailRoom);
        listView = findViewById(R.id.listView_detailRoom);
        btnThem = findViewById(R.id.btn_ThemNguoi_detailRoom);
        btnBack = findViewById(R.id.btn_QuayLai_detailRoom);

        idPhong = getIntent().getIntExtra("idPhong", -1);

        phongDAO = new PhongTroDAO(this);
        khachDAO = new khachthueDAO(this);

        // set tên phòng
        PhongTro phong = phongDAO.getPhongById(idPhong);
        if (phong != null) {
            txtTenPhong.setText(phong.getTenphong());
        } else {
            txtTenPhong.setText("Không tìm thấy phòng");
        }

        // lấy danh sách khách chỉ của phòng này
        List<khachthue> listKhach = khachDAO.getKhachTheoPhong(idPhong);
        adapter = new PersonInRoomAdapter(this, listKhach);
        listView.setAdapter(adapter);

        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(this, qlkhachthue_activity_chucnang.class);
            intent.putExtra("check", 1);
            intent.putExtra("idPhong", idPhong);
            intent.putExtra("tenphong", phong.getTenphong());
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<khachthue> listKhach = khachDAO.getKhachTheoPhong(idPhong);
        adapter = new PersonInRoomAdapter(this, listKhach);
        listView.setAdapter(adapter);
    }
}
