package com.example.logincustomer.ui.Account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.AccountAdapter;
import com.example.logincustomer.data.DAO.account_DAO;
import com.example.logincustomer.data.Model.Account;

import java.util.ArrayList;
import java.util.List;

public class account_activity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnThem;
    private ListView lvUser;

    private List<Account> accountList;
    private AccountAdapter adapter;
    private account_DAO accountDao;

    // Biến hỗ trợ sửa
    private boolean isEditing = false;
    private int editPosition = -1;
    private int editingId = -1; // lưu id khi sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        anhxa();

        accountDao = new account_DAO(this);

        // 🔹 Lấy toàn bộ tài khoản từ DB
        accountList = new ArrayList<>(accountDao.getAllAccount());

        // 🔹 Gắn adapter vào ListView
        adapter = new AccountAdapter(this, R.layout.layout_account_item, accountList, this);
        lvUser.setAdapter(adapter);

        // 🔹 Xử lý khi nhấn nút "Thêm" hoặc "Lưu sửa"
        btnThem.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditing) {
                // ✅ Cập nhật tài khoản
                Account acc = new Account(editingId, username, password);
                accountDao.updateAccount(acc);

                Toast.makeText(this, "Đã cập nhật tài khoản", Toast.LENGTH_SHORT).show();

                // Reset trạng thái
                isEditing = false;
                editPosition = -1;
                editingId = -1;
                btnThem.setText("Xác nhận");
            } else {
                // ✅ Thêm tài khoản mới
                Account acc = new Account(0, username, password);
                accountDao.insertAccount(acc);
                Toast.makeText(this, "Đã thêm tài khoản: " + username, Toast.LENGTH_SHORT).show();
            }

            // 🔹 Cập nhật lại danh sách sau khi thêm/sửa
            refreshList();

            // 🔹 Xóa dữ liệu nhập
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

    // 🔹 Hàm được Adapter gọi khi chọn "Sửa"
    public void loadDataToEdit(Account account, int position) {
        edtUsername.setText(account.getUsername());
        edtPassword.setText(account.getPass());
        isEditing = true;
        editPosition = position;
        editingId = account.getId(); // lưu id để update DB
        btnThem.setText("Lưu sửa");
    }

    // 🔹 Hàm load lại dữ liệu sau khi thêm / sửa / xóa
    private void refreshList() {
        accountList.clear();
        accountList.addAll(accountDao.getAllAccount());
        adapter.notifyDataSetChanged();
    }
}
