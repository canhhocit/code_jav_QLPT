package com.example.logincustomer.ui.QLphong_tam;

import android.content.Intent;
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

import com.example.logincustomer.data.DAO.PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.R;

public class UpdatePhongActivity extends AppCompatActivity {

    EditText edtTenPhong, edtSoNguoi, edtGia;
    Button btnLuu,btnhuy;
    PhongTroDAO phongTroDAO;
    int idPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_dialog_suaphong);

        btnhuy = findViewById(R.id.btnHuy_suaphong);

        edtTenPhong = findViewById(R.id.edt_TenPhong_suaphong);
        edtGia = findViewById(R.id.edt_giaPhong_suaphong);
        btnLuu = findViewById(R.id.btnLuu_suaphong);

        phongTroDAO = new PhongTroDAO(this);

        // Nhận dữ liệu từ Intent
        idPhong = getIntent().getIntExtra("idPhong", -1);
        edtTenPhong.setText(getIntent().getStringExtra("tenPhong"));
        edtGia.setText(String.valueOf(getIntent().getDoubleExtra("gia", 0)));

        btnhuy.setOnClickListener(v -> {
            finish();
        });

        btnLuu.setOnClickListener(v -> {
            String ten = edtTenPhong.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            double gia = Double.parseDouble(giaStr);
            PhongTro phong = new PhongTro(idPhong, ten, 0, gia, 0);
            int result = phongTroDAO.updatePhongTro(phong);

            if (result > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                // Gửi dữ liệu về để cập nhật danh sách
                Intent resultIntent = new Intent();
                resultIntent.putExtra("idPhong", idPhong);
                resultIntent.putExtra("tenPhong", ten);
                resultIntent.putExtra("gia", gia);
                setResult(RESULT_OK, resultIntent);

                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}