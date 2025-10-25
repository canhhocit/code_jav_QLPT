package com.example.logincustomer.ui.Account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.AccountAdapter;
import com.example.logincustomer.data.Model.Account;

import java.util.ArrayList;

public class account_activity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnThem;
    private ListView lvUser;

    private ArrayList<Account> accountList;
    private AccountAdapter adapter;

    // Các biến hỗ trợ sửa
    private boolean isEditing = false;
    private int editPosition = -1;

    private int autoIncrementId = 1; // Dùng tạm khi chưa có SQLite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        anhxa();

        accountList = new ArrayList<>();
        adapter = new AccountAdapter(this, R.layout.layout_account_item, accountList, this);
        lvUser.setAdapter(adapter);

        // Khi nhấn nút "Xác nhận"
        btnThem.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditing) {
                // Cập nhật item đang sửa
                Account acc = accountList.get(editPosition);
                acc.setUsername(username);
                acc.setPass(password);
                adapter.notifyDataSetChanged();

                Toast.makeText(this, "Đã cập nhật tài khoản", Toast.LENGTH_SHORT).show();

                // Reset trạng thái về thêm mới
                isEditing = false;
                editPosition = -1;
                btnThem.setText("Xác nhận");
            } else {
                // Thêm mới
                Account acc = new Account(autoIncrementId++, username, password);
                accountList.add(acc);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã thêm tài khoản: " + username, Toast.LENGTH_SHORT).show();
            }

            // Xóa nội dung EditText
            edtUsername.setText("");
            edtPassword.setText("");
        });
    }

    private void anhxa() {
        edtUsername = findViewById(R.id.acc_edtUsername);
        edtPassword = findViewById(R.id.acc_edtPassword);
        btnThem = findViewById(R.id.acc_btnThem);
        lvUser = findViewById(R.id.acc_lvUser);
    }

    // Hàm được adapter gọi khi chọn "Sửa"
    public void loadDataToEdit(Account account, int position) {
        edtUsername.setText(account.getUsername());
        edtPassword.setText(account.getPass());
        isEditing = true;
        editPosition = position;
        btnThem.setText("Lưu sửa");
    }
}
