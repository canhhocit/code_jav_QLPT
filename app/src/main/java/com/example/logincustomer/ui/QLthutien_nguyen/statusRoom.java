package com.example.logincustomer.ui.QLthutien_nguyen;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;

import java.util.ArrayList;
import java.util.List;

public class statusRoom extends AppCompatActivity {

    private Spinner spinnerPhong;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private List<PhongTro> listPhong;
    private List<String> listTenPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_statusroom);

        spinnerPhong = findViewById(R.id.spinnerPhong);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);

        // 🔹 Lấy danh sách phòng từ DB
        listPhong = phongTroDAO.getAllPhongTro();
        listTenPhong = new ArrayList<>();
        listTenPhong.add("Tất cả"); // mục đầu tiên

        for (PhongTro p : listPhong) {
            listTenPhong.add(p.getTenphong());
        }

        // 🔹 Tạo adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listTenPhong
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhong.setAdapter(adapter);

        // 🔹 Xử lý khi chọn phòng
        spinnerPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = listTenPhong.get(position);
                if (selected.equals("Tất cả")) {
                    // ✅ Hiển thị tất cả dữ liệu
                    Log.d("FILTER", "Hiển thị tất cả phòng");
                    // Gọi hàm load toàn bộ danh sách (VD: loadAllBills();)
                } else {
                    // ✅ Lấy id phòng tương ứng
                    PhongTro selectedPhong = listPhong.get(position - 1); // vì "Tất cả" ở vị trí 0
                    int idPhong = selectedPhong.getIdphong();
                    Log.d("FILTER", "Lọc theo phòng ID: " + idPhong + ", tên: " + selected);
                    // Gọi hàm lọc dữ liệu theo idPhong
                    // filterDataByRoom(idPhong);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
