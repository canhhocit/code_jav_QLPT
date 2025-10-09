package com.example.logincustomer.ui.QLhopdong_y;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;
import com.example.logincustomer.data.Model.hopdong;
import com.example.logincustomer.data.Model.hopdong_ifKhach;
import com.example.logincustomer.data.Model.hopdong_ifRoom;

import java.util.Calendar;

public class hopdong_activity_chucnang extends AppCompatActivity {

    private EditText edtPhong, edtHoten, edtSdt, edtCccd, edtNgaysinh, edtNgayky;
    private Button btnThem, btnSua, btnXoa;
    private qlhopdong_hopdongDAO hdDAO;
    private Context context = hopdong_activity_chucnang.this;
    private String tenkhach;
    private int check;
    private int idkhach,idphong,idhopdong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlhopdong_activity_chucnang);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainchucnanghopdong), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhxa();
        getDataFromIntent();
        setEvents();
    }

    private void anhxa() {
        edtPhong = findViewById(R.id.hopdong_edt_phong);
        edtHoten = findViewById(R.id.hopdong_edt_hoten);
        edtSdt = findViewById(R.id.hopdong_edt_sdt);
        edtCccd = findViewById(R.id.hopdong_edt_cccd);
        edtNgaysinh = findViewById(R.id.hopdong_edt_ngaysinh);
        edtNgayky = findViewById(R.id.hopdong_edt_ngayky);
        edtPhong.setEnabled(false); edtSdt.setEnabled(false); edtHoten.setEnabled(false);
        btnThem = findViewById(R.id.hopdong_btnthem);
        btnSua = findViewById(R.id.hopdong_btnsua);
        btnXoa = findViewById(R.id.hopdong_btnxoa);
        btnThem.setVisibility(View.INVISIBLE); btnSua.setVisibility(View.INVISIBLE);btnXoa.setVisibility(View.INVISIBLE);
        hdDAO = new qlhopdong_hopdongDAO(this);
    }

    private void getDataFromIntent() {
        check = getIntent().getIntExtra("check", 0);
        if(check==1){
            btnThem.setVisibility(View.VISIBLE);
            idphong = getIntent().getIntExtra("idphong", -1);
            tenkhach = getIntent().getStringExtra("tenkhach");

            hopdong_ifRoom room=hdDAO.getinfRoombyID(idphong);
            int idkhach= hdDAO.getIDkhachbyten(tenkhach);
            if (idkhach == -1) {
                Toast.makeText(this, "Không tìm thấy khách thuê", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            hopdong_ifKhach khach = hdDAO.getinfKhachbyID(idkhach);
            if (khach == null) {
                Toast.makeText(this, "Lỗi: không tìm thấy thông tin khách thuê", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            edtHoten.setText(tenkhach);
            edtNgaysinh.setText(khach.getNgaysinh());
            edtSdt.setText(khach.getSdt());
            edtPhong.setText(room.getTenphong());
        }
        else if(check==2){
            btnSua.setVisibility(View.VISIBLE);btnXoa.setVisibility(View.VISIBLE);
            idphong = getIntent().getIntExtra("idphong", -1);
            hopdong_ifRoom room =hdDAO.getinfRoombyID(idphong);

            hopdong hd =hdDAO.getInf_HDbyIDphong(idphong);
            idhopdong = hd.getIdhopdong();
            hopdong_ifKhach khach = hdDAO.getinfKhachbyID(hd.getIdkhach());
            edtHoten.setText(khach.getTenkhach()); edtNgaysinh.setText(khach.getNgaysinh()); edtSdt.setText(khach.getSdt());
            edtCccd.setText(hd.getCccd()); edtNgayky.setText(hd.getNgayky()); edtPhong.setText(room.getTenphong());

        }

    }

    private void setEvents() {

        edtNgayky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayky);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themHopDong();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaHopDong();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaHopDong();
            }
        });
    }

    private void themHopDong() {
        String hoten = edtHoten.getText().toString().trim();
        String sdt = edtSdt.getText().toString().trim();
        String cccd = edtCccd.getText().toString().trim();
        String ngayky = edtNgayky.getText().toString().trim();

        if (hoten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || ngayky.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        idkhach = hdDAO.getIDkhachbyten(hoten);
        if (idkhach == -1) {
            Toast.makeText(this, "Không tìm thấy khách thuê!", Toast.LENGTH_SHORT).show();
            return;
        }

        hopdong hd = new hopdong();
        hd.setCccd(cccd);
        hd.setNgayky(ngayky);
        hd.setIdphong(idphong);
        hd.setIdkhach(idkhach);

        long result = hdDAO.insertHD(hd);
        if (result > 0) {
            Toast.makeText(this, "Thêm hợp đồng thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, hopdong_activity_dshopdong.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Thêm hợp đồng thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void suaHopDong() {
        String cccd = edtCccd.getText().toString().trim();
        String ngayky = edtNgayky.getText().toString().trim();

        hopdong hd = new hopdong();
        hd.setIdhopdong(idhopdong);
        hd.setCccd(cccd);
        hd.setNgayky(ngayky);


        long result = hdDAO.updateHD(hd);
        if (result > 0) {
            Toast.makeText(this, "Cập nhật hợp đồng thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, hopdong_activity_dshopdong.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaHopDong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa hợp đồng này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int result = hdDAO.deleteHD(idhopdong);
                if (result > 0) {
                    hdDAO.deleteKhacThue(idkhach);
                    Toast.makeText(hopdong_activity_chucnang.this, "Đã xóa hợp đồng!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, hopdong_activity_dshopdong.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(hopdong_activity_chucnang.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDatePickerDialog(final EditText target) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                hopdong_activity_chucnang.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        target.setText(d + "/" + (m + 1) + "/" + y);
                    }
                },
                year, month, day
        );
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }
}
