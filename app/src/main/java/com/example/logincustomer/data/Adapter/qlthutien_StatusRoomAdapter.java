package com.example.logincustomer.data.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.Model.qlthutien_HoaDon;
import com.example.logincustomer.ui.QLthutien_nguyen.BillRoomActivity;
import com.example.logincustomer.ui.QLthutien_nguyen.SuaHoaDonActivity;

import java.text.DecimalFormat;
import java.util.List;

public class qlthutien_StatusRoomAdapter extends RecyclerView.Adapter<qlthutien_StatusRoomAdapter.ViewHolder> {

    private Context context;
    private List<qlthutien_HoaDon> hoaDonList;
    private OnItemClickListener listener;
    private qlphongtro_PhongTroDAO phongTroDAO;
    private qlthutien_HoaDonDAO qlthutienHoaDonDAO;


    // Interface để truyền sự kiện click
    public interface OnItemClickListener {
        void onItemClick(qlthutien_HoaDon hoaDon);
        void onMenuClick(qlthutien_HoaDon hoaDon, View view);
    }

    public qlthutien_StatusRoomAdapter(Context context, List<qlthutien_HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        phongTroDAO = new qlphongtro_PhongTroDAO(context);
        qlthutienHoaDonDAO = new qlthutien_HoaDonDAO(context);
    }

    @NonNull
    @Override
    public qlthutien_StatusRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qlthutien_item_statusroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull qlthutien_StatusRoomAdapter.ViewHolder holder, int position) {
        qlthutien_HoaDon hoaDon = hoaDonList.get(position);

        holder.tvNgay.setText(hoaDon.getNgaytaohdon());
        String tenphong = phongTroDAO.getTenPhongById(hoaDon.getIdphong());
        holder.tvPhong.setText(tenphong);

        DecimalFormat df = new DecimalFormat("#,###");
        holder.tvSoTien.setText(df.format(hoaDon.getTongtien()) + " đ");

        // Đặt icon trạng thái
        if (hoaDon.isTrangthai()) {
            holder.imgTrangThai.setImageResource(R.drawable.img_correct); // icon đã thanh toán
        } else {
            holder.imgTrangThai.setImageResource(R.drawable.img_wrong); // icon chưa thanh toán
        }

        // Sự kiện click vào nút menu ⋮
        holder.iconMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.getMenuInflater().inflate(R.menu.menu_item_dshoadon, popup.getMenu());

            int check = qlthutienHoaDonDAO.kiemTraTinhTrangHoaDon(hoaDon.getIdphong());

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_tt) {
                    boolean dathanhtoan = hoaDon.isTrangthai();
                    if (dathanhtoan) {
                        Toast.makeText(context, "Hóa đơn này đã thanh toán!", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Thanh toán")
                                .setMessage("Xác nhận thanh toán!")
                                .setPositiveButton("Thanh toán", (dialog, which) -> {
                                    qlthutienHoaDonDAO.updateTrangThai(hoaDon.getIdhoadon(), true);
                                    hoaDon.setTrangthai(true);
                                    notifyItemChanged(position);
                                    Toast.makeText(context, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                        return true;
                    }
                } else if (itemId == R.id.menu_edit) {
                    // ✏️ Sửa hóa đơn (nếu có)
                    if (!qlthutienHoaDonDAO.coTheSuaHoaDon(hoaDon.getIdphong())) {
                        Toast.makeText(context, "Hóa đơn này đã thanh toán, không thể sửa!", Toast.LENGTH_SHORT).show();
                        return true;
                    }else {
                        //code sửa
                        Intent intent = new Intent(context, SuaHoaDonActivity.class);
                        intent.putExtra("check", 2);
                        intent.putExtra("idhoadon", hoaDon.getIdhoadon());
                        context.startActivity(intent);
                    }
                    return true;
                } else if (itemId == R.id.menu_xem) {
                        Intent intent = new Intent(context, BillRoomActivity.class);
                        intent.putExtra("idhoadon", hoaDon.getIdhoadon());
                        context.startActivity(intent);
                        return true;

                } else if (itemId == R.id.menu_deletehd) {

                    new AlertDialog.Builder(context)
                            .setTitle("Xóa hóa đơn")
                            .setMessage("Xác nhận xóa hóa đơn!")
                            .setPositiveButton("Xóa", (dialog, which) -> {

                                boolean state = qlthutienHoaDonDAO.trangthaihoadon(hoaDon.getIdhoadon());
                                if(state){
                                    qlthutienHoaDonDAO.deleteHoaDon(hoaDon.getIdhoadon());
                                    hoaDonList.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xóa hóa đơn!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Hóa đơn này chưa thanh toán, không thể xóa!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList != null ? hoaDonList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgay, tvPhong, tvSoTien;
        ImageView imgTrangThai, iconMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgay = itemView.findViewById(R.id.txtItemDate);
            tvPhong = itemView.findViewById(R.id.txtItemRoom);
            tvSoTien = itemView.findViewById(R.id.txtItemPrice);
            imgTrangThai = itemView.findViewById(R.id.imgItemStatus);
            iconMenu = itemView.findViewById(R.id.iconMoreOption);
        }
    }
    public void setHoaDonList(List<qlthutien_HoaDon> list) {
        this.hoaDonList.clear();
        this.hoaDonList.addAll(list);
        notifyDataSetChanged();
    }


}

