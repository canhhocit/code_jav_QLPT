package com.example.logincustomer.ui.Login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;

public class login_activity_login extends AppCompatActivity {
    private TextView tv_quenmk, tv_dangky;
    private EditText edt_username, edt_pass;
    private Button btn_dangnhap;
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

    }

    private void anhxa() {
        tv_quenmk = findViewById(R.id.login_tv_quenmk);
        tv_dangky = findViewById(R.id.login_tv_dangky);
        edt_username = findViewById(R.id.login_edt_username);
        edt_pass = findViewById(R.id.login_edt_pass);
        btn_dangnhap = findViewById(R.id.login_btnLogin);
    }
}