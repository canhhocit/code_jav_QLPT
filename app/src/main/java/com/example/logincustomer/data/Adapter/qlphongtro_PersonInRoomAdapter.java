package com.example.logincustomer.data.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlkhachthue_khachthueDAO;
import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;


import java.util.List;

public class qlphongtro_PersonInRoomAdapter extends BaseAdapter {

    private Context context;
    private List<khachthue> list;
    private qlkhachthue_khachthueDAO dao;

    public qlphongtro_PersonInRoomAdapter(Context context, List<khachthue> list) {
        this.context = context;
        this.list = list;
        this.dao = new qlkhachthue_khachthueDAO(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdkhach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.qlphongtro_item_person_inroom, parent, false);
            holder = new ViewHolder();
            holder.txtTenKhach = convertView.findViewById(R.id.txtName_itemPersonInRoom);
            holder.txtSdt = convertView.findViewById(R.id.txtsdt_itemPersonInRoom);
            holder.txtGioiTinh = convertView.findViewById(R.id.txtSex_itemPersonInRoom);
            holder.txtNgaySinh = convertView.findViewById(R.id.txtNgaySinh_itemPersonInRoom);
            holder.txtDiaChi = convertView.findViewById(R.id.txtDiaChi_itemPersonInRoom);
            holder.iconMenu = convertView.findViewById(R.id.iconMoreOption_itemPersonInRoom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        khachthue khach = list.get(position);
        holder.txtTenKhach.setText(khach.getHoten());
        holder.txtSdt.setText(khach.getSdt());
        holder.txtGioiTinh.setText(khach.getGioitinh());
        holder.txtNgaySinh.setText(khach.getNgaysinh());
        holder.txtDiaChi.setText(khach.getDiachi());

        // 🟣 Khi nhấn vào biểu tượng "..."
        holder.iconMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.getMenuInflater().inflate(R.menu.menu_item_person_inroom, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_edit_personroom) {
                    // ✏️ Sửa thông tin khách thuê
                    Intent intent = new Intent(context, qlkhachthue_activity_chucnang.class);
                    intent.putExtra("check", 2);
                    intent.putExtra("idkhach", khach.getIdkhach());
                    intent.putExtra("hoten", khach.getHoten());
                    intent.putExtra("gioitinh", khach.getGioitinh());
                    intent.putExtra("ngaysinh", khach.getNgaysinh());
                    intent.putExtra("sdt", khach.getSdt());
                    intent.putExtra("diachi", khach.getDiachi());
                    intent.putExtra("idphong", khach.getIdphong());

                    context.startActivity(intent);
                    return true;

                } else if (itemId == R.id.menu_delete_personroom) {
                    // 🗑️ Xóa khách thuê
                    new AlertDialog.Builder(context)
                            .setTitle("Xóa khách thuê")
                            .setMessage("Bạn có chắc muốn xóa khách này không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                dao.deleteKhacThue(khach.getIdkhach());
                                list.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Đã xóa khách thuê", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                    return true;
                } else {
                    return false;
                }
            });
            popup.show();
        });
        return convertView;
    }
    private static class ViewHolder {
        TextView txtTenKhach, txtSdt, txtGioiTinh, txtNgaySinh, txtDiaChi;
        ImageView iconMenu;
    }

}
