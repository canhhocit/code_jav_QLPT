package com.example.logincustomer.ui.QLphong_tam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.data.DAO.PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.R;

import java.util.ArrayList;
import java.util.List;

public class qlphong_activity_home extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch, btnAdd, btnUpdate;
    ListView listView;
    PhongTroDAO dao;
    PhongTroAdapter adapter;
    List<PhongTro> listPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_activity_dsphongtro);

        edtSearch = findViewById(R.id.edtSearch_phongtro);
        btnSearch = findViewById(R.id.btnSearch_phongtro);
        btnAdd = findViewById(R.id.btnAddRoom_phongtro);
        btnUpdate = findViewById(R.id.btnUpdateRoom_phongtro);
        listView = findViewById(R.id.listview_dsphong_phongtro);

        dao = new PhongTroDAO(this);
        listPhong = dao.getAllPhongTro();
        if (listPhong == null) {
            listPhong = new ArrayList<>();
        }
        adapter = new PhongTroAdapter(this, listPhong);
        listView.setAdapter(adapter);


        // Tìm kiếm
        btnSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim();
            List<PhongTro> result = dao.searchPhong(keyword);
            adapter.updateList(result);
        });

        // Thêm phòng
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPhongActivity.class);
            startActivity(intent);
        });

        // Sửa phòng
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdatePhongActivity.class);
            startActivity(intent);
        });
    }
}