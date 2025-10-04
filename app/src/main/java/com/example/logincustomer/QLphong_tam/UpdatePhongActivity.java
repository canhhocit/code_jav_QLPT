package com.example.logincustomer.QLphong_tam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.DAO.PhongTroDAO;
import com.example.logincustomer.Model.PhongTro;
import com.example.logincustomer.R;

public class UpdatePhongActivity extends AppCompatActivity {

    EditText edtTenPhong, edtSoNguoi, edtGia;
    Button btnLuu;
    PhongTroDAO phongTroDAO;
    int idPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_dialog_suaphong);

        edtTenPhong = findViewById(R.id.edt_TenPhong_suaphong);
        edtGia = findViewById(R.id.edt_giaPhong_suaphong);
        btnLuu = findViewById(R.id.btnLuu_suaphong);

        phongTroDAO = new PhongTroDAO(this);

        // Nhận dữ liệu từ Intent
        idPhong = getIntent().getIntExtra("idPhong", -1);
        edtTenPhong.setText(getIntent().getStringExtra("tenPhong"));
        edtSoNguoi.setText(String.valueOf(getIntent().getIntExtra("soNguoi", 0)));
        edtGia.setText(String.valueOf(getIntent().getDoubleExtra("gia", 0)));

        btnLuu.setOnClickListener(v -> {
            String ten = edtTenPhong.getText().toString().trim();
            String soNguoiStr = edtSoNguoi.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || soNguoiStr.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int soNguoi = Integer.parseInt(soNguoiStr);
            double gia = Double.parseDouble(giaStr);

            PhongTro phong = new PhongTro(idPhong, ten, soNguoi, gia, 0);
            int result = phongTroDAO.updatePhongTro(phong);

            if (result > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại danh sách
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}