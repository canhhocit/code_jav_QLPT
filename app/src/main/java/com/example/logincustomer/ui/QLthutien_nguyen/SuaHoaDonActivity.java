package com.example.logincustomer.ui.QLthutien_nguyen;

import android.Manifest;
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
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DAO.qlthutien_GiaMacDinhDienNuocDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.qlphongtro_PhongTro;
import com.example.logincustomer.data.Model.qlthutien_ChiTietHoaDon;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SuaHoaDonActivity extends AppCompatActivity {

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
    private static final double GIA_NUOC_MACDINH = 15000;
    private final DecimalFormat df = new DecimalFormat("#,###");
    private qlthutien_ChiTietHoaDonDAO qlthutienChiTietHoaDonDAO;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private qlthutien_GiaMacDinhDienNuocDAO DefaultValueWE;
    private qlthutien_ChiTietHoaDon chiTietDien, chiTietNuoc,
    chitiethoadondien, chitiethoadonnuoc;
    private qlthutien_HoaDon hoaDonintent;
    private qlphongtro_PhongTro phongtro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlthutien_layout_updatebill);
        anhxaid();

        int idhoadon = getIntent().getIntExtra("idhoadon", -1);

        hoaDonDAO = new qlthutien_HoaDonDAO(this);
        qlthutienChiTietHoaDonDAO = new qlthutien_ChiTietHoaDonDAO(this);
        dichVuConDAO = new qlthutien_DichVuConDAO(this);
        DefaultValueWE = new qlthutien_GiaMacDinhDienNuocDAO(this);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);

        chitiethoadondien = qlthutienChiTietHoaDonDAO.getChiTietHoaDonByIdHoaDonDien(idhoadon);
        chitiethoadonnuoc = qlthutienChiTietHoaDonDAO.getChiTietHoaDonByIdHoaDonNuoc(idhoadon);


        int idphong = hoaDonDAO.getIdPhongByIdHoaDon(idhoadon);
        phongtro = phongTroDAO.getPhongById(idphong);

        // ✅ Nhận idhoadon từ intent
        if (idhoadon == -1) {
            Toast.makeText(this, "Không tìm thấy ID hóa đơn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ Lấy hóa đơn từ DB
        qlthutien_HoaDon hoaDon = hoaDonDAO.getHoaDonById(idhoadon);
        if (hoaDon == null) {
            Toast.makeText(this, "Không tìm thấy hóa đơn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ Điền dữ liệu cũ vào form
        txtTenPhong.setText(phongtro.getTenphong());
        txtGiaPhong.setText(df.format(phongtro.getGia()));
        edtdate.setText(hoaDon.getNgaytaohdon());
        edtnote.setText(hoaDon.getGhichu());

        edtOldElectric.setText(String.valueOf(chitiethoadondien.getSodiencu()));
        edtNewElectric.setText(String.valueOf(chitiethoadondien.getSodienmoi()));
        edtOldWater.setText(String.valueOf(chitiethoadonnuoc.getSonuoccu()));
        edtNewWater.setText(String.valueOf(chitiethoadonnuoc.getSonuocmoi()));

        // ✅ Ảnh cũ (nếu có)
        if (hoaDon.getImgDienCu() != null) imgDienCu.setImageURI(Uri.parse(hoaDon.getImgDienCu()));
        if (hoaDon.getImgDienMoi() != null) imgDienMoi.setImageURI(Uri.parse(hoaDon.getImgDienMoi()));
        if (hoaDon.getImgNuocCu() != null) imgNuocCu.setImageURI(Uri.parse(hoaDon.getImgNuocCu()));
        if (hoaDon.getImgNuocMoi() != null) imgNuocMoi.setImageURI(Uri.parse(hoaDon.getImgNuocMoi()));

        // ✅ Lưu lại các path ảnh cũ
        pathDienCu = hoaDon.getImgDienCu();
        pathDienMoi = hoaDon.getImgDienMoi();
        pathNuocCu = hoaDon.getImgNuocCu();
        pathNuocMoi = hoaDon.getImgNuocMoi();

        // ✅ Gán lại DAO và các phần tính toán
        layGiaMacDinhTuDatabase();
        listDichVu = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDichVu.add(new qlthutien_DichVuCon("Tiền điện", giaDien));
        listDichVu.add(new qlthutien_DichVuCon("Tiền nước", giaNuoc));
        adapter = new qlthutien_TotalPriceAdapter(listDichVu);
        recyclerView.setAdapter(adapter);

        listOtherServices = dichVuConDAO.getAll();
        calculateTotalOtherServices();
        otherAdapter = new qlthutien_DichVuConAdapter(listOtherServices);
        recyclerOtherService.setLayoutManager(new LinearLayoutManager(this));
        recyclerOtherService.setAdapter(otherAdapter);
        txtOtherServiceTotal.setText(df.format(totalOtherServices));

        // --- SETUP "Tiền dịch vụ khác" (RecyclerView con, adapter, DAO) ---
        dichVuConDAO = new qlthutien_DichVuConDAO(this);
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


        // tính tổng lại
        tinhToan();
        picture();
        chooseDate();

        btnTaoHoaDon.setOnClickListener(v -> {
            capNhatHoaDon(idhoadon, idphong);
            finish();
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

    private void chooseDate() {
        edtdate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    SuaHoaDonActivity.this,
                    (view, year1, month1, dayOfMonth) -> edtdate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    y, m, d
            );
            dialog.show();
        });
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
    private void capNhatHoaDon(int idhoadon, int idphong) {
        String ngayTao = edtdate.getText().toString().trim();
        String ghiChu = edtnote.getText().toString().trim();

        String txsodiencu = edtOldElectric.getText().toString().trim();
        String txsodienmoi = edtNewElectric.getText().toString().trim();
        String txsonuoccu = edtOldWater.getText().toString().trim();
        String txsonuocmoi = edtNewWater.getText().toString().trim();

        if (txsonuoccu.isEmpty() || txsonuocmoi.isEmpty() ||
                txsodiencu.isEmpty() || txsodienmoi.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int oldE = Integer.parseInt(txsodiencu);
        int newE = Integer.parseInt(txsodienmoi);
        int oldW = Integer.parseInt(txsonuoccu);
        int newW = Integer.parseInt(txsonuocmoi);

        if (newE < oldE || newW < oldW) {
            Toast.makeText(this, "Chỉ số mới phải lớn hơn chỉ số cũ!", Toast.LENGTH_SHORT).show();
            return;
        }

        double giaDien = DefaultValueWE.getGiaDien();
        double giaNuoc = DefaultValueWE.getGiaNuoc();
        double giaphong = parseDouble(txtGiaPhong.getText().toString());

        int soDienSuDung = newE - oldE;
        int soNuocSuDung = newW - oldW;

        double tienDien = soDienSuDung * giaDien;
        double tienNuoc = soNuocSuDung * giaNuoc;
        double tongTien = tienDien + tienNuoc + giaphong + totalOtherServices;

        // ✅ Gán dữ liệu mới cho hóa đơn
        qlthutien_HoaDon hd = new qlthutien_HoaDon();
        hd.setIdphong(idphong);
        hd.setIdhoadon(idhoadon);
        hd.setNgaytaohdon(ngayTao);
        hd.setGhichu(ghiChu);
        hd.setTongtien(tongTien);
        hd.setImgDienCu(pathDienCu);
        hd.setImgDienMoi(pathDienMoi);
        hd.setImgNuocCu(pathNuocCu);
        hd.setImgNuocMoi(pathNuocMoi);

        int result = hoaDonDAO.updateHoaDon(hd);
        if (result == -1) {
            Toast.makeText(this, "Lỗi khi sửa hóa đơn!", Toast.LENGTH_SHORT).show();
            return;
        }
        // 7️⃣ Tạo chi tiết hóa đơn cho điện
        chiTietDien = new qlthutien_ChiTietHoaDon();
        chiTietDien.setIdhoadon(idhoadon);
        chiTietDien.setTendichvu("Điện");
        chiTietDien.setSodiencu(oldE);
        chiTietDien.setSodienmoi(newE);
        chiTietDien.setSosudung(soDienSuDung);
        chiTietDien.setThanhtien((int) tienDien);

        // 8️⃣ Tạo chi tiết hóa đơn cho nước
        chiTietNuoc = new qlthutien_ChiTietHoaDon();
        chiTietNuoc.setIdhoadon(idhoadon);
        chiTietNuoc.setTendichvu("Nước");
        chiTietNuoc.setSonuoccu(oldW);
        chiTietNuoc.setSonuocmoi(newW);
        chiTietNuoc.setSosudung(soNuocSuDung);
        chiTietNuoc.setThanhtien((int) tienNuoc);

        // 9️⃣ Insert chi tiết hóa đơn
        qlthutienChiTietHoaDonDAO = new qlthutien_ChiTietHoaDonDAO(SuaHoaDonActivity.this);
        qlthutienChiTietHoaDonDAO.updateSoDienNuoc(chiTietDien);
        qlthutienChiTietHoaDonDAO.updateSoDienNuoc(chiTietNuoc);

        if (result > 0) {
            Toast.makeText(this, "Cập nhật hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}

