package com.example.logincustomer.QLkhachthue_trang;

import android.os.Bundle;
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

public class qlkhachthue_activity_chucnang extends AppCompatActivity {
    private ImageView imageBack;
    private EditText edtHoTen, edtNgaySinh, edtSDT, edtDiaChi, edtPhong;
    private RadioGroup rdgGioiTinh;
    private RadioButton rdoNam, rdoNu;
    private Button btnSua, btnXoa, btnThem;
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
        them();
    }

    private void them() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(qlkhachthue_activity_chucnang.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String thongTin = "Thêm khách thuê:\n" +
                        "Họ tên: " + hoTen + "\n" +
                        "Giới tính: " + gioiTinh + "\n" +
                        "Ngày sinh: " + ngaySinh + "\n" +
                        "SĐT: " + sdt + "\n" +
                        "Địa chỉ: " + diaChi + "\n" +
                        "Phòng: " + phong;

                Toast.makeText(qlkhachthue_activity_chucnang.this, thongTin, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void anhXaID() {
        imageBack = findViewById(R.id.image_back);
        edtHoTen = findViewById(R.id.khachthue_edt_hoten);
        edtNgaySinh = findViewById(R.id.khachthue_edt_ngaysinh);
        edtSDT = findViewById(R.id.khachthue_edt_sdt);
        edtDiaChi = findViewById(R.id.khachthue_edt_diachi);
        edtPhong = findViewById(R.id.khachthue_edt_phong);

        rdgGioiTinh = findViewById(R.id.khachthue_rdogroup);
        rdoNam = findViewById(R.id.khachthue_radioNam);
        rdoNu = findViewById(R.id.khachthue_radioNu);

        btnSua = findViewById(R.id.khachthue_btnSua);
        btnXoa = findViewById(R.id.khachthue_btnXoa);
        btnThem = findViewById(R.id.khachthue_btnThem);
    }

}