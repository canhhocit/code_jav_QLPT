package com.example.logincustomer.ui.QLbaocao_canh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

import java.util.Calendar;

public class baocao_activity_chitietthuchi extends AppCompatActivity {
    private TextView tvtitle;
    private EditText edtTenThuChi, edtSoTien, edtNgayThuChi;
    private RadioGroup rdoGroupLoai;
    private RadioButton rdoThu, rdoChi;
    private Button btnSuaQuayLai, btnSua, btnThemQuayLai, btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activity_chitietthuchi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainbaocao_chitietthuchi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxaid();
        back_dsthuchi();
        checkbtn_getdata();

    }


    private void checkbtn_getdata() {
        Intent intent=getIntent();
        int check = intent.getIntExtra("check",0);
        if(check==1){
            btnThem.setVisibility(View.VISIBLE);
        }
    }
    private void back_dsthuchi() {
        tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxaid() {
        tvtitle = findViewById(R.id.baocao_chitietthuchi_tvTitle);
        edtTenThuChi = findViewById(R.id.baocao_edt_tenthuchi);
        edtSoTien = findViewById(R.id.baocao_edt_sotien);
        edtNgayThuChi = findViewById(R.id.baocao_edt_ngaythuchi);

        rdoGroupLoai = findViewById(R.id.baocao_rdogroup);
        rdoThu = findViewById(R.id.baocao_rdo_thu);
        rdoChi = findViewById(R.id.baocao_rdo_chi);

        btnThem = findViewById(R.id.baocao_thuchi_btnthem);
        btnSua = findViewById(R.id.baocao_thuchi_btnsua);
        btnThem.setVisibility(View.INVISIBLE);
        btnSua.setVisibility(View.INVISIBLE);
        edtNgayThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(baocao_activity_chitietthuchi.this,
                        (view, year1, month1, dayOfMonth) ->
                                edtNgayThuChi.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                        y, m, d
                );
                dialog.show();
            }
        });

    }
}
