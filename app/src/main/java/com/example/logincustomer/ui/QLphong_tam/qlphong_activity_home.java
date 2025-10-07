package com.example.logincustomer.ui.QLphong_tam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.data.Adapter.qlphongtro_PhongTroAdapter;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.R;

import java.util.ArrayList;
import java.util.List;

public class qlphong_activity_home extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch, btnAdd, btnUpdate;
    ListView listView;
    qlphongtro_PhongTroDAO dao;
    qlphongtro_PhongTroAdapter adapter;
    List<PhongTro> listPhong;
    PhongTro phongDangChon = null;
    TextView tvtitle;

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
        tvtitle = findViewById(R.id.qlphongtro_dsphong_tvTitle);
        tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dao = new qlphongtro_PhongTroDAO(this);
        listPhong = dao.getAllPhongTro();
        if (listPhong == null) {
            listPhong = new ArrayList<>();
        }

        // Cập nhật số người cho từng phòng (đồng bộ trước khi hiển thị)
        listPhong = dao.getAllPhongTro();
        for (PhongTro p : listPhong) {
            dao.updateSoNguoiPhong(p.getIdphong());
        }

        adapter = new qlphongtro_PhongTroAdapter(this, listPhong);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            phongDangChon = listPhong.get(position);
            Toast.makeText(this, "Đã chọn phòng: " + phongDangChon.getTenphong(), Toast.LENGTH_SHORT).show();
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
            if (phongDangChon == null) {
                Toast.makeText(this, "Vui lòng chọn phòng để sửa!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, UpdatePhongActivity.class);
            intent.putExtra("idPhong", phongDangChon.getIdphong());
            intent.putExtra("tenPhong", phongDangChon.getTenphong());
            intent.putExtra("soNguoi", phongDangChon.getSonguoi());
            intent.putExtra("gia", phongDangChon.getGia());
            startActivityForResult(intent, REQUEST_UPDATE);
        });
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
                //xử lý update
                int idPhong = data.getIntExtra("idPhong", -1);
                String tenPhong = data.getStringExtra("tenPhong");
                double giaPhong = data.getDoubleExtra("gia", 0);

                // Cập nhật lại trong danh sách
                for (PhongTro p : listPhong) {
                    if (p.getIdphong() == idPhong) {
                        p.setTenphong(tenPhong);
                        p.setGia(giaPhong);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

        }
    }
    public void setPhongDangChon(PhongTro pt) {
        this.phongDangChon = pt;
    }
    @Override
    protected void onResume() {
        super.onResume();

        for (PhongTro p : dao.getAllPhongTro()) {
            dao.updateSoNguoiPhong(p.getIdphong());
        }

        listPhong.clear();
        listPhong.addAll(dao.getAllPhongTro());
        adapter.notifyDataSetChanged();
    }


}
