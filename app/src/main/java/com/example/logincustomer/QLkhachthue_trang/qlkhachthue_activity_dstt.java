package com.example.logincustomer.QLkhachthue_trang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

public class qlkhachthue_activity_dstt extends AppCompatActivity {

    // 🔹 Khai báo biến toàn cục
    private EditText edtTK;
    private ListView listKT;
    private AppCompatButton btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlkhachthue_activity_dstt);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // anh xa id
        anhxaid();
        //cac ham
        buttonthem();
    }

    private void anhxaid() {
        edtTK = findViewById(R.id.khachthue_edt_timkiem);
        listKT = findViewById(R.id.khachthue_list_khachthue);
        btnThem = findViewById(R.id.khachthue_btn_themkhachthue);
    }

    private void buttonthem() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(qlkhachthue_activity_dstt.this, qlkhachthue_activity_chucnang.class);
                startActivity(intent);
            }
        });
    }
}
