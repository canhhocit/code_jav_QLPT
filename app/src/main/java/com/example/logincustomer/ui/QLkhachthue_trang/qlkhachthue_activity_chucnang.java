package com.example.logincustomer.ui.QLkhachthue_trang;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlkhachthue_khachthueDAO;
import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.ui.QLhopdong_y.hopdong_activity_chucnang;

import java.util.Calendar;

public class qlkhachthue_activity_chucnang extends AppCompatActivity {
    private ImageView imageBack;
    private EditText edtHoTen, edtNgaySinh, edtSDT, edtDiaChi, edtPhong;
    private RadioGroup rdgGioiTinh;
    private Button btnSua, btnXoa, btnThem;
    private qlkhachthue_khachthueDAO khachDAO;
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

        nhandulieu();
        them();
        Sua();
        Xoa();
        back_list();
    }

    private void Xoa() {
      btnXoa.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder builder = new AlertDialog.Builder(qlkhachthue_activity_chucnang.this);
              builder.setTitle("Lưu ý");
              builder.setIcon(R.drawable.warning_img);
              builder.setMessage("Xóa người này?");
              builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Intent intent = getIntent();
                      int idkhach = intent.getIntExtra("idkhach", -1);
                      if (idkhach != -1) {
                          khachDAO.deleteKhacThue(idkhach);
                          Toast.makeText(qlkhachthue_activity_chucnang.this, "Đã xóa khách thuê!", Toast.LENGTH_SHORT).show();
                          finish();
                      } else {
                          Toast.makeText(qlkhachthue_activity_chucnang.this, "Lỗi: không tìm thấy khách!", Toast.LENGTH_SHORT).show();
                      }
                  }
              });
              builder.setNegativeButton("Hủy",null);
              builder.show();

          }
      });

    }

    private void Sua() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= getIntent();
                int idkhach=intent.getIntExtra("idkhach",-1);
                khachthue kt = getdatafromText();
                if (kt == null) {
                    Toast.makeText(qlkhachthue_activity_chucnang.this, "Lỗi, hãy chọn khách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(khachDAO.checkexists(kt)>0){
                    Toast.makeText(qlkhachthue_activity_chucnang.this, "Thông tin này đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                kt.setIdkhach(idkhach);
                khachDAO.updateKhachThue(kt);
                Toast.makeText(qlkhachthue_activity_chucnang.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish(); // return dstt
            }
        });
    }

    private void back_list() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void nhandulieu() {
        Intent intent = getIntent();
        int check = intent.getIntExtra("check", 0);

        String tenPhong = intent.getStringExtra("tenphong");
        if (tenPhong != null) {
            edtPhong.setText(tenPhong);
        }

        if (check == 1) {
            btnThem.setVisibility(View.VISIBLE);
        } else if (check == 2) {
            btnSua.setVisibility(View.VISIBLE);
            btnXoa.setVisibility(View.VISIBLE);

            String hoten = intent.getStringExtra("hoten");
            String gioitinh = intent.getStringExtra("gioitinh");
            String ngaysinh = intent.getStringExtra("ngaysinh");
            String sdt = intent.getStringExtra("sdt");
            String diachi = intent.getStringExtra("diachi");
            int idphong = intent.getIntExtra("idphong", -1);

            edtHoTen.setText(hoten);
            edtNgaySinh.setText(ngaysinh);
            edtSDT.setText(sdt);
            edtDiaChi.setText(diachi);


            khachDAO = new qlkhachthue_khachthueDAO(this);
            String tenphongDB = khachDAO.getTenphongbyID(idphong);
            edtPhong.setText(tenphongDB);

            if ("Nam".equalsIgnoreCase(gioitinh)) {
                ((RadioButton) findViewById(R.id.khachthue_radioNam)).setChecked(true);
            } else if ("Nữ".equalsIgnoreCase(gioitinh)) {
                ((RadioButton) findViewById(R.id.khachthue_radioNu)).setChecked(true);
            }
        }
    }
    private void them() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachthue kt =getdatafromText();
                if(kt==null){
                    return;
                }
                if(khachDAO.checkCountperson(kt.getIdphong())==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(qlkhachthue_activity_chucnang.this);
                    builder.setTitle("Thêm thất bại");
                    builder.setIcon(R.drawable.img_wrong);
                    builder.setMessage("Phòng này chưa được ký hợp đồng, bạn có muốn ký hợp đồng với phòng này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(qlkhachthue_activity_chucnang.this, hopdong_activity_chucnang.class);
                            intent.putExtra("idphong",kt.getIdphong());
                            intent.putExtra("idkhach",kt.getIdkhach());
                            intent.putExtra("check",1);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                    return;
                }
                if(khachDAO.checkexists(kt)>0){
                    Toast.makeText(qlkhachthue_activity_chucnang.this, "Thông tin này đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                khachDAO.insertKhachThue(kt);
                Toast.makeText(qlkhachthue_activity_chucnang.this, "Thêm khách thuê thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private khachthue getdatafromText() {
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
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!khachDAO.checkIDbyTenphong(phong)) {
            Toast.makeText(this, "Phòng không tồn tại!", Toast.LENGTH_SHORT).show();
            return null;
        }

        int idphong = khachDAO.getIDbyTenphong(phong);
        return new khachthue(hoTen, gioiTinh, ngaySinh, sdt, diaChi, idphong);
    }

    private void anhXaID() {
        imageBack = findViewById(R.id.image_back);
        edtHoTen = findViewById(R.id.khachthue_edt_hoten);
        edtNgaySinh = findViewById(R.id.khachthue_edt_ngaysinh);
        edtSDT = findViewById(R.id.khachthue_edt_sdt);
        edtDiaChi = findViewById(R.id.khachthue_edt_diachi);
        edtPhong = findViewById(R.id.khachthue_edt_phong);

        rdgGioiTinh = findViewById(R.id.khachthue_rdogroup);

        btnSua = findViewById(R.id.khachthue_btnSua);
        btnXoa = findViewById(R.id.khachthue_btnXoa);
        btnThem = findViewById(R.id.khachthue_btnThem);
        btnThem.setVisibility(View.INVISIBLE);
        btnSua.setVisibility(View.INVISIBLE);
        btnXoa.setVisibility(View.INVISIBLE);
        khachDAO = new qlkhachthue_khachthueDAO(this);
        //click ngay
        edtNgaySinh.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    qlkhachthue_activity_chucnang.this,
                    (view, year1, month1, dayOfMonth) -> {
                        if (year1 > 2015) {
                            Toast.makeText(this, "Năm sinh phải lớn hơn 2015!", Toast.LENGTH_SHORT).show();
                        } else {
                            edtNgaySinh.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                        }
                    },
                    y, m, d
            );
            //k cho chon ngay>ngay htai
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();

        });
    }

}