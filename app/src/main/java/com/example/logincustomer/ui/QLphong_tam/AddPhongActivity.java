package com.example.logincustomer.ui.QLphong_tam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.DAO.PhongTroDAO;
import com.example.logincustomer.R;

public class AddPhongActivity extends AppCompatActivity {
    Button huy, luu;
    EditText tenphong, giaphong;
    private PhongTroDAO phongTroDAO; // thêm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_dialog_themphongnho);

        huy = findViewById(R.id.btnHuy_themPhong);
        luu = findViewById(R.id.btnLuu_themPhong);
        tenphong = findViewById(R.id.edtTenPhong_themphong);
        giaphong = findViewById(R.id.edtGiaPhong_themphong);

        // khởi tạo DAO
        phongTroDAO = new PhongTroDAO(this);

        huy.setOnClickListener(v -> finish());

        luu.setOnClickListener(v -> {
            String ten = tenphong.getText().toString().trim();
            String giaStr = giaphong.getText().toString().trim();

            if (ten.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // CHECK TRÙNG TÊN PHÒNG
            if (phongTroDAO.isPhongExists(ten)) {
                Toast.makeText(this, "Tên phòng đã tồn tại. Vui lòng chọn tên khác.", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia;
            try {
                gia = Integer.parseInt(giaStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá phòng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("tenPhong", ten);
            intent.putExtra("giaPhong", gia);
            setResult(RESULT_OK, intent);

            Toast.makeText(this, "Tạo thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}