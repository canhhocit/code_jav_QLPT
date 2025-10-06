package com.example.logincustomer.ui.QLbaocao_canh;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

public class baocao_activity_homedsthuchi extends AppCompatActivity {
    private TextView tvTitle;
    private EditText edt_timkiem;
    private ListView lv_thuchi;
    private ImageButton imgbtn_Them;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activity_home_dsthuchi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maindsthuchi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxaID();
        back_homebc();
        goto_chitietthuchi();
    }

    private void goto_chitietthuchi() {
        imgbtn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baocao_activity_homedsthuchi.this, baocao_activity_chitietthuchi.class);
                intent.putExtra("check",1);
                startActivity(intent);
            }
        });
    }

    private void back_homebc() {
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxaID() {
        tvTitle = findViewById(R.id.baocao_dsthuchi_tvTitle);
        edt_timkiem = findViewById(R.id.khachthue_edt_timkiem);
        lv_thuchi = findViewById(R.id.baocao_listv_dsthuchi);
        imgbtn_Them = findViewById(R.id.baocao_thuchi_imgthem);
    }
}
