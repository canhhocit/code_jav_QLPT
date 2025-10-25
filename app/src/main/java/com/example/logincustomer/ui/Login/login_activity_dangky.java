package com.example.logincustomer.ui.Login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.account_DAO;
import com.example.logincustomer.data.Model.Account;

import java.util.List;

public class login_activity_dangky extends AppCompatActivity {
    private EditText edt_name, edt_pass, edt_acp_pass;
    private Button btn_dangky,btn_huy;
    private account_DAO accDAO;
    private Context context = login_activity_dangky.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity_dangky);
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
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_name.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();
                String acp_pass = edt_acp_pass.getText().toString().trim();

                if (username.isEmpty() || pass.isEmpty() || acp_pass.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(acp_pass)) {
                    Toast.makeText(context, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Account> list = accDAO.getAllAccount();
                for (Account acc : list) {
                    if (acc.getUsername().equalsIgnoreCase(username)) {
                        Toast.makeText(context, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Account newAcc = new Account();
                newAcc.setUsername(username);
                newAcc.setPass(pass);
                accDAO.insertAccount(newAcc);

                Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
                edt_name.setText("");
                edt_pass.setText("");
                edt_acp_pass.setText("");
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void anhxa() {
        edt_name = findViewById(R.id.dangky_edt_username);
        edt_pass = findViewById(R.id.dangky_edt_mk);
        edt_acp_pass = findViewById(R.id.dangky_edt_xacnhanmk);
        btn_dangky = findViewById(R.id.dangky_btn_dangky);
        btn_huy = findViewById(R.id.dangky_btn_huy);
    }
}