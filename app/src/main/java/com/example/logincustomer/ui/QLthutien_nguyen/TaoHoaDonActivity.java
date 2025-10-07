package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.TotalPriceAdapter;
import com.example.logincustomer.data.Adapter.DichVuConAdapter;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.DichVuCon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaoHoaDonActivity extends AppCompatActivity {

    private EditText edtOldElectric, edtNewElectric, edtOldWater, edtNewWater, edtdate;
    private TextView txtTongTien, txtGiaPhong, txtTenPhong;

    // views cho "Tiền dịch vụ khác"
    private TextView txtOtherServiceTotal;
    private ImageView imgExpandOther, imgBack;
    private RecyclerView recyclerOtherService;
    private boolean isOtherExpanded = false;
    private Button btnTaoHoaDon;
    private RecyclerView recyclerView;
    private TotalPriceAdapter adapter;
    private ArrayList<DichVuCon> listDichVu;

    // danh sách dịch vụ con (lấy từ DB)
    private qlthutien_DichVuConDAO dichVuConDAO;
    private ArrayList<DichVuCon> listOtherServices;
    private DichVuConAdapter otherAdapter;
    private double totalOtherServices = 0.0;

    private double giaDien;
    private double giaNuoc;

    private static final double GIA_DIEN_MACDINH = 3500;
    private static final double GIA_NUOC_MACDINH = 15000;
    private final DecimalFormat df = new DecimalFormat("#,###");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_total_priceroom);
        anhxaid();

        double giaphong = getIntent().getDoubleExtra("giaphong", 0);
        // giữ nguyên kiểu hiển thị hiện tại (nếu muốn format đẹp: df.format(giaphong) + " đ")

        txtGiaPhong.setText(df.format(giaphong));
        txtTenPhong.setText(String.valueOf(getIntent().getStringExtra("tenphong")));

        // Lấy giá điện nước từ database (nếu có)
        layGiaMacDinhTuDatabase();

        // Khởi tạo danh sách dịch vụ cho recycler chính (Tiền điện, tiền nước)
        listDichVu = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDichVu.add(new DichVuCon("Tiền điện", giaDien));
        listDichVu.add(new DichVuCon("Tiền nước", giaNuoc));
        adapter = new TotalPriceAdapter(listDichVu);
        recyclerView.setAdapter(adapter);

        // --- SETUP "Tiền dịch vụ khác" (RecyclerView con, adapter, DAO) ---
        dichVuConDAO = new qlthutien_DichVuConDAO(this);
        listOtherServices = dichVuConDAO.getAll(); // lấy tất cả dich vu con từ DB

        // tính tổng các dịch vụ con
        calculateTotalOtherServices();

        otherAdapter = new DichVuConAdapter(listOtherServices);
        recyclerOtherService.setLayoutManager(new LinearLayoutManager(this));
        recyclerOtherService.setAdapter(otherAdapter);
        recyclerOtherService.setVisibility(View.GONE); // mặc định ẩn

        // hiển thị tổng dịch vụ khác (đã format)
        txtOtherServiceTotal.setText(df.format(totalOtherServices));//+đ

        // hành vi mở/đóng khi bấm mũi tên hoặc bấm vào tổng tiền
        View.OnClickListener toggleOther = v -> toggleOtherServices();
        imgExpandOther.setOnClickListener(toggleOther);
        txtOtherServiceTotal.setOnClickListener(toggleOther);
        // ---------------------------------------------------------------

        // TextWatcher để tính toán tự động khi thay đổi chỉ số
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { tinhToan(); }
            @Override public void afterTextChanged(Editable s) {}
        };

        edtOldElectric.addTextChangedListener(watcher);
        edtNewElectric.addTextChangedListener(watcher);
        edtOldWater.addTextChangedListener(watcher);
        edtNewWater.addTextChangedListener(watcher);

        imgBack.setOnClickListener(v -> {
            finish();
        });

        // click chọn ngày
        chooseDate();
        // tính lần đầu (gồm cả dịch vụ khác)
        tinhToan();
    }

    private void toggleOtherServices() {
        if (isOtherExpanded) {
            recyclerOtherService.setVisibility(View.GONE);
            imgExpandOther.animate().rotation(0).setDuration(200).start();
        } else {
            recyclerOtherService.setVisibility(View.VISIBLE);
            imgExpandOther.animate().rotation(180).setDuration(200).start(); // xoay chỉ minh hoạ
        }
        isOtherExpanded = !isOtherExpanded;
    }

    //nếu có thì dùng, không có thì dùng mặc định
    private void layGiaMacDinhTuDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT giadien, gianuoc FROM GiaMacDinh WHERE id = 1", null);

        if (cursor.moveToFirst()) {
            giaDien = cursor.getDouble(0);
            giaNuoc = cursor.getDouble(1);
        } else {
            giaDien = GIA_DIEN_MACDINH;
            giaNuoc = GIA_NUOC_MACDINH;
        }

        cursor.close();
        db.close();
    }

    private void calculateTotalOtherServices() {
        totalOtherServices = 0.0;
        if (listOtherServices != null) {
            for (DichVuCon dv : listOtherServices) {
                totalOtherServices += dv.getGiatien();
            }
        }
    }

    private void tinhToan() {
        double oldE = parseDouble(edtOldElectric.getText().toString());
        double newE = parseDouble(edtNewElectric.getText().toString());
        double oldW = parseDouble(edtOldWater.getText().toString());
        double newW = parseDouble(edtNewWater.getText().toString());
        double giaphong = parseDouble(txtGiaPhong.getText().toString());

        double usedE = Math.max(0, newE - oldE);
        double usedW = Math.max(0, newW - oldW);

        double totalE = usedE * giaDien;
        double totalW = usedW * giaNuoc;

        // Cập nhật lại list hiển thị (dùng TotalPriceAdapter.setValues như hiện tại)
        listDichVu.clear();
        listDichVu.add(new DichVuCon("Tiền điện", giaDien));
        listDichVu.add(new DichVuCon("Tiền nước", giaNuoc));
        // nếu adapter có phương thức setValues (như bạn đã dùng), ta giữ nguyên
        adapter.setValues(usedE, totalE, usedW, totalW);

        // cập nhật lại tổng: cộng cả dịch vụ con
        // nếu danh sách dịch vụ con có thay đổi động thì cần gọi calculateTotalOtherServices() lại trước khi tính
        calculateTotalOtherServices();
        txtOtherServiceTotal.setText(df.format(totalOtherServices) + " đ");

        double tong = totalE + totalW + giaphong + totalOtherServices;
        txtTongTien.setText("Tổng tiền: " + df.format(tong) + " đ");
    }

    private double parseDouble(String s) {
        try {
            if (s == null || s.isEmpty()) return 0;
            // nếu trước đó bạn set giá hiển thị với dấu phẩy (df.format), loại bỏ phẩy
            String cleaned = s.replaceAll(",", "").replaceAll(" đ", "").trim();
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void nhandulieuIntent(){ }

    private void chooseDate(){
        edtdate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    TaoHoaDonActivity.this,
                    (view, year1, month1, dayOfMonth) -> edtdate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    y, m, d
            );
            dialog.show();
        });
    }
    private void anhxaid(){
        // Ánh xạ view
        edtOldElectric = findViewById(R.id.edt_oldElectric_totalPrice);
        edtNewElectric = findViewById(R.id.edt_newElectric_totalPrice);
        edtOldWater = findViewById(R.id.edt_oldWater_totalPrice);
        edtNewWater = findViewById(R.id.edt_newWater_totalPrice);

        txtTongTien = findViewById(R.id.txt_sumPrice_totalPrice);
        edtdate = findViewById(R.id.edt_date_totalPrice);
        recyclerView = findViewById(R.id.recycler_totalPrice);
        txtGiaPhong = findViewById(R.id.txt_tienPhong_totalPrice);
        txtTenPhong = findViewById(R.id.tvTenPhong);
        btnTaoHoaDon = findViewById(R.id.btn_createBill_totalPrice);
        imgBack = findViewById(R.id.img_arrowback_totalPriceroom);
        // ánh xạ cho phần dịch vụ khác (row riêng của bạn)
        txtOtherServiceTotal = findViewById(R.id.txt_otherService_totalPrice);
        imgExpandOther = findViewById(R.id.img_expand_OtherService_totalPrice);
        recyclerOtherService = findViewById(R.id.recycler_OtherService_totalPrice);
    }
}
