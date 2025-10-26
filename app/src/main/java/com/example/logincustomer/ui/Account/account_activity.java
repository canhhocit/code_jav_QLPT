package com.example.logincustomer.ui.Account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imgback;
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

        accountList = new ArrayList<>(accountDao.getAllAccount());

        adapter = new AccountAdapter(this, R.layout.layout_account_item, accountList, this);
        lvUser.setAdapter(adapter);

        btnThem.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditing) {
                List<Account> list = accountDao.getAllAccount();
                boolean tonTai = false;
                for (Account acc : list) {
                    if (username.equalsIgnoreCase(acc.getUsername())) {
                        tonTai = true;
                        break;
                    }
                }
                if(tonTai){
                    Account acc = new Account(editingId, username, password);
                    accountDao.updateAccount(acc);

                    Toast.makeText(this, "Đã cập nhật tài khoản", Toast.LENGTH_SHORT).show();

                    isEditing = false;
                    editPosition = -1;
                    editingId = -1;
                    btnThem.setText("Xác nhận");
                }
                else{
                    Toast.makeText(this, "Tên tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                }

            } else {
                List<Account> list = accountDao.getAllAccount();
                for (Account acc : list) {
                    if (acc.getUsername().equalsIgnoreCase(username)) {
                        Toast.makeText(this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Account acc = new Account(0, username, password);
                if(accountDao.insertAccount(acc)>0){
                    Toast.makeText(this, "Đã thêm tài khoản: " + username, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }

            }

            refreshList();

            edtUsername.setText("");
            edtPassword.setText("");

            imgback.setOnClickListener(v1 ->{
                finish();
            });
        });
    }

    private void anhxa() {
        edtUsername = findViewById(R.id.acc_edtUsername);
        edtPassword = findViewById(R.id.acc_edtPassword);
        btnThem = findViewById(R.id.acc_btnThem);
        lvUser = findViewById(R.id.acc_lvUser);
        imgback = findViewById(R.id.img_arrowback_account);
    }


    public void loadDataToEdit(Account account, int position) {
        edtUsername.setText(account.getUsername());
        edtPassword.setText(account.getPass());
        isEditing = true;
        editPosition = position;
        editingId = account.getId(); // lưu id để update DB
        btnThem.setText("Cập nhật");
    }


    private void refreshList() {
        accountList.clear();
        accountList.addAll(accountDao.getAllAccount());
        adapter.notifyDataSetChanged();
    }
}
