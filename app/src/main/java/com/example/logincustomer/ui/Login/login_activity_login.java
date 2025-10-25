package com.example.logincustomer.ui.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.account_DAO;
import com.example.logincustomer.data.Model.Account;
import com.example.logincustomer.ui.Manager_Home.activity_manager;

import java.util.List;

public class login_activity_login extends AppCompatActivity {
    private TextView tv_quenmk, tv_dangky;
    private EditText edt_name, edt_pass;
    private Button btn_dangnhap;
    private account_DAO accDAO;
    private Context context = login_activity_login.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity_login);
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
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_name.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();
                if (username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Account> list = accDAO.getAllAccount();
                for (Account acc : list) {
                    if (acc.getUsername().equalsIgnoreCase(username) && acc.getPass().equalsIgnoreCase(pass)) {
                        Intent intent = new Intent(context, activity_manager.class );
                        startActivity(intent);
                        Toast.makeText(context, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        new AlertDialog.Builder(context)
                                .setTitle("Thông báo")
                                .setMessage("Tên đăng nhập hoặc mật khẩu không hợp lệ!")
                                .setIcon(R.drawable.img_wrong)
                                .setPositiveButton("OK", null).show();
                    }
                }
            }
        });

        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, login_activity_dangky.class );
                startActivity(intent);
            }
        });
        tv_quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, login_activity_quenmk.class );
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        tv_quenmk = findViewById(R.id.login_tv_quenmk);
        tv_dangky = findViewById(R.id.login_tv_dangky);
        edt_name = findViewById(R.id.login_edt_username);
        edt_pass = findViewById(R.id.login_edt_pass);
        btn_dangnhap = findViewById(R.id.login_btnLogin);
    }
}