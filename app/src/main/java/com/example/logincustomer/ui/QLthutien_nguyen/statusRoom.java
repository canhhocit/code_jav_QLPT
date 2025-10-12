package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.StatusRoomAdapter;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.Model.HoaDon;
import com.example.logincustomer.data.Model.PhongTro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class statusRoom extends AppCompatActivity {
    private List<PhongTro> listPhong;
    private List<String> listTenPhong;
    private List<HoaDon> hoaDonList;
    private Spinner spinnerPhong;
    private EditText edtFromDate, edtToDate;
    private RecyclerView recyclerView;
    private Button btnTimKiem;
    private ImageView imgback;
    private StatusRoomAdapter adapter;
    private qlthutien_HoaDonDAO hoaDonDAO;
    private qlphongtro_PhongTroDAO phongTroDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_statusroom);
        anhxa();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hoaDonDAO = new qlthutien_HoaDonDAO(this);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);

        hoaDonList = hoaDonDAO.getAllHoaDon(); // lấy tất cả hóa đơn ban đầu
        adapter = new StatusRoomAdapter(this, hoaDonList);
        recyclerView.setAdapter(adapter);





        // 🔹 Nút tìm kiếm
        btnTimKiem.setOnClickListener(v -> {
            String tuNgay = edtFromDate.getText().toString();
            String denNgay = edtToDate.getText().toString();
            String tenPhong = spinnerPhong.getSelectedItem().toString();

            int idPhong = -1;
            if (!tenPhong.equals("Tất cả")) {
                PhongTro selected = listPhong.get(spinnerPhong.getSelectedItemPosition() - 1);
                idPhong = selected.getIdphong();
            }
            filterHoaDon(tuNgay, denNgay, idPhong);
        });
    }


    // 🔹 Hàm lọc hóa đơn
    private void filterHoaDon(String tuNgay, String denNgay, int idPhong) {
        hoaDonList.clear();
        hoaDonList.addAll(hoaDonDAO.getFilteredHoaDon(tuNgay, denNgay, idPhong));
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        spinnerPhong = findViewById(R.id.spinnerPhong_statusRoom);
        btnTimKiem = findViewById(R.id.btn_find_statusRoom);
        edtFromDate = findViewById(R.id.edt_tuDate_statusRoom);
        edtToDate = findViewById(R.id.edt_denDate_statusRoom);
        recyclerView = findViewById(R.id.recycler_status_statusRoom);
        imgback = findViewById(R.id.img_back_statusRoom);
    }

    @Override
    protected void onResume() {
        super.onResume();

        hoaDonList = hoaDonDAO.getAllHoaDon(); // lấy tất cả hóa đơn ban đầu
        adapter = new StatusRoomAdapter(this, hoaDonList);
        recyclerView.setAdapter(adapter);
    }
}
