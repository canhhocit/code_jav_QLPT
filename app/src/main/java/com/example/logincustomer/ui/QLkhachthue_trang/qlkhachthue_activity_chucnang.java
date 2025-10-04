package com.example.logincustomer.ui.QLkhachthue_trang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.khachthueDAO;
import com.example.logincustomer.data.Model.khachthue;

import java.util.Calendar;

public class qlkhachthue_activity_chucnang extends AppCompatActivity {
    private ImageView imageBack;
    private EditText edtHoTen, edtNgaySinh, edtSDT, edtDiaChi, edtPhong;
    private RadioGroup rdgGioiTinh;
    private Button btnSua, btnXoa, btnThem;
    private khachthueDAO khachDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlkhachthue_activity_chucnang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhXaID();
        khachDAO = new khachthueDAO(this);
        nhandulieu();
        them();
        Sua();
        Xoa();
        back_list();


    }

    private void Xoa() {
      btnXoa.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = getIntent();
              int idkhach = intent.getIntExtra("idkhach", -1);
              if (idkhach != -1) {
                  khachDAO.deleteKhacThue(idkhach);
                  Toast.makeText(qlkhachthue_activity_chucnang.this, "Đã xóa khách thuê!", Toast.LENGTH_SHORT).show();
                  finish();
              } else {
                  Toast.makeText(qlkhachthue_activity_chucnang.this, "Lỗi: không tìm thấy khách!", Toast.LENGTH_SHORT).show();
              }
          }
      });

    }

    private void Sua() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= getIntent();
                int idkhach=intent.getIntExtra("idkhach",-1);
                khachthue kt = getdatafromText();
                if (kt == null) {
                    Toast.makeText(qlkhachthue_activity_chucnang.this, "chua co du lieu khach thue", Toast.LENGTH_SHORT).show();
                    return;
                }

                kt.setIdkhach(idkhach);
                khachDAO.updateKhachThue(kt);
                Toast.makeText(qlkhachthue_activity_chucnang.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                finish(); // return dstt
            }
        });
    }

    private void back_list() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(qlkhachthue_activity_chucnang.this,qlkhachthue_activity_dstt.class);
                startActivity(intent);
            }
        });
    }

    private void nhandulieu() {
        Intent intent = getIntent();
        int check = intent.getIntExtra("check", 0);
        if (check == 1) {
            btnThem.setVisibility(View.VISIBLE);
        } else if (check == 2) {
            btnSua.setVisibility(View.VISIBLE);
            btnXoa.setVisibility(View.VISIBLE);

            String hoten = intent.getStringExtra("hoten");
            String gioitinh = intent.getStringExtra("gioitinh");
            String ngaysinh = intent.getStringExtra("ngaysinh");
            String sdt = intent.getStringExtra("sdt");
            String diachi = intent.getStringExtra("diachi");
            int idphong = intent.getIntExtra("idphong", -1);

            edtHoTen.setText(hoten);
            edtNgaySinh.setText(ngaysinh);
            edtSDT.setText(sdt);
            edtDiaChi.setText(diachi);
            // lay ten phong tu id
            khachDAO = new khachthueDAO(this);
            String tenphong = khachDAO.getTenphongbyID(idphong);
            edtPhong.setText(tenphong);

            if ("Nam".equalsIgnoreCase(gioitinh)) {
                ((RadioButton) findViewById(R.id.khachthue_radioNam)).setChecked(true);
            } else if ("Nữ".equalsIgnoreCase(gioitinh)) {
                ((RadioButton) findViewById(R.id.khachthue_radioNu)).setChecked(true);
            }


        }
    }

    private void them() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachDAO.insertKhachThue(getdatafromText());
                Toast.makeText(qlkhachthue_activity_chucnang.this, "Thêm khách thuê thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
    private khachthue getdatafromText() {
        String hoTen = edtHoTen.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        String phong = edtPhong.getText().toString().trim();

        String gioiTinh = "";
        int selectedId = rdgGioiTinh.getCheckedRadioButtonId();
        if (selectedId == R.id.khachthue_radioNam) {
            gioiTinh = "Nam";
        } else if (selectedId == R.id.khachthue_radioNu) {
            gioiTinh = "Nữ";
        }

        if (hoTen.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() ||
                diaChi.isEmpty() || phong.isEmpty() || gioiTinh.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!khachDAO.checkIDbyTenphong(phong)) {
            Toast.makeText(this, "Phòng không tồn tại!", Toast.LENGTH_SHORT).show();
            return null;
        }

        int idphong = khachDAO.getIDbyTenphong(phong);
        return new khachthue(hoTen, gioiTinh, ngaySinh, sdt, diaChi, idphong);
    }




    private void anhXaID() {
        imageBack = findViewById(R.id.image_back);
        edtHoTen = findViewById(R.id.khachthue_edt_hoten);
        edtNgaySinh = findViewById(R.id.khachthue_edt_ngaysinh);
        edtSDT = findViewById(R.id.khachthue_edt_sdt);
        edtDiaChi = findViewById(R.id.khachthue_edt_diachi);
        edtPhong = findViewById(R.id.khachthue_edt_phong);

        rdgGioiTinh = findViewById(R.id.khachthue_rdogroup);

        btnSua = findViewById(R.id.khachthue_btnSua);
        btnXoa = findViewById(R.id.khachthue_btnXoa);
        btnThem = findViewById(R.id.khachthue_btnThem);
        btnThem.setVisibility(View.INVISIBLE);
        btnSua.setVisibility(View.INVISIBLE);
        btnXoa.setVisibility(View.INVISIBLE);

        //click ngay
        edtNgaySinh.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    qlkhachthue_activity_chucnang.this,
                    (view, year1, month1, dayOfMonth) ->
                            edtNgaySinh.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    y, m, d
            );
            dialog.show();
        });
    }

}