package com.example.logincustomer.data.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logincustomer.data.DAO.qlkhachthue_khachthueDAO;
import com.example.logincustomer.data.Model.khachthue;
import com.example.logincustomer.R;
import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;

import java.util.List;

public class qlkhachthueAdapter extends BaseAdapter {

    private Context context;
    private List<khachthue> listkhachthue;
    private qlkhachthue_khachthueDAO ktDAO;

    public qlkhachthueAdapter(Context context, List<khachthue> listkhachthue, qlkhachthue_khachthueDAO ktDAO) {
        this.context = context;
        this.listkhachthue = listkhachthue;
        this.ktDAO = ktDAO;
    }

    @Override
    public int getCount() {
        return listkhachthue.size();
    }

    @Override
    public Object getItem(int position) {
        return listkhachthue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listkhachthue.get(position).getIdkhach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.qlkhachthue_itemlistview, parent, false);
        }

        khachthue khach = listkhachthue.get(position);

        TextView tvHoTen = convertView.findViewById(R.id.khachthue_tv_hotenlv);
        TextView tvGioiTinh = convertView.findViewById(R.id.khachthue_tv_gioitinglv);
        TextView tvNgaySinh = convertView.findViewById(R.id.khachthue_tv_ngaysinhlv);
        TextView tvPhong = convertView.findViewById(R.id.khachthue_tv_phonglv);
        ImageView imgopt = convertView.findViewById(R.id.khachthue_img_option);

        imgopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imgopt);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_qlkhach, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.menu_qlkhach_sua) {
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
                        } else if (itemId == R.id.menu_qlkhach_xoa) {
                            int nguoi=ktDAO.SoNguoiTrongPhong(khach.getIdphong());
                            if(nguoi==1){
                                new AlertDialog.Builder(context)
                                        .setTitle("Xóa khách thuê")
                                        .setMessage("Phòng này chỉ còn 1 người, bạn có muốn hủy hợp đồng không?")
                                        .setPositiveButton("Xóa", (dialog, which) -> {
                                            ktDAO.deleteKhachThue(khach.getIdkhach());
                                            //xoa luon HD
                                            ktDAO.deleteHD_Nguoi(khach.getIdphong(), khach.getIdkhach());
                                            listkhachthue.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Đã xóa khách thuê", Toast.LENGTH_SHORT).show();
                                        })
                                        .setNegativeButton("Hủy", null)
                                        .show();
                                return true;
                            }else{
                                new AlertDialog.Builder(context)
                                        .setTitle("Xóa khách thuê")
                                        .setMessage("Bạn có chắc muốn xóa khách này không?")
                                        .setPositiveButton("Xóa", (dialog, which) -> {
                                            ktDAO.deleteKhachThue(khach.getIdkhach());
                                            listkhachthue.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Đã xóa khách thuê", Toast.LENGTH_SHORT).show();
                                        })
                                        .setNegativeButton("Hủy", null)
                                        .show();
                                return true;
                            }

                        } else if (itemId == R.id.menu_qlkhach_xemct) {
                            Intent intent = new Intent(context, qlkhachthue_activity_chucnang.class);
                            intent.putExtra("check", 5);
                            intent.putExtra("hoten", khach.getHoten());
                            intent.putExtra("gioitinh", khach.getGioitinh());
                            intent.putExtra("ngaysinh", khach.getNgaysinh());
                            intent.putExtra("diachi", khach.getDiachi());
                            intent.putExtra("sdt", khach.getSdt());
                            intent.putExtra("idphong", khach.getIdphong());
                            context.startActivity(intent);
                            return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        String tenphong = ktDAO.getTenphongbyID(khach.getIdphong());
        tvHoTen.setText(khach.getHoten());
        tvGioiTinh.setText(khach.getGioitinh());
        tvNgaySinh.setText(khach.getNgaysinh());
        tvPhong.setText(tenphong != null ? tenphong : "Chưa có phòng");

        return convertView;
    }
}
