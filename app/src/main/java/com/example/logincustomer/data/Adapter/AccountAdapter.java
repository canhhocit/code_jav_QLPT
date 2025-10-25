package com.example.logincustomer.data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.account_DAO;
import com.example.logincustomer.data.Model.Account;
import com.example.logincustomer.ui.Account.account_activity;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {

    private Context context;
    private List<Account> accountList;
    private account_activity activity;
    private account_DAO accountDao;

    public AccountAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects, account_activity activity) {
        super(context, resource, objects);
        this.context = context;
        this.accountList = objects;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_account_item, parent, false);
        }

        Account account = accountList.get(position);
        accountDao = new account_DAO(context);

        TextView txtUser = view.findViewById(R.id.acc_txt_user);
        TextView txtPassword = view.findViewById(R.id.acc_txt_password);
        ImageView iconMore = view.findViewById(R.id.acc_icon_moreOption);

        txtUser.setText(account.getUsername());
        txtPassword.setText(account.getPass());

        iconMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, iconMore);
            popup.getMenuInflater().inflate(R.menu.menu_account, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_edit) {
                    // Gọi hàm trong Activity để đưa dữ liệu lên EditText
                    activity.loadDataToEdit(account, position);
                    return true;
                } else if (id == R.id.menu_delete) {
                    accountList.remove(position);
                    accountDao.deleteAccount(account.getId());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa tài khoản " + account.getUsername(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popup.show();
        });

        return view;
    }
}
