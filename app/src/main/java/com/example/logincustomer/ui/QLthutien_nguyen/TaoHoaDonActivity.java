package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlthutien_TotalPriceAdapter;
import com.example.logincustomer.data.Adapter.qlthutien_DichVuConAdapter;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.DichVuCon;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaoHoaDonActivity extends AppCompatActivity {

    private EditText edtOldElectric, edtNewElectric, edtOldWater, edtNewWater, edtdate, edtnote;
    private TextView txtTongTien, txtGiaPhong, txtTenPhong;

    // views cho "Tiền dịch vụ khác"
    private TextView txtOtherServiceTotal;
    private ImageView imgExpandOther, imgBack,currentImageView;
    private RecyclerView recyclerOtherService;
    private boolean isOtherExpanded = false;
    private Button btnTaoHoaDon;
    private RecyclerView recyclerView;
    private qlthutien_TotalPriceAdapter adapter;
    private ArrayList<DichVuCon> listDichVu;

    // danh sách dịch vụ con (lấy từ DB)
    private qlthutien_DichVuConDAO dichVuConDAO;
    private ArrayList<DichVuCon> listOtherServices;
    private qlthutien_DichVuConAdapter otherAdapter;
    private double totalOtherServices = 0.0;

    private double giaDien;
    private double giaNuoc;

    private ImageView imgDienCu, imgDienMoi, iconDienCu, iconDienMoi;
    private ImageView imgNuocCu, imgNuocMoi, iconNuocCu, iconNuocMoi;

    // Dùng ActivityResultLauncher cho an toàn (API mới)
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
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
        adapter = new qlthutien_TotalPriceAdapter(listDichVu);
        recyclerView.setAdapter(adapter);

        // --- SETUP "Tiền dịch vụ khác" (RecyclerView con, adapter, DAO) ---
        dichVuConDAO = new qlthutien_DichVuConDAO(this);
        listOtherServices = dichVuConDAO.getAll(); // lấy tất cả dich vu con từ DB
        // tính tổng các dịch vụ con
        calculateTotalOtherServices();
        otherAdapter = new qlthutien_DichVuConAdapter(listOtherServices);
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
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tinhToan();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
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
        picture();

        btnTaoHoaDon.setOnClickListener(v -> {

            showDialog();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


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

        // Cập nhật lại list hiển thị (dùng qlthutien_TotalPriceAdapter.setValues như hiện tại)
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
            String cleaned = s
                    .replaceAll("[,. đ]", "") // loại dấu phẩy, chấm, khoảng trắng mảnh, ký tự đ
                    .trim();
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void nhandulieuIntent() {
    }

    private void chooseDate() {
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tạo hóa đơn thành công");
        builder.setMessage("Xem hóa đơn?");

        builder.setNegativeButton("Quay về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng activity hiện tại
                finish();
            }
        });

        builder.setPositiveButton("Xem hóa đơn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Chuyển sang activity khác
                Intent intent = new Intent(TaoHoaDonActivity.this, BillRoomActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }
    private void anhxaid() {
        // Ánh xạ view
        imgBack = findViewById(R.id.img_arrowback_totalPriceroom);
        txtTenPhong = findViewById(R.id.tvTenPhong);
        edtdate = findViewById(R.id.edt_date_totalPrice);

        edtOldElectric = findViewById(R.id.edt_oldElectric_totalPrice);
        edtNewElectric = findViewById(R.id.edt_newElectric_totalPrice);
        edtOldWater = findViewById(R.id.edt_oldWater_totalPrice);
        edtNewWater = findViewById(R.id.edt_newWater_totalPrice);

        edtnote = findViewById(R.id.edt_note_totalPrice);
        recyclerView = findViewById(R.id.recycler_totalPrice);
        txtGiaPhong = findViewById(R.id.txt_tienPhong_totalPrice);
        txtTongTien = findViewById(R.id.txt_sumPrice_totalPrice);
        btnTaoHoaDon = findViewById(R.id.btn_createBill_totalPrice);

        iconDienCu = findViewById(R.id.imga_oldElectric_totalPrice);
        imgDienCu = findViewById(R.id.imgb_oldElectric_totalPrice);
        iconDienMoi = findViewById(R.id.imga_newElectric_totalPrice);
        imgDienMoi = findViewById(R.id.imgb_newElectric_totalPrice);
        iconNuocCu = findViewById(R.id.imga_oldWater_totalPrice);
        imgNuocCu = findViewById(R.id.imgb_oldWater_totalPrice);
        iconNuocMoi = findViewById(R.id.imga_newWater_totalPrice);
        imgNuocMoi = findViewById(R.id.imgb_newWater_totalPrice);

        // ánh xạ cho phần dịch vụ khác (row riêng của bạn)
        txtOtherServiceTotal = findViewById(R.id.txt_otherService_totalPrice);
        imgExpandOther = findViewById(R.id.img_expand_OtherService_totalPrice);
        recyclerOtherService = findViewById(R.id.recycler_OtherService_totalPrice);
    }
    private void picture() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && currentImageView != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        currentImageView.setImageBitmap(photo);
                    } else {
                        Toast.makeText(this, "Hủy chụp ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && currentImageView != null) {
                        Uri uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            currentImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "Hủy chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

        eventButtonCamera();
    }
    private void eventButtonCamera(){
        // --- Khi nhấn vào hình điện: mở camera ---
        iconDienCu.setOnClickListener(v -> {
            currentImageView = imgDienCu;
            openCamera();
        });

        iconDienMoi.setOnClickListener(v -> {
            currentImageView = imgDienMoi;
            openCamera();
        });

        iconNuocCu.setOnClickListener(v -> {
            currentImageView = imgNuocCu;
            openCamera();
        });

        iconNuocMoi.setOnClickListener(v -> {
            currentImageView = imgNuocMoi;
            openCamera();
        });

        // --- Khi nhấn vào hình nước: mở thư viện ---
        imgDienCu.setOnClickListener(v -> {
            currentImageView = imgDienCu;
            openGallery();
        });

        imgDienMoi.setOnClickListener(v -> {
            currentImageView = imgDienMoi;
            openGallery();
        });

        imgNuocCu.setOnClickListener(v -> {
            currentImageView = imgNuocCu;
            openGallery();
        });

        imgNuocMoi.setOnClickListener(v -> {
            currentImageView = imgNuocMoi;
            openGallery();
        });
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Người dùng đã cho phép
                openCamera();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền camera để chụp ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
