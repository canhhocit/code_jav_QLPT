package com.example.logincustomer.ui.Login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.account_DAO;
import com.example.logincustomer.data.Model.Account;

import java.util.List;
import java.util.Random;

public class login_activity_quenmk extends AppCompatActivity {
    private Context context = login_activity_quenmk.this;
    private account_DAO accDAO ;

    private EditText edtUsername, edtMa, edtMKMoi, edtXacNhanMK;
    private Button btnGuiMa, btnXacNhan;
    private TextView tvTitle;
    private String maxacnhan="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity_quenmk);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        chucnang();
    }

    private void chucnang() {
        accDAO = new account_DAO(context);

        btnGuiMa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                List<Account> list = accDAO.getAllAccount();
                if (username.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean tonTai = false;
                for (Account acc : list) {
                    if (username.equalsIgnoreCase(acc.getUsername())) {
                        tonTai = true;
                        break;
                    }
                }

                if (!tonTai) {
                    new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Tên đăng nhập không tồn tại!")
                            .setIcon(R.drawable.img_wrong)
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    maxacnhan = String.format("%06d", new Random().nextInt(1000000));

                    new AlertDialog.Builder(context)
                            .setTitle("Mã xác nhận")
                            .setMessage("Mã xác nhận của bạn là: " + maxacnhan)
                            .setPositiveButton("OK", null)
                            .show();
                }

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String maNhap = edtMa.getText().toString().trim();
                String mkMoi = edtMKMoi.getText().toString().trim();
                String mkXacNhan = edtXacNhanMK.getText().toString().trim();

                if (username.isEmpty() || maNhap.isEmpty() || mkMoi.isEmpty() || mkXacNhan.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!maNhap.equals(maxacnhan)) {
                    Toast.makeText(context, "Mã xác nhận không chính xác!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mkMoi.equals(mkXacNhan)) {
                    Toast.makeText(context, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Account acc = new Account(username,mkXacNhan);
                accDAO.updateAccount(acc);
                new AlertDialog.Builder(context)
                            .setTitle("Thành công")
                            .setMessage("Đổi mật khẩu thành công!")
                            .setPositiveButton("OK", null)
                            .show();
                finish();

            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void anhxa() {
        edtUsername = findViewById(R.id.quenmk_edt_username);
        edtMa = findViewById(R.id.quenmk_edt_ma);
        edtMKMoi = findViewById(R.id.quenmk_edt_mkmoi);
        edtXacNhanMK = findViewById(R.id.quenmk_edt_xacnhanmk);
        btnGuiMa = findViewById(R.id.quenmk_btn_guima);
        btnXacNhan = findViewById(R.id.quenmk_btn_xacnhan);
        tvTitle = findViewById(R.id.login_tv0);
        
    }
}