package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logincustomer.data.Model.HoaDon;
import com.example.logincustomer.data.Model.PhongTro;

import java.util.ArrayList;
import java.util.Calendar;

public class s {
    // -------------------oc--------------------------------------------

//// 🔹 Lấy danh sách phòng từ DB
//    listPhong = phongTroDAO.getAllPhongTro();
//    listTenPhong = new ArrayList<>();
//        listTenPhong.add("Tất cả"); // mục đầu tiên
//
//        for (
//    PhongTro p : listPhong) {
//        listTenPhong.add(p.getTenphong());
//    }
//
//    // 🔹 Tạo adapter cho Spinner
//    ArrayAdapter<String> adapterPhong = new ArrayAdapter<>(
//            this,
//            android.R.layout.simple_spinner_item,
//            listTenPhong
//    );
//        adapterPhong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerPhong.setAdapter(adapterPhong);
//
//    // 🔹 Xử lý khi chọn phòng
//        spinnerPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            String selected = listTenPhong.get(position);
//            if (selected.equals("Tất cả")) {
//                Log.d("FILTER", "Hiển thị tất cả phòng");
//            } else {
//                PhongTro selectedPhong = listPhong.get(position - 1); // vì "Tất cả" ở vị trí 0
//                int idPhong = selectedPhong.getIdphong();
//                Log.d("FILTER", "Lọc theo phòng ID: " + idPhong + ", tên: " + selected);
//            }
//        }
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {}
//    });
    // ---------------------oc------------------------------------------
    // 🔹 Gán sự kiện chọn ngày
//        edtFromDate.setOnClickListener(v -> showDatePickerDialog(edtFromDate));
//        edtToDate.setOnClickListener(v -> showDatePickerDialog(edtToDate));
// -----------------------fu----------------------------------------
// 🔹 Hàm hiển thị DatePicker
//private void showDatePickerDialog(EditText target) {
//    Calendar calendar = Calendar.getInstance();
//    DatePickerDialog dialog = new DatePickerDialog(
//            this,
//            (view, year, month, dayOfMonth) -> {
//                String selected = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
//                target.setText(selected);
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//    );
//    dialog.show();
//}
    // ---------------------------------------------------------------

//                <ImageView
//    android:id="@+id/btn_Delete_billRoom"
//    android:layout_width="28dp"
//    android:layout_height="28dp"
//    android:src="@drawable/img_trash"
//    app:tint="#FF0000" />
    // ---------------------------------------------------------------
}



//-----------------luuq ya
//trong giamacdinhdiennuocDAO để ý hàm getgiadien & getgianuoc
//check số người = 0 thì k tạo hóa đơn

//cần đổi lại cách check, khi trùng tháng ở quản lý phòng trọ khi thêm hóa đơn

// ---------------------------------------------------------------
//            int checkstatus = hoaDonDAO.kiemTraTinhTrangHoaDon(idphong);
//            if (checkstatus == 1) {
//                Toast.makeText(this, "Phòng này có hóa đơn chưa thanh toán, không thể tạo thêm!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (checkstatus == 2) {
//                Toast.makeText(this, "Phòng này đã có hóa đơn tháng này, không thể tạo thêm!", Toast.LENGTH_SHORT).show();
//                return;
//            }
// -------------------------fu----1----------------------------------
//private void checkintentforidphong(int idphong) {
//    hoaDonintent = hoaDonDAO.getNewestHoaDonByPhong(idphong);
//    chitiethoadon = new ChiTietHoaDon();
//    txtTenPhong.setText("Sửa hóa đơn");
//    btnTaoHoaDon.setText("Xác nhận");
//    edtdate.setText(hoaDonintent.getNgaytaohdon());
//
//    edtOldElectric.setText(chitiethoadon.getSodiencu());
//    edtNewElectric.setText(chitiethoadon.getSodienmoi());
//    edtOldWater.setText(chitiethoadon.getSonuoccu());
//    edtNewWater.setText(chitiethoadon.getSonuocmoi());
//}
//private void checkintentforidhoadon(int idhoadon){
//    hoaDonintent = hoaDonDAO.getNewestHoaDonByPhong(idphong);
//    chitiethoadon = new ChiTietHoaDon();
//    txtTenPhong.setText("Sửa hóa đơn");
//    btnTaoHoaDon.setText("Xác nhận");
//    edtdate.setText(hoaDonintent.getNgaytaohdon());
//
//    edtOldElectric.setText(chitiethoadon.getSodiencu());
//    edtNewElectric.setText(chitiethoadon.getSodienmoi());
//    edtOldWater.setText(chitiethoadon.getSonuoccu());
//    edtNewWater.setText(chitiethoadon.getSonuocmoi());
//}
// ----------------------oc-----------1------------------------------
//        if (check == 1) {
//checkintentforidphong(idphong);
//        }else if(check == 2){
//checkintentforidhoadon(idhoadon);
//        }
// -------------------------check with idphong--------------------------------------
//if (check == 1) {
//        btnTaoHoaDon.setOnClickListener(v -> {
//// 1️⃣ Lấy dữ liệu từ giao diện
//String ngayTao = edtdate.getText().toString().trim();
//String ghiChu = edtnote.getText().toString().trim();
//
//String txsodiencu = edtOldElectric.getText().toString().trim();
//String txsodienmoi = edtNewElectric.getText().toString().trim();
//String txsonuoccu = edtOldWater.getText().toString().trim();
//String txsonuocmoi = edtNewWater.getText().toString().trim();
//
////ngayTao.isEmpty() || cho vào if dưới
//                if (txsonuoccu.isEmpty() || txsonuocmoi.isEmpty() ||
//        txsodiencu.isEmpty() || txsodienmoi.isEmpty()) {
//        Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
//                    return;
//                            }
//
//int oldE = Integer.parseInt(txsodiencu);
//int newE = Integer.parseInt(txsodienmoi);
//int oldW = Integer.parseInt(txsonuoccu);
//int newW = Integer.parseInt(txsonuocmoi);
//
//                if (newE < oldE) {
//        Toast.makeText(this, "Số điện mới phải lớn hơn số cũ!", Toast.LENGTH_SHORT).show();
//                    return;
//                            } else if (newW < oldW) {
//        Toast.makeText(this, "Số nước mới phải lớn hơn số cũ!", Toast.LENGTH_SHORT).show();
//                    return;
//                            }
//
//// 2️⃣ Lấy giá điện nước từ bảng GiaMacDinh
//DefaultValueWE = new qlthutien_GiaMacDinhDienNuocDAO(TaoHoaDonActivity.this);
//double giaDien = DefaultValueWE.getGiaDien(); // bạn cần tạo hàm này trong DAO
//double giaNuoc = DefaultValueWE.getGiaNuoc();
//
//// 3️⃣ Tính tiền điện nước
//int soDienSuDung = newE - oldE;
//int soNuocSuDung = newW - oldW;
//
//double tienDien = soDienSuDung * giaDien;
//double tienNuoc = soNuocSuDung * giaNuoc;
//
//// 4️⃣ Tính tổng tiền
//double tongTien = tienDien + tienNuoc + giaphong + totalOtherServices; // tùy bạn có thể tính thêm
//
//// 5️⃣ Tạo hóa đơn chính
//HoaDon hd = new HoaDon();
//                hd.setIdphong(idphong);
//                hd.setNgaytaohdon(ngayTao);
//                hd.setTrangthai(false);
//                hd.setGhichu(ghiChu);
//                hd.setTongtien(tongTien);
//                hd.setImgDienCu(pathDienCu);
//                hd.setImgDienMoi(pathDienMoi);
//                hd.setImgNuocCu(pathNuocCu);
//                hd.setImgNuocMoi(pathNuocMoi);
//
//// 6️⃣ Insert vào bảng HoaDon
//hoaDonDAO = new qlthutien_HoaDonDAO(TaoHoaDonActivity.this);
//long result = hoaDonDAO.updateHoaDon(hd);
//
//                if (result == -1) {
//        Toast.makeText(this, "Lỗi khi cập nhật hóa đơn!", Toast.LENGTH_SHORT).show();
//                    return;
//                            }
//
//// 7️⃣ Tạo chi tiết hóa đơn cho điện
//chiTietDien = new ChiTietHoaDon();
//                chiTietDien.setIdhoadon((int) result);
//        chiTietDien.setTendichvu("Điện");
//                chiTietDien.setSodiencu(oldE);
//                chiTietDien.setSodienmoi(newE);
//                chiTietDien.setSosudung(soDienSuDung);
//                chiTietDien.setThanhtien((int) tienDien);
//
//// 8️⃣ Tạo chi tiết hóa đơn cho nước
//chiTietNuoc = new ChiTietHoaDon();
//                chiTietNuoc.setIdhoadon((int) result);
//        chiTietNuoc.setTendichvu("Nước");
//                chiTietNuoc.setSonuoccu(oldW);
//                chiTietNuoc.setSonuocmoi(newW);
//                chiTietNuoc.setSosudung(soNuocSuDung);
//                chiTietNuoc.setThanhtien((int) tienNuoc);
//
//// 9️⃣ Insert chi tiết hóa đơn
//chiTietHoaDonDAO = new ChiTietHoaDonDAO(TaoHoaDonActivity.this);
//                chiTietHoaDonDAO.insertChiTiet(chiTietDien);
//                chiTietHoaDonDAO.insertChiTiet(chiTietNuoc);
//
//                if (result != -1) {
//        Toast.makeText(this, "Cập nhật hóa đơn thành công!", Toast.LENGTH_SHORT).show();
//                } else {
//                        Toast.makeText(this, "Lỗi khi cập nhật hóa đơn!", Toast.LENGTH_SHORT).show();
//                }
//
//AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Cập nhật hóa đơn thành công");
//                builder.setMessage("Xem hóa đơn?");
//
//                builder.setNegativeButton("Quay về", new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        // Đóng activity hiện tại
//        finish();
//    }
//});
//
//        builder.setPositiveButton("Xem hóa đơn", new DialogInterface.OnClickListener() {
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        // Chuyển sang activity khác
//        Intent intent = new Intent(TaoHoaDonActivity.this, BillRoomActivity.class);
//        intent.putExtra("idhoadon", (int) result);
//        startActivity(intent);
//    }
//});
//        builder.show();
//            });
//                    }
// --------------------2-------------------------------------------
//int idhoadon = getIntent().getIntExtra("idhoadon", -1);
//int check = getIntent().getIntExtra("check", 0);
// ---------------------------------------------------------------
//giá phòng trong sửa
// ---------------------------------------------------------------
// ---------------------------------------------------------------
// ---------------------------------------------------------------
// ---------------------------------------------------------------