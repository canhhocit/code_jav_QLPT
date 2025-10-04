package com.example.logincustomer.QLphong_tam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.DAO.PhongTroDAO;
import com.example.logincustomer.Model.PhongTro;
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

    private static final int REQUEST_ADD = 100;
    private static final int REQUEST_UPDATE = 101;

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


        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(qlphong_activity_home.this, "đã bấm", Toast.LENGTH_SHORT).show();
        });

        // 🔍 Tìm kiếm
        btnSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim();
            List<PhongTro> result = dao.searchPhong(keyword);
            adapter.updateList(result);
        });

        // ➕ Thêm phòng
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPhongActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        // ✏️ Sửa phòng
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdatePhongActivity.class);
            startActivityForResult(intent, REQUEST_UPDATE);
        });




//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            PhongTro selectedPhong = listPhong.get(position);
//            Toast.makeText(qlphong_activity_home.this, "đã bấm", Toast.LENGTH_SHORT).show();
//            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//            Intent intent = new Intent(qlphong_activity_home.this, UpdatePhongActivity.class);
//            intent.putExtra("idPhong", selectedPhong.getIdphong());
//            intent.putExtra("tenPhong", selectedPhong.getTenphong());
//            intent.putExtra("soNguoi", selectedPhong.getSonguoi());
//            intent.putExtra("gia", selectedPhong.getGia());
//            startActivity(intent);
//        });

    }

    // 📌 Nhận kết quả trả về từ AddPhongActivity hoặc UpdatePhongActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD) {
                String tenPhong = data.getStringExtra("tenPhong");
                int giaPhong = data.getIntExtra("giaPhong", 0);

                // dự phòng: nếu tên đã tồn tại thì thông báo và không insert
                if (dao.isPhongExists(tenPhong)) {
                    Toast.makeText(this, "Phòng đã tồn tại (đã được thêm trước đó).", Toast.LENGTH_SHORT).show();
                    return;
                }

                PhongTro phong = new PhongTro();
                phong.setTenphong(tenPhong);
                phong.setGia(giaPhong);
                phong.setSonguoi(0);  // mặc định khi thêm

                long id = dao.insertPhongTro(phong);
                if (id > 0) {
                    phong.setIdphong((int) id);
                    listPhong.add(phong);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Lưu phòng thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
            else if (requestCode == REQUEST_UPDATE) {
                // xử lý update...
            }
        }
    }

}
