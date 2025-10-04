package com.example.logincustomer.QLphong_tam;

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

import com.example.logincustomer.DAO.HoaDonDAO;
import com.example.logincustomer.Model.PhongTro;
import com.example.logincustomer.QLthutien_nguyen.BillRoomActivity;
import com.example.logincustomer.QLthutien_nguyen.TaoHoaDonActivity;
import com.example.logincustomer.R;

import java.util.List;

public class PhongTroAdapter extends BaseAdapter {
    private Context context;
    private List<PhongTro> list;
    private LayoutInflater inflater;

    HoaDonDAO hoaDonDAO;
    public PhongTroAdapter(Context context, List<PhongTro> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        hoaDonDAO = new HoaDonDAO(context);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return list.get(i).getIdphong(); }



    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.qlphongtro_item_dsphong, parent, false);
        }

        TextView txtPhong = convertView.findViewById(R.id.txtPhong_itemdsphong);
        TextView txtSoNguoi = convertView.findViewById(R.id.txtSoNguoi_itemdsphong);
        TextView txtGia = convertView.findViewById(R.id.txtGiaPhong_itemdsphong);
        TextView txtXemChiTiet = convertView.findViewById(R.id.txtXemChiTiet_itemdsphong);
        ImageView iconMenu = convertView.findViewById(R.id.iconXoaphong_itemdsphong);

        PhongTro pt = list.get(i);

        txtPhong.setText(pt.getTenphong());
        txtSoNguoi.setText(String.valueOf(pt.getSonguoi()));
        txtGia.setText(String.valueOf(pt.getGia()));

        // Khi nhấn "xem"
        txtXemChiTiet.setOnClickListener(v -> {
            Intent intent = new Intent(context, BillRoomActivity.class);
            intent.putExtra("idPhong", pt.getIdphong());
            context.startActivity(intent);
        });

        // Khi nhấn "..."
        iconMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.getMenuInflater().inflate(R.menu.menu_item_dsphong, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_add) {
                    // Mở activity tạo hóa đơn
                    Intent intent = new Intent(context, TaoHoaDonActivity.class);
                    intent.putExtra("idPhong", pt.getIdphong());
                    context.startActivity(intent);
                    return true;

                } else if (item.getItemId() == R.id.menu_edit) {
                    // Kiểm tra có hóa đơn chưa
                    // Nếu có → mở activity sửa hóa đơn
                    // Nếu chưa → thông báo
                    if (hoaDonDAO.coHoaDonChoPhong(pt.getIdphong())) {
                        Intent intent = new Intent(context, TaoHoaDonActivity.class);
                        intent.putExtra("idPhong", pt.getIdphong());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Phòng này chưa có hóa đơn để sửa!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            });

            popup.show();
        });

        return convertView;
    }

    public void updateList(List<PhongTro> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
}

