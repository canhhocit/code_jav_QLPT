package com.example.logincustomer.ui.Manager_Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.ui.Account.account_activity;
import com.example.logincustomer.ui.Login.login_activity_dangky;
import com.example.logincustomer.ui.QLbaocao_canh.baocao_activity_homeBC;
import com.example.logincustomer.ui.QLhopdong_y.hopdong_activity_showphong;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_dstt;
import com.example.logincustomer.ui.QLphong_tam.qlphong_activity_home;
import com.example.logincustomer.ui.QLthutien_nguyen.Guide;
import com.example.logincustomer.ui.QLthutien_nguyen.SetValueDefault;
import com.example.logincustomer.ui.QLthutien_nguyen.statusRoom;
import com.example.logincustomer.R;

public class activity_manager extends AppCompatActivity {
    ImageView imgPhong, imgKhach, imgThuTien, imgHopDong, imgBaoCao, imgtaikhoan,imgsetDefault, imgGuide ,imgdangxuat,imgthoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manager_activity_homemanager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainmanager), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgPhong   = findViewById(R.id.manager_imgPhong);
        imgKhach   = findViewById(R.id.manager_imgKhachThue);
        imgThuTien = findViewById(R.id.manager_imgThuTien);
        imgHopDong = findViewById(R.id.manager_imgHopDong);
        imgBaoCao  = findViewById(R.id.manager_imgBaoCao);
        imgtaikhoan = findViewById(R.id.manager_imgTaiKhoan);
        imgsetDefault = findViewById(R.id.manager_imgDefault);
        imgGuide = findViewById(R.id.manager_imgGuide);
        imgdangxuat = findViewById(R.id.manager_imgLogout);
        imgthoat = findViewById(R.id.manager_imgThoat);
        imgPhong.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, qlphong_activity_home.class);
            startActivity(intent);
        });

        imgKhach.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, qlkhachthue_activity_dstt.class);
            startActivity(intent);
        });

        imgHopDong.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, hopdong_activity_showphong.class);
            startActivity(intent);
        });

        imgBaoCao.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, baocao_activity_homeBC.class);
            startActivity(intent);
        });

        imgThuTien.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, statusRoom.class);
            startActivity(intent);
        });

        imgsetDefault.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, SetValueDefault.class);
            startActivity(intent);
        });

        imgGuide.setOnClickListener(v -> {
            Intent intent = new Intent(activity_manager.this, Guide.class);
            startActivity(intent);
        });

        imgdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_manager.this, login_activity_dangky.class);
                startActivity(intent);
            }
        });
        imgthoat.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity_manager.this);
            builder.setTitle("Xác nhận");
            builder.setIcon(R.drawable.warning_img);
            builder.setMessage("Bạn muốn thoát ứng dụng không");
            builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        });
        imgtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_manager.this, account_activity.class);
                startActivity(intent);
            }
        });
    }
}