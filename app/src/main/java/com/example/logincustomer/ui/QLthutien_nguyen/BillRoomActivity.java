package com.example.logincustomer.ui.QLthutien_nguyen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlthutien_DichVuConAdapter;
import com.example.logincustomer.data.DAO.qlthutien_ChiTietHoaDonDAO;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_DichVuConDAO;
import com.example.logincustomer.data.DAO.qlthutien_GiaMacDinhDienNuocDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.DatabaseHelper.DatabaseHelper;
import com.example.logincustomer.data.Model.qlthutien_ChiTietHoaDon;
import com.example.logincustomer.data.Model.qlthutien_DichVuCon;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;
import com.example.logincustomer.data.Model.qlphongtro_PhongTro;
import com.example.logincustomer.ui.Manager_Home.activity_manager;
import com.example.logincustomer.ui.QLphong_tam.qlphong_activity_home;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BillRoomActivity extends AppCompatActivity {

    private ImageView imgBackBillRoom, imgStatusBillRoom, imgExpandMoneyBillRoom,
    imgNuocMoi, imgNuocCu, imgDienMoi, imgDienCu;
    private TextView txtRoomBillRoom, txtStatusBillRoom, txtDateBillRoom,
            txtQuantityPeopleBillRoom, txtOldWaterBillRoom, txtNewWaterBillRoom, txtPriceWaterBillRoom,
            txtSumServiceWaterBillRoom, txtOldElectricBillRoom, txtNewElectricBillRoom,txtSumServiceElectricBillRoom,
            txtPriceElectricBillRoom, txtOtherServiceTotal, txtSumMoneyBillRoom,txtpriceRoom,
            txtdetailWbillroom, txtdetailEbillroom;
    private EditText edtNoteBillRoom;
    private Button btnBackBillRoom, btnPayBillRoom;
    private qlthutien_DichVuConDAO dichVuConDAO;
    private ArrayList<qlthutien_DichVuCon> listOtherServices;
    private qlthutien_DichVuConAdapter otherAdapter;
    private double totalOtherServices = 0.0;
    private RecyclerView recyclerOtherService;
    private boolean isOtherExpanded = false;
    private double giaDien;
    private double giaNuoc;
    private static final double GIA_DIEN_MACDINH = 3500;
    private static final double GIA_NUOC_MACDINH = 20000;
    private final DecimalFormat df = new DecimalFormat("#,###");
    private int idHoaDon;
    private qlthutien_HoaDonDAO hoaDonDAO;
    private qlthutien_ChiTietHoaDonDAO qlthutienChiTietHoaDonDAO;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private qlthutien_GiaMacDinhDienNuocDAO dienNuocDAO;
    private boolean dathanhtoan = false;
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlthutien_layout_billroom);
        anhxa();

        hoaDonDAO = new qlthutien_HoaDonDAO(this);
        qlthutienChiTietHoaDonDAO = new qlthutien_ChiTietHoaDonDAO(this);
        phongTroDAO = new qlphongtro_PhongTroDAO(this);
        dienNuocDAO = new qlthutien_GiaMacDinhDienNuocDAO(this);

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
        idHoaDon = getIntent().getIntExtra("idhoadon", -1);
        if (idHoaDon == -1) {
            Toast.makeText(this, "Không tìm thấy hóa đơn! BillroomActivity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        hienThiHoaDon(idHoaDon);

        int checkback = getIntent().getIntExtra("checkback", -1);

        setupBackHandler(checkback);
        quaytrove(checkback);

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
                    try {
                        int success = hoaDonDAO.updateTrangThai(idHoaDon, true);

                        if (dathanhtoan) {
                            Toast.makeText(BillRoomActivity.this, "Hóa đơn đã thanh toán!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (success == 1) {
                            Toast.makeText(BillRoomActivity.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                            hienThiHoaDon(idHoaDon);
                        } else {
                            Toast.makeText(BillRoomActivity.this, "Hủy thanh toán!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DEBUG", "Lỗi: " + e.getMessage());

                    }
                }
            });
            builder.show();
        });

        txtdetailWbillroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DEBUG", "Đã nhấn vào txtdetailWbillroom");
                qlthutien_HoaDon hd = hoaDonDAO.getHoaDonById(idHoaDon);
                if (hd == null) {
                    Log.e("DEBUG", "Không tìm thấy hóa đơn");
                    return;
                }
                Log.d("DEBUG", "imgNuocCu = " + hd.getImgNuocCu());
                Log.d("DEBUG", "imgNuocMoi = " + hd.getImgNuocMoi());

                showBottomSheetWImages(hd);
            }
        });

        txtdetailEbillroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qlthutien_HoaDon hd = hoaDonDAO.getHoaDonById(idHoaDon);
                showBottomSheetEImages(hd);
            }
        });
    }

    private void hienThiHoaDon(int idHoaDon) {
        qlthutien_HoaDon hd = hoaDonDAO.getHoaDonById(idHoaDon);

        if (hd == null) {
            Toast.makeText(this, "Không tìm thấy dữ liệu hóa đơn!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin phòng
        qlphongtro_PhongTro phong = phongTroDAO.getPhongById(hd.getIdphong());
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
        List<qlthutien_ChiTietHoaDon> chiTietList = qlthutienChiTietHoaDonDAO.getByHoaDonId(idHoaDon);
        for (qlthutien_ChiTietHoaDon ct : chiTietList) {
            if (ct.getTendichvu().equalsIgnoreCase("Điện")) {
                txtOldElectricBillRoom.setText(String.valueOf(ct.getSodiencu()));
                txtNewElectricBillRoom.setText(String.valueOf(ct.getSodienmoi()));
                txtSumServiceElectricBillRoom.setText(String.format("%,.0f đ", (double) ct.getThanhtien()));
                txtPriceElectricBillRoom.setText(String.format("%,.0f đ", giaDien));
            } else if (ct.getTendichvu().equalsIgnoreCase("Nước")) {
                txtOldWaterBillRoom.setText(String.valueOf(ct.getSonuoccu()));
                txtNewWaterBillRoom.setText(String.valueOf(ct.getSonuocmoi()));
                txtSumServiceWaterBillRoom.setText(String.format("%,.0f đ", (double) ct.getThanhtien()));
                txtPriceWaterBillRoom.setText(String.format("%,.0f đ", giaNuoc));
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

        txtdetailEbillroom = findViewById(R.id.detailE_billroom);
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
    private void quaytrove(int checkback){
        imgBackBillRoom.setOnClickListener(v -> {
            if (checkback == 1) {
                Intent intent = new Intent(BillRoomActivity.this, qlphong_activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        });

        btnBackBillRoom.setOnClickListener(v -> {
            Intent intent = new Intent(BillRoomActivity.this, activity_manager.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
        giaDien = dienNuocDAO.getGiaDien();
        giaNuoc = dienNuocDAO.getGiaNuoc();

        if(giaDien == 0 || giaNuoc ==0){
            if(giaDien == 0 && giaNuoc == 0){
                giaDien = GIA_DIEN_MACDINH;
                giaNuoc = GIA_NUOC_MACDINH;
            } else if (giaDien == 0) {
                giaDien = GIA_DIEN_MACDINH;
            }else {
                giaNuoc = GIA_NUOC_MACDINH;
            }
        }
    }

    private void calculateTotalOtherServices() {
        totalOtherServices = 0.0;
        if (listOtherServices != null) {
            for (qlthutien_DichVuCon dv : listOtherServices) {
                totalOtherServices += dv.getGiatien();
            }
        }
    }

    private void checktrangthai(qlthutien_HoaDon hd){
        dathanhtoan = hd.isTrangthai();
        if (dathanhtoan) {
            imgStatusBillRoom.setImageResource(R.drawable.img_correct);
            txtStatusBillRoom.setText("Đã thanh toán");
            txtStatusBillRoom.setTextColor(Color.parseColor("#00AA00"));
        } else {
            imgStatusBillRoom.setImageResource(R.drawable.img_wrong);
            txtStatusBillRoom.setText("Chưa thanh toán");
            txtStatusBillRoom.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    private void showBottomSheetWImages(qlthutien_HoaDon hoaDon) {
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.qlthutien_layout_bottomsheet, null);
        bottomSheetDialog.setContentView(view);

        imgNuocCu = view.findViewById(R.id.imganhCu);
        imgNuocMoi = view.findViewById(R.id.imganhMoi);

        // Gọi hàm load ảnh từ đường dẫn
        loadImageFromPath(hoaDon.getImgNuocCu(), imgNuocCu);
        loadImageFromPath(hoaDon.getImgNuocMoi(), imgNuocMoi);

        bottomSheetDialog.show();
    }
    private void showBottomSheetEImages(qlthutien_HoaDon hoaDon) {
        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.qlthutien_layout_bottomsheet, null);
        bottomSheetDialog.setContentView(view);

        imgDienCu = view.findViewById(R.id.imganhCu);
        imgDienMoi = view.findViewById(R.id.imganhMoi);

        loadImageFromPath(hoaDon.getImgDienCu(), imgDienCu);
        loadImageFromPath(hoaDon.getImgDienMoi(), imgDienMoi);

        bottomSheetDialog.show();
    }
    private void loadImageFromPath(String imagePath, ImageView imageView) {
        if (imagePath == null || imagePath.isEmpty()) {
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            return;
        }

        try {
            if (imagePath.startsWith("content://")) {
                Uri uri = Uri.parse(imagePath);
                try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);
                }
            } else {
                // Đường dẫn file thông thường
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(android.R.drawable.ic_menu_report_image);
                }
            }
        } catch (Exception e) {
            Log.e("DEBUG", "Lỗi load ảnh: " + e.getMessage());
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        }

    }

    private void setupBackHandler(int checkback) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (checkback == 1) {
                    Intent intent = new Intent(BillRoomActivity.this, qlphong_activity_home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }


}

