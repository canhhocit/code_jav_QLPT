package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlthutien_StatusRoomAdapter;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;
import com.example.logincustomer.data.Model.qlphongtro_PhongTro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class statusRoom extends AppCompatActivity {
    private List<qlphongtro_PhongTro> listPhong;
    private List<String> listTenPhong;
    private List<qlthutien_HoaDon> hoaDonList;
    private Spinner spinnerPhong;
    private EditText edtFromDate, edtToDate;
    private RecyclerView recyclerView;
    private ImageView imgback, imgdown;
    private TextView txtNgay;
    private RadioButton radida, radichua;
    private CheckBox showall;
    private qlthutien_StatusRoomAdapter adapter;
    private qlthutien_HoaDonDAO hoaDonDAO;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private boolean sortAsc = true; // true = tăng dần, false = giảm dần

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_statusroom);
        anhxa();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hoaDonDAO = new qlthutien_HoaDonDAO(this);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);

        hoaDonList = hoaDonDAO.getAllHoaDon();
        adapter = new qlthutien_StatusRoomAdapter(this, hoaDonList);
        recyclerView.setAdapter(adapter);

        // Gọi hàm filter mỗi khi người dùng thay đổi điều kiện lọc
        radida.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
        radichua.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
        showall.setOnCheckedChangeListener((buttonView, isChecked) -> filter());
        spinnerPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        edtFromDate.setOnClickListener(v -> showDatePickerDialog(edtFromDate));
        edtToDate.setOnClickListener(v -> showDatePickerDialog(edtToDate));

        // Lấy danh sách phòng từ DB
        listPhong = phongTroDAO.getAllPhongTro();
        listTenPhong = new ArrayList<>();
        listTenPhong.add("Tất cả"); // mục đầu tiên

        for (qlphongtro_PhongTro p : listPhong) {
            listTenPhong.add(p.getTenphong());
        }

        // Tạo adapter cho Spinner
        ArrayAdapter<String> adapterPhong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTenPhong);
        adapterPhong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhong.setAdapter(adapterPhong);

        // Xử lý khi chọn phòng
        spinnerPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // Khi người dùng nhập ngày
        edtFromDate.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) filter(); });
        edtToDate.setOnFocusChangeListener((v, hasFocus) -> { if (!hasFocus) filter(); });

        txtNgay.setOnClickListener(v -> {
            sortAsc = !sortAsc; // đảo chiều
            sortByNgay(sortAsc);
        });
        quayve();
    }
    private void anhxa() {
        spinnerPhong = findViewById(R.id.spinnerPhong_statusRoom);
        edtFromDate = findViewById(R.id.edt_tuDate_statusRoom);
        edtToDate = findViewById(R.id.edt_denDate_statusRoom);
        recyclerView = findViewById(R.id.recycler_status_statusRoom);
        imgback = findViewById(R.id.img_back_statusRoom);
        imgdown = findViewById(R.id.iconSortNgay_statusroom);
        txtNgay =  findViewById(R.id.txtdate_statusroom);
        radida = findViewById(R.id.radi_da);
        radichua = findViewById(R.id.radi_chua);
        showall = findViewById(R.id.checkbox_showall_statusroom);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (hoaDonDAO != null && adapter != null) {
            hoaDonList = hoaDonDAO.getAllHoaDon();
            adapter.setHoaDonList(hoaDonList);
            adapter.notifyDataSetChanged();
        }
    }

    private void filter() {
        String fromDate = convertToDbDate(edtFromDate.getText().toString().trim());
        String toDate = convertToDbDate(edtToDate.getText().toString().trim());
        String tenPhong = spinnerPhong.getSelectedItem() != null ? spinnerPhong.getSelectedItem().toString() : null;
        boolean showAllChecked = showall.isChecked();

        Boolean trangThai = null;
        if (radida.isChecked()) trangThai = true;
        else if (radichua.isChecked()) trangThai = false;

        hoaDonList = hoaDonDAO.filterHoaDon(fromDate, toDate, tenPhong, trangThai, showAllChecked);
        Log.d("FILTER", "Gọi filter() với from=" + fromDate + ", to=" + toDate);

        adapter.setHoaDonList(hoaDonList);
        adapter.notifyDataSetChanged();
    }
    //  Hàm hiển thị DatePicker
    private void showDatePickerDialog(EditText target) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String selected = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    target.setText(selected);
                    filter();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        //ngày chọn không được vượt quá ngày hiện tại
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        //chọn ngày kết thúc (toDate)
        if (target.getId() == R.id.edt_denDate_statusRoom) {
            String fromDateString = edtFromDate.getText().toString().trim();
            //ngày bắt đầu (fromDate) đã được chọn
            if (!fromDateString.isEmpty()) {
                try {
                    // Chuyển chuỗi ngày sang dạng Date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date fromDate = sdf.parse(fromDateString);
                    // không thể chọn ngày nào trước fromDate
                    if (fromDate != null) {
                        dialog.getDatePicker().setMinDate(fromDate.getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        dialog.show();
    }
    private String convertToDbDate(String date) {
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d = sdfInput.parse(date);
            return sdfOutput.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }
    private void sortByNgay(boolean asc) {
        Log.d("SORT", "sortByNgay() được gọi, asc = " + asc + ", list size = " + (hoaDonList != null ? hoaDonList.size() : 0));
        if (hoaDonList == null || hoaDonList.isEmpty()) return;

        Collections.sort(hoaDonList, (hd1, hd2) -> {
            Log.d("SORT", "Trước sort: " + hd1.getNgaytaohdon() + " - " + hd2.getNgaytaohdon());
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date d1 = sdf.parse(hd1.getNgaytaohdon());
                Date d2 = sdf.parse(hd2.getNgaytaohdon());
                if (asc) return d1.compareTo(d2);   // tăng dần
                else return d2.compareTo(d1);       // giảm dần
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        adapter.setHoaDonList(hoaDonList);

        ImageView imgArrow = findViewById(R.id.iconSortNgay_statusroom);
        if (imgArrow != null) {
            float rotation = asc ? 0f : 180f;
            imgArrow.animate().rotation(rotation).setDuration(200).start();
        }
    }
    private void quayve(){
        imgback.setOnClickListener(v -> {
            finish();
        });
    }
}
