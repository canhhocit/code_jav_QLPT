package com.example.logincustomer.ui.QLphong_tam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

public class UpdatePhongActivity extends AppCompatActivity {

    Button huy, luu;
    EditText tenphong, giaphong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlphongtro_dialog_suaphong);

        huy = findViewById(R.id.btnHuy_suaphong);
        luu = findViewById(R.id.btnLuu_suaphong);
        tenphong = findViewById(R.id.edt_TenPhong_suaphong);
        giaphong = findViewById(R.id.edt_giaPhong_suaphong);


        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
