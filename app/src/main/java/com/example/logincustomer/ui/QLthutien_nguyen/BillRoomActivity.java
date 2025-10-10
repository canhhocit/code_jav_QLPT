package com.example.logincustomer.ui.QLthutien_nguyen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private ImageView imgBackBillRoom, btnDeleteBillRoom, imgStatusBillRoom, imgExpandMoneyBillRoom;
    private TextView txtRoomBillRoom, txtStatusBillRoom, txtDateBillRoom,
            txtQuantityPeopleBillRoom, txtOldWaterBillRoom, txtNewWaterBillRoom, txtPriceWaterBillRoom,
            txtSumServiceWaterBillRoom, txtOldElectricBillRoom, txtNewElectricBillRoom,txtSumServiceElectricBillRoom,
            txtPriceElectricBillRoom, txtOtherServiceTotal, txtSumMoneyBillRoom,txtdetailEbillroom,
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlthutien_layout_billroom);

        anhxa();


        txtRoomBillRoom.setText(getIntent().getStringExtra("Tenphong"));
        txtOldWaterBillRoom.setText(String.valueOf(getIntent().getIntExtra("Nuoccu", 0)));
        txtNewWaterBillRoom.setText(String.valueOf(getIntent().getIntExtra("Nuocmoi", 0)));
        txtOldElectricBillRoom.setText(String.valueOf(getIntent().getIntExtra("Diencu", 0)));
        txtNewElectricBillRoom.setText(String.valueOf(getIntent().getIntExtra("Dienmoi", 0)));

        // Nhận id hoá đơn được truyền từ intent
        idHoaDon = getIntent().getIntExtra("idhoadon", -1);
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
        }

        txtDateBillRoom.setText("" + hd.getNgaytaohdon());
        //tvTrangThai.setText(hd.isTrangthai() ? "Đã thanh toán" : "Chưa thanh toán");
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

        txtdetailEbillroom = findViewById(R.id.detailE_billroom);
        txtOldElectricBillRoom = findViewById(R.id.txt_oldElectric_billRoom);
        txtNewElectricBillRoom = findViewById(R.id.txt_newElectric_billRoom);
        txtPriceElectricBillRoom = findViewById(R.id.txt_priceElectric_billRoom);
        txtSumServiceElectricBillRoom = findViewById(R.id.txt_sumServiceElectric_billRoom);

        txtSumMoneyBillRoom = findViewById(R.id.txt_sumMoney_billRoom);
        edtNoteBillRoom = findViewById(R.id.edt_note_billRoom);

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

}

