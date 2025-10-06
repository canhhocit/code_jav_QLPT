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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.baocao_thuchiDAO;
import com.example.logincustomer.data.Model.baocao_thuchi;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;

import java.util.Calendar;

public class baocao_activity_chitietthuchi extends AppCompatActivity {
    private TextView tvtitle;
    private EditText edtTenThuChi, edtSoTien, edtNgayThuChi;
    private RadioGroup rdoGroupLoai;
    private Button btnSua, btnThem;
    private baocao_thuchiDAO thuchiDAO;

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
        thuchiDAO = new baocao_thuchiDAO(baocao_activity_chitietthuchi.this);
        them();
        sua();
    }

    private void sua() {
        Intent intent = getIntent();

        int idthuchi = intent.getIntExtra("idthuchi", -1);
        String ten = intent.getStringExtra("tenthuchi");
        double sotien = intent.getDoubleExtra("sotien", 0);
        String ngay = intent.getStringExtra("ngaythuchi");
        String loai = intent.getStringExtra("loai");

        edtTenThuChi.setText(ten);
        edtSoTien.setText(String.valueOf(sotien));
        edtNgayThuChi.setText(ngay);
        if (loai != null) {
            if (loai.equalsIgnoreCase("Thu")) {
                rdoGroupLoai.check(R.id.baocao_rdo_thu);
            } else if (loai.equalsIgnoreCase("Chi")) {
                rdoGroupLoai.check(R.id.baocao_rdo_chi);
            }
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getdatafromText() == null) {
                    return;
                }
                baocao_thuchi thuchi = getdatafromText();
                thuchi.setIdthuchi(idthuchi); 

                int result = thuchiDAO.updateThuchi(thuchi);
                if (result > 0) {
                    Toast.makeText(baocao_activity_chitietthuchi.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(baocao_activity_chitietthuchi.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }


    private void them() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getdatafromText()==null){
                    return;
                }
                thuchiDAO.insertThuchi(getdatafromText());
                Toast.makeText(baocao_activity_chitietthuchi.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
    private baocao_thuchi getdatafromText(){
        String ten = edtTenThuChi.getText().toString().trim();
        String sotienStr = edtSoTien.getText().toString().trim();
        String ngay = edtNgayThuChi.getText().toString().trim();

        int selectedId = rdoGroupLoai.getCheckedRadioButtonId();
        String loai = "";
        if (selectedId == R.id.baocao_rdo_thu) {
            loai = "Thu";
        } else if (selectedId == R.id.baocao_rdo_chi) {
            loai = "Chi";
        }

        if (ten.isEmpty() || sotienStr.isEmpty() || ngay.isEmpty() || loai.isEmpty()) {
            new androidx.appcompat.app.AlertDialog.Builder(baocao_activity_chitietthuchi.this)
                    .setTitle("Lưu ý")
                    .setIcon(R.drawable.warning_img)
                    .setMessage("Vui lòng nhập đầy đủ thông tin!")
                    .setPositiveButton("OK", null)
                    .show();
            return null;
        }
        double sotien = Double.parseDouble(sotienStr);
        return new baocao_thuchi(ten,sotien,ngay,loai);
    }

    private void checkbtn_getdata() {
        Intent intent=getIntent();
        int check = intent.getIntExtra("check",0);
        if(check==1){
            btnThem.setVisibility(View.VISIBLE);
        }else if(check ==2){
            btnSua.setVisibility(View.VISIBLE);
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
