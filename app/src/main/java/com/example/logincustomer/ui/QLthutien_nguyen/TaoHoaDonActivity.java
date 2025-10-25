package com.example.logincustomer.ui.QLthutien_nguyen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlthutien_TotalPriceAdapter;
import com.example.logincustomer.data.Adapter.qlthutien_DichVuConAdapter;
import com.example.logincustomer.data.DAO.qlthutien_ChiTietHoaDonDAO;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DAO.qlthutien_GiaMacDinhDienNuocDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.qlthutien_ChiTietHoaDon;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaoHoaDonActivity extends AppCompatActivity {

    private EditText edtOldElectric, edtNewElectric, edtOldWater, edtNewWater, edtdate, edtnote;
    private TextView txtTongTien, txtGiaPhong, txtTenPhong;

    // views cho "Tiền dịch vụ khác"
    private TextView txtOtherServiceTotal;
    private ImageView imgExpandOther, imgBack, currentImageView;
    private RecyclerView recyclerOtherService;
    private boolean isOtherExpanded = false;
    private Button btnTaoHoaDon;
    private RecyclerView recyclerView;
    private qlthutien_TotalPriceAdapter adapter;
    private ArrayList<qlthutien_DichVuCon> listDichVu;

    // danh sách dịch vụ con (lấy từ DB)
    private qlthutien_DichVuConDAO dichVuConDAO;
    private ArrayList<qlthutien_DichVuCon> listOtherServices;
    private qlthutien_DichVuConAdapter otherAdapter;
    private double totalOtherServices = 0.0;
    private qlthutien_HoaDonDAO hoaDonDAO;
    private double giaDien;
    private double giaNuoc;
    private double tong = 0.0;

    private ImageView imgDienCu, imgDienMoi, iconDienCu, iconDienMoi;
    private ImageView imgNuocCu, imgNuocMoi, iconNuocCu, iconNuocMoi;
    private String pathDienCu, pathDienMoi, pathNuocCu, pathNuocMoi;

    // Dùng ActivityResultLauncher cho an toàn (API mới)
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Uri imageUri;
    private static final double GIA_DIEN_MACDINH = 3500;
    private static final double GIA_NUOC_MACDINH = 20000;
    private final DecimalFormat df = new DecimalFormat("#,###");
    private qlthutien_ChiTietHoaDonDAO qlthutienChiTietHoaDonDAO;
    private qlthutien_GiaMacDinhDienNuocDAO DefaultValueWE, checkdiennuoc;
    private qlthutien_ChiTietHoaDon chiTietDien, chiTietNuoc, chitiethoadon;
    private qlthutien_HoaDon hoaDonintent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_total_priceroom);
        anhxaid();

        hoaDonDAO = new qlthutien_HoaDonDAO(TaoHoaDonActivity.this);

        int idphong = getIntent().getIntExtra("idPhong", 0);
        String tenphong = getIntent().getStringExtra("tenphong");
        double giaphong = getIntent().getDoubleExtra("giaphong", 0);

        // giữ nguyên kiểu hiển thị hiện tại (nếu muốn format đẹp: df.format(giaphong) + " đ")
        txtGiaPhong.setText(df.format(giaphong));
        txtTenPhong.setText(String.valueOf(tenphong));

        //show thông tin điện nươớc
        checkdiennuoc = new qlthutien_GiaMacDinhDienNuocDAO(TaoHoaDonActivity.this);
        if(!checkdiennuoc.hasGiaMacDinh()){
            showNoGiaMacDinhDialog();
        }
        // Lấy giá điện nước từ database (nếu có)
        layGiaMacDinhTuDatabase();

        // Khởi tạo danh sách dịch vụ cho recycler chính (Tiền điện, tiền nước)
        listDichVu = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDichVu.add(new qlthutien_DichVuCon("Tiền điện", giaDien));
        listDichVu.add(new qlthutien_DichVuCon("Tiền nước", giaNuoc));
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
        chooseDate(idphong);

        // tính lần đầu (gồm cả dịch vụ khác)
        tinhToan();
        picture();

        btnTaoHoaDon.setOnClickListener(v -> {
            // 1️⃣ Lấy dữ liệu từ giao diện
            String ngayTao = edtdate.getText().toString().trim();
            String ghiChu = edtnote.getText().toString().trim();
            String txsodiencu = edtOldElectric.getText().toString().trim();
            String txsodienmoi = edtNewElectric.getText().toString().trim();
            String txsonuoccu = edtOldWater.getText().toString().trim();
            String txsonuocmoi = edtNewWater.getText().toString().trim();

            // cho vào if dưới
            if (ngayTao.isEmpty() || txsonuoccu.isEmpty() || txsonuocmoi.isEmpty() ||
                    txsodiencu.isEmpty() || txsodienmoi.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int oldE = Integer.parseInt(txsodiencu);
            int newE = Integer.parseInt(txsodienmoi);
            int oldW = Integer.parseInt(txsonuoccu);
            int newW = Integer.parseInt(txsonuocmoi);

            if (newE < oldE) {
                Toast.makeText(this, "Số điện mới phải lớn hơn số cũ!", Toast.LENGTH_SHORT).show();
                return;
            } else if (newW < oldW) {
                Toast.makeText(this, "Số nước mới phải lớn hơn số cũ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2️⃣ Lấy giá điện nước từ bảng GiaMacDinh
            DefaultValueWE = new qlthutien_GiaMacDinhDienNuocDAO(TaoHoaDonActivity.this);
            double giaDien = DefaultValueWE.getGiaDien(); // bạn cần tạo hàm này trong DAO
            double giaNuoc = DefaultValueWE.getGiaNuoc();

            // 3️⃣ Tính tiền điện nước
            int soDienSuDung = newE - oldE;
            int soNuocSuDung = newW - oldW;

            double tienDien = soDienSuDung * giaDien;
            double tienNuoc = soNuocSuDung * giaNuoc;

            // 4️⃣ Tính tổng tiền
            double tongTien = tienDien + tienNuoc + giaphong + totalOtherServices; // tùy bạn có thể tính thêm

            // 5️⃣ Tạo hóa đơn chính
            qlthutien_HoaDon hd = new qlthutien_HoaDon();
            hd.setIdphong(idphong);
            hd.setNgaytaohdon(ngayTao);

            hd.setTrangthai(false);
            hd.setGhichu(ghiChu);
            hd.setTongtien(tongTien);
            hd.setImgDienCu(pathDienCu);
            hd.setImgDienMoi(pathDienMoi);
            hd.setImgNuocCu(pathNuocCu);
            hd.setImgNuocMoi(pathNuocMoi);

            // 6️⃣ Insert vào bảng qlthutien_HoaDon

            long result = hoaDonDAO.insertHoaDon(hd);

            if (result == -1) {
                Toast.makeText(this, "Lỗi khi thêm hóa đơn!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 7️⃣ Tạo chi tiết hóa đơn cho điện
            chiTietDien = new qlthutien_ChiTietHoaDon();
            chiTietDien.setIdhoadon((int) result);
            chiTietDien.setTendichvu("Điện");
            chiTietDien.setSodiencu(oldE);
            chiTietDien.setSodienmoi(newE);
            chiTietDien.setSosudung(soDienSuDung);
            chiTietDien.setThanhtien((int) tienDien);

            // 8️⃣ Tạo chi tiết hóa đơn cho nước
            chiTietNuoc = new qlthutien_ChiTietHoaDon();
            chiTietNuoc.setIdhoadon((int) result);
            chiTietNuoc.setTendichvu("Nước");
            chiTietNuoc.setSonuoccu(oldW);
            chiTietNuoc.setSonuocmoi(newW);
            chiTietNuoc.setSosudung(soNuocSuDung);
            chiTietNuoc.setThanhtien((int) tienNuoc);

            // 9️⃣ Insert chi tiết hóa đơn
            qlthutienChiTietHoaDonDAO = new qlthutien_ChiTietHoaDonDAO(TaoHoaDonActivity.this);
            qlthutienChiTietHoaDonDAO.insertChiTiet(chiTietDien);
            qlthutienChiTietHoaDonDAO.insertChiTiet(chiTietNuoc);

            if (result != -1) {
                Toast.makeText(this, "Thêm hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lỗi khi thêm hóa đơn!", Toast.LENGTH_SHORT).show();
            }

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
                    intent.putExtra("idhoadon", (int) result);
                    startActivity(intent);
                }
            });
            builder.show();
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
    }

    private void calculateTotalOtherServices() {
        totalOtherServices = 0.0;
        if (listOtherServices != null) {
            for (qlthutien_DichVuCon dv : listOtherServices) {
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
        listDichVu.add(new qlthutien_DichVuCon("Tiền điện", giaDien));
        listDichVu.add(new qlthutien_DichVuCon("Tiền nước", giaNuoc));
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

    private void chooseDate(int idphong) {
        edtdate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    TaoHoaDonActivity.this,
                    (view, year, month, dayOfMonth) -> {

                        // Định dạng lại chuỗi thành "YYYY-MM" để khớp với câu lệnh SQL
                        // String.format("%02d", ...) sẽ đảm bảo tháng luôn có 2 chữ số (ví dụ: 09, 10, 11)
                        String thangNamKiemTra = String.format("%d-%02d", year, month + 1);

                        // Gọi hàm kiểm tra từ DAO với định dạng đã chuẩn
                        boolean daTonTai = hoaDonDAO.kiemTraHoaDonDaTonTaiWithThangNam(idphong, thangNamKiemTra);

                        if (daTonTai) {
                            // Hiển thị tháng/năm cho người dùng dễ hiểu
                            Toast.makeText(TaoHoaDonActivity.this, "Hóa đơn cho tháng " + (month + 1) + "/" + year + " đã tồn tại!", Toast.LENGTH_LONG).show();
                            edtdate.setText(""); // Reset về trạng thái ban đầu
                        } else {
                            // Nếu false: Set ngày đầy đủ vào EditText như cũ
                            edtdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    },
                    y, m, d
            );
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();
        });
    }
    private void showNoGiaMacDinhDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Chưa thiết lập giá điện và nước mặc định.\nMặc định sẽ dùng giá điện và nước lần lượt là 3.500 và 20.000\nMuốn thay đổi vui lòng vào phần 'Thiết lập mặc định'.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss(); // Đóng thông báo khi nhấn OK
                })
                .setCancelable(false) // Không cho nhấn ra ngoài để tắt
                .show();
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
                    if (result.getResultCode() == RESULT_OK && currentImageView != null) {
                        currentImageView.setImageURI(imageUri);

                        // 🔥 Lưu đường dẫn (để insert DB)
                        String path = imageUri.toString();

                        if (currentImageView == imgDienCu) pathDienCu = path;
                        else if (currentImageView == imgDienMoi) pathDienMoi = path;
                        else if (currentImageView == imgNuocCu) pathNuocCu = path;
                        else if (currentImageView == imgNuocMoi) pathNuocMoi = path;
                        Log.d("IMAGE_PATH", "Camera imageUri = " + imageUri);
                    } else {
                        Toast.makeText(this, "Hủy chụp ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && currentImageView != null) {
                        Uri uri = result.getData().getData();

                        // 👉 Giữ quyền truy cập lâu dài cho URI được chọn
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                        try {
                            getContentResolver().takePersistableUriPermission(uri, takeFlags);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }

                        currentImageView.setImageURI(uri);

                        String path = uri.toString();
                        if (currentImageView == imgDienCu) pathDienCu = path;
                        else if (currentImageView == imgDienMoi) pathDienMoi = path;
                        else if (currentImageView == imgNuocCu) pathNuocCu = path;
                        else if (currentImageView == imgNuocMoi) pathNuocMoi = path;

                        Log.d("IMAGE_PATH", "Gallery uri = " + uri);
                    } else {
                        Toast.makeText(this, "Hủy chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

        eventButtonCamera();
    }

    private void eventButtonCamera() {
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    System.currentTimeMillis() + "_photo.jpg");

            imageUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".provider",
                    photoFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(intent);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
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

