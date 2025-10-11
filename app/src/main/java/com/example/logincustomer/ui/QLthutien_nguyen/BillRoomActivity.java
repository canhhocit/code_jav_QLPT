package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlthutien_DichVuConAdapter;
import com.example.logincustomer.data.DAO.ChiTietHoaDonDAO;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DAO.qlthutien_GiaMacDinhDienNuocDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.ChiTietHoaDon;
import com.example.logincustomer.data.Model.DichVuCon;
import com.example.logincustomer.data.Model.HoaDon;
import com.example.logincustomer.data.Model.PhongTro;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BillRoomActivity extends AppCompatActivity {

    private ImageView imgBackBillRoom, imgStatusBillRoom, imgExpandMoneyBillRoom;
    private TextView txtRoomBillRoom, txtStatusBillRoom, txtDateBillRoom,
            txtQuantityPeopleBillRoom, txtOldWaterBillRoom, txtNewWaterBillRoom, txtPriceWaterBillRoom,
            txtSumServiceWaterBillRoom, txtOldElectricBillRoom, txtNewElectricBillRoom,txtSumServiceElectricBillRoom,
            txtPriceElectricBillRoom, txtOtherServiceTotal, txtSumMoneyBillRoom,txtpriceRoom,
            txtdetailWbillroom;
    private EditText edtNoteBillRoom;
    private Button btnBackBillRoom, btnPayBillRoom;
    // danh sách dịch vụ con (lấy từ DB)
    private qlthutien_DichVuConDAO dichVuConDAO;
    private ArrayList<DichVuCon> listOtherServices;
    private qlthutien_DichVuConAdapter otherAdapter;
    private double totalOtherServices = 0.0;
    private RecyclerView recyclerOtherService;
    private boolean isOtherExpanded = false;
    private double giaDien;
    private double giaNuoc;
    private static final double GIA_DIEN_MACDINH = 3500;
    private static final double GIA_NUOC_MACDINH = 15000;
    private final DecimalFormat df = new DecimalFormat("#,###");
    private int idHoaDon;
    private qlthutien_HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private qlthutien_GiaMacDinhDienNuocDAO dienNuocDAO;
    private boolean dathanhtoan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlthutien_layout_billroom);

        anhxa();

        // Lấy giá điện nước từ database (nếu có)
        layGiaMacDinhTuDatabase();
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
        imgExpandMoneyBillRoom.setOnClickListener(toggleOther);
        txtOtherServiceTotal.setOnClickListener(toggleOther);

        // Nhận id hoá đơn được truyền từ intent
        idHoaDon = (int)getIntent().getLongExtra("idhoadon", -1);
        if (idHoaDon == -1) {
            Toast.makeText(this, "Không tìm thấy hóa đơn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        hoaDonDAO = new qlthutien_HoaDonDAO(this);
        chiTietHoaDonDAO = new ChiTietHoaDonDAO(this);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);
        dienNuocDAO = new qlthutien_GiaMacDinhDienNuocDAO(this);

        hienThiHoaDon(idHoaDon);

        quaytrove();

        btnPayBillRoom.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận thanh toán");
            builder.setMessage("Thanh toán?");

            builder.setNegativeButton("Quay về", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Đóng activity hiện tại
                    finish();
                }
            });

            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean success = hoaDonDAO.updateTrangThaiHoaDon(idHoaDon, true);

                    if(dathanhtoan){
                        Toast.makeText(BillRoomActivity.this, "Hóa đơn đã thanh toán!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (success) {
                        Toast.makeText(BillRoomActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                        hienThiHoaDon(idHoaDon);
                    } else {
                        Toast.makeText(BillRoomActivity.this, "Hủy thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();

        });
    }

    private void hienThiHoaDon(int idHoaDon) {
        HoaDon hd = hoaDonDAO.getHoaDonById(idHoaDon);

        if (hd == null) {
            Toast.makeText(this, "Không tìm thấy dữ liệu hóa đơn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin phòng
        PhongTro phong = phongTroDAO.getPhongById(hd.getIdphong());
        if (phong != null) {
            txtRoomBillRoom.setText("" + phong.getTenphong());
            txtQuantityPeopleBillRoom.setText("" + phong.getSonguoi());
            txtpriceRoom.setText(String.format("%,.0f đ", phong.getGia()));
        }

        checktrangthai(hd);

        txtDateBillRoom.setText("" + hd.getNgaytaohdon());
        edtNoteBillRoom.setText(""+hd.getGhichu());
        txtSumMoneyBillRoom.setText(String.format("%,.0f đ", hd.getTongtien()));

        // Lấy chi tiết hóa đơn (Điện & Nước)
        List<ChiTietHoaDon> chiTietList = chiTietHoaDonDAO.getByHoaDonId(idHoaDon);
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getTendichvu().equalsIgnoreCase("Điện")) {
                txtOldElectricBillRoom.setText(String.valueOf(ct.getSodiencu()));
                txtNewElectricBillRoom.setText(String.valueOf(ct.getSodienmoi()));
                txtSumServiceElectricBillRoom.setText(String.format("%,.0f đ", (double) ct.getThanhtien()));
                txtPriceElectricBillRoom.setText(String.format("%,.0f đ", dienNuocDAO.getGiaDien()));
            } else if (ct.getTendichvu().equalsIgnoreCase("Nước")) {
                txtOldWaterBillRoom.setText(String.valueOf(ct.getSonuoccu()));
                txtNewWaterBillRoom.setText(String.valueOf(ct.getSonuocmoi()));
                txtSumServiceWaterBillRoom.setText(String.format("%,.0f đ", (double) ct.getThanhtien()));
                txtPriceWaterBillRoom.setText(String.format("%,.0f đ", dienNuocDAO.getGiaNuoc()));
            }
        }
    }

    private void anhxa(){
        // Ánh xạ
        imgBackBillRoom = findViewById(R.id.img_back_billRoom);
        imgStatusBillRoom = findViewById(R.id.img_status_billRoom);
        txtStatusBillRoom = findViewById(R.id.txt_status_billRoom);

        imgExpandMoneyBillRoom = findViewById(R.id.img_expand_OtherService_billRoom);
        txtOtherServiceTotal = findViewById(R.id.txt_totalotherService_billRoom);
        recyclerOtherService = findViewById(R.id.recycler_OtherService_billRoom);

        txtRoomBillRoom = findViewById(R.id.txt_room_billRoom);
        txtDateBillRoom = findViewById(R.id.txt_date_billRoom);
        txtQuantityPeopleBillRoom = findViewById(R.id.txt_quantityPeople_billRoom);

        txtdetailWbillroom = findViewById(R.id.detailW_billroom);
        txtOldWaterBillRoom = findViewById(R.id.txt_oldWater_billRoom);
        txtNewWaterBillRoom = findViewById(R.id.txt_newWater_billRoom);
        txtPriceWaterBillRoom = findViewById(R.id.txt_priceWater_billRoom);
        txtSumServiceWaterBillRoom = findViewById(R.id.txt_sumServiceWater_billRoom);

        txtpriceRoom = findViewById(R.id.detailE_billroom);
        txtOldElectricBillRoom = findViewById(R.id.txt_oldElectric_billRoom);
        txtNewElectricBillRoom = findViewById(R.id.txt_newElectric_billRoom);
        txtPriceElectricBillRoom = findViewById(R.id.txt_priceElectric_billRoom);
        txtSumServiceElectricBillRoom = findViewById(R.id.txt_sumServiceElectric_billRoom);

        txtSumMoneyBillRoom = findViewById(R.id.txt_sumMoney_billRoom);
        edtNoteBillRoom = findViewById(R.id.edt_note_billRoom);

        txtpriceRoom = findViewById(R.id.txt_priceRoom_billRoom);
        btnBackBillRoom = findViewById(R.id.btn_back_billRoom);
        btnPayBillRoom = findViewById(R.id.btn_pay_billRoom);
    }
    private void quaytrove(){
        imgBackBillRoom.setOnClickListener(v -> {
            finish();
        });
        btnBackBillRoom.setOnClickListener(v -> {
            finish();
        });
    }
    private void toggleOtherServices() {
        if (isOtherExpanded) {
            recyclerOtherService.setVisibility(View.GONE);
            imgExpandMoneyBillRoom.animate().rotation(0).setDuration(200).start();
        } else {
            recyclerOtherService.setVisibility(View.VISIBLE);
            imgExpandMoneyBillRoom.animate().rotation(180).setDuration(200).start(); // xoay chỉ minh hoạ
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

    private void checktrangthai(HoaDon hd){
        dathanhtoan = hd.isTrangthai();
        if (dathanhtoan) {
            imgStatusBillRoom.setImageResource(R.drawable.img_correct);  // ảnh "đã thanh toán"
            txtStatusBillRoom.setText("Đã thanh toán");
            txtStatusBillRoom.setTextColor(Color.parseColor("#00AA00")); // màu xanh
        } else {
            imgStatusBillRoom.setImageResource(R.drawable.img_wrong);    // ảnh "chưa thanh toán"
            txtStatusBillRoom.setText("Chưa thanh toán");
            txtStatusBillRoom.setTextColor(Color.parseColor("#FF0000")); // màu đỏ
        }
    }

}

