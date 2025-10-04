package com.example.logincustomer.QLphong_tam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

public class AddPhongActivity extends AppCompatActivity {

    Button huy, luu;
    EditText tenphong, giaphong;
    CheckBox phongtrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_dialog_themphongnho);

        huy = findViewById(R.id.btnHuy_themPhong);
        luu = findViewById(R.id.btnLuu_themPhong);
        tenphong = findViewById(R.id.edtTenPhong_themphong);
        giaphong = findViewById(R.id.edtGiaPhong_themphong);
        phongtrong = findViewById(R.id.checkbox_status_themPhong);

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}