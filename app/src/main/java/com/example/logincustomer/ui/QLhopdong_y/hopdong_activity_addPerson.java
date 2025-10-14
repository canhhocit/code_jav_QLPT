package com.example.logincustomer.ui.QLhopdong_y;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.logincustomer.ui.QLhopdong_y.hopdong_activity_chucnang;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;

import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;

import java.util.Calendar;

public class hopdong_activity_addPerson extends AppCompatActivity {

    private ImageView imgBack;
    private EditText edtHoten, edtNgaysinh, edtSdt, edtDiachi, edtPhong;
    private RadioGroup rdGroup;
    private RadioButton rdNam, rdNu;
    private Button btnThem;
    private Context context = hopdong_activity_addPerson.this;
    private int idphong;
    private String tenphong;
    private qlhopdong_hopdongDAO hdDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlhopdong_activity_add_person);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhxa();
        getIntentData();
        setEvents();
    }

    private void anhxa() {
        imgBack = findViewById(R.id.hopndong_khachthue_image_back);
        edtHoten = findViewById(R.id.hopdong_khachthue_edt_hoten);
        edtNgaysinh = findViewById(R.id.hopdong_khachthue_edt_ngaysinh);
        edtSdt = findViewById(R.id.hopdong_khachthue_edt_sdt);
        edtDiachi = findViewById(R.id.hopdong_khachthue_edt_diachi);
        edtPhong = findViewById(R.id.hopdong_khachthue_edt_phong);
        rdGroup = findViewById(R.id.hopdong_khachthue_rdogroup);
        rdNam = findViewById(R.id.hopdong_khachthue_radioNam);
        rdNu = findViewById(R.id.hopdong_khachthue_radioNu);
        btnThem = findViewById(R.id.hopdong_khachthue_btnThem);
        hdDAO = new qlhopdong_hopdongDAO(context);
        edtNgaysinh.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    context,
                    (view, year1, month1, dayOfMonth) -> {
                        if (year1 > 2015) {
                            Toast.makeText(this, "Năm sinh phải nhỏ hơn 2015!", Toast.LENGTH_SHORT).show();
                        } else {
                            edtNgaysinh.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                        }
                    },
                    y, m, d
            );
            //k cho chon ngay>ngay htai
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();

        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        idphong = intent.getIntExtra("idphong", -1);
        tenphong = intent.getStringExtra("tenphong");
        edtPhong.setText(tenphong);
        edtPhong.setEnabled(false);
    }

    private void setEvents() {
        imgBack.setOnClickListener(v -> finish());

        btnThem.setOnClickListener(v -> {
            String hoten = edtHoten.getText().toString().trim();
            String ngaysinh = edtNgaysinh.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String diachi = edtDiachi.getText().toString().trim();
            String gioitinh = rdNam.isChecked() ? "Nam" : rdNu.isChecked() ? "Nữ" : "";
            if (hoten.isEmpty() || ngaysinh.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || gioitinh.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            khachthue khach = new khachthue();
            khach.setHoten(hoten);
            khach.setNgaysinh(ngaysinh);
            khach.setSdt(sdt);
            khach.setDiachi(diachi);
            khach.setGioitinh(gioitinh);
            khach.setIdphong(idphong);
            if (idphong == -1) {
                Toast.makeText(this, "Không xác định được phòng!", Toast.LENGTH_SHORT).show();
                return;
            }

            int checkdialog = getIntent().getIntExtra("checkDialog",-1);
            if(checkdialog ==1){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thêm thất bại");
                builder.setIcon(R.drawable.img_wrong);
                builder.setMessage("Phòng này chưa được ký hợp đồng, bạn có muốn ký hợp đồng với phòng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long result = hdDAO.insertKhachThue(khach);
                        if (result>0) {
                            Toast.makeText(context, "Thêm khách thuê thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(context, hopdong_activity_chucnang.class);
                        intent.putExtra("check",1);
                        intent.putExtra("idphong",idphong);
                        intent.putExtra("tenkhach",hoten);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            }

        });
    }


}
