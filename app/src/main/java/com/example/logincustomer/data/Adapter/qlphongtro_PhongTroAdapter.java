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

import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.DAO.qlthutien_HoaDonDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.ui.QLphong_tam.DetailInRoom;
import com.example.logincustomer.ui.QLphong_tam.qlphong_activity_home;
import com.example.logincustomer.ui.QLthutien_nguyen.BillRoomActivity;
import com.example.logincustomer.ui.QLthutien_nguyen.SuaHoaDonActivity;
import com.example.logincustomer.ui.QLthutien_nguyen.TaoHoaDonActivity;
import com.example.logincustomer.R;

import java.util.List;

public class qlphongtro_PhongTroAdapter extends BaseAdapter {
    private Context context;
    private List<PhongTro> list;
    private LayoutInflater inflater;
    private qlthutien_HoaDonDAO qlthutienHoaDonDAO;
    private qlphongtro_PhongTroDAO qlphongtroPhongTroDAO;

    private int selectedPosition = -1;

    public qlphongtro_PhongTroAdapter(Context context, List<PhongTro> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        qlthutienHoaDonDAO = new qlthutien_HoaDonDAO(context);
        qlphongtroPhongTroDAO = new qlphongtro_PhongTroDAO(context);
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
        ImageView iconMenu = convertView.findViewById(R.id.iconMoreOption_itemdsphong);

        PhongTro pt = list.get(i);

        txtPhong.setText(pt.getTenphong());
        txtSoNguoi.setText(String.valueOf(pt.getSonguoi()));
        txtGia.setText(String.valueOf(pt.getGia()));

        // Nếu là dòng được chọn thì tô màu
        if (i == selectedPosition) {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        convertView.setOnClickListener(v -> {
            selectedPosition = i; // Lưu dòng được chọn
            notifyDataSetChanged(); // Cập nhật lại màu hiển thị

            if (context instanceof qlphong_activity_home) {
                ((qlphong_activity_home) context).setPhongDangChon(pt);
            }
        });

        // Giữ lâu để xóa phòng
        convertView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Xóa phòng")
                    .setMessage("Bạn có chắc chắn muốn xóa phòng \"" + pt.getTenphong() + "\" không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Gọi DAO để xóa
                        qlphongtroPhongTroDAO = new qlphongtro_PhongTroDAO(context);
                        int result = qlphongtroPhongTroDAO.deletePhongTro(pt.getIdphong());
                        if (result > 0) {
                            list.remove(i);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa phòng!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi khi xóa phòng!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });

        // 🟡 Khi nhấn "Xem chi tiết"
        txtXemChiTiet.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailInRoom.class);
                intent.putExtra("idPhong", pt.getIdphong());
                context.startActivity(intent);
        });

        // 🟣 Khi nhấn vào biểu tượng "..."
        iconMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, v);
            popup.getMenuInflater().inflate(R.menu.menu_item_dsphong, popup.getMenu());

            int check = qlthutienHoaDonDAO.kiemTraTinhTrangHoaDon(pt.getIdphong());

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_add) {
                    // 🔍 Kiểm tra phòng đã có hóa đơn chưa
                    if (check == 1) {
                        Toast.makeText(context, "Phòng này có hóa đơn chưa thanh toán, không thể tạo thêm!", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (check == 2) {
                        Toast.makeText(context, "Phòng này đã có hóa đơn tháng này, không thể tạo thêm!", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    // ➕ Tạo hóa đơn mới
                    Intent intent = new Intent(context, TaoHoaDonActivity.class);
                    intent.putExtra("idPhong", pt.getIdphong());
                    intent.putExtra("giaphong", pt.getGia());
                    intent.putExtra("tenphong", pt.getTenphong());
                    intent.putExtra("songuoi",pt.getSonguoi());
                    context.startActivity(intent);
                    return true;

                } else if (itemId == R.id.menu_edit) {
                    // ✏️ Sửa hóa đơn (nếu có)
                    if (check == 0) {
                        Toast.makeText(context, "Phòng này chưa có hóa đơn để sửa!", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (!qlthutienHoaDonDAO.coTheSuaHoaDon(pt.getIdphong())) {
                        Toast.makeText(context, "Hóa đơn này đã thanh toán, không thể sửa!", Toast.LENGTH_SHORT).show();
                        return true;
                    }else{
                        //code sửa
                        int idhoadon = qlthutienHoaDonDAO.getNewestHoaDonIdByPhong(pt.getIdphong());
                        Intent intent = new Intent(context, SuaHoaDonActivity.class);
                        intent.putExtra("idhoadon", idhoadon);
                        context.startActivity(intent);
                    }


                    return true;
                } else if (itemId == R.id.menu_xem) {
                    //  xem hóa đơn (nếu có)
                    if (qlthutienHoaDonDAO.coHoaDonChoPhong(pt.getIdphong())) {
                        int idhoadon = qlthutienHoaDonDAO.getIdHoaDonByIdPhong(pt.getIdphong());
                        Intent intent = new Intent(context, BillRoomActivity.class);
                        intent.putExtra("idhoadon", idhoadon);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Phòng này chưa có hóa đơn!", Toast.LENGTH_SHORT).show();
                    }



                    return true;
                } else if (itemId == R.id.menu_deletephong) {
                    // 🗑️ Xóa phòng
                    new AlertDialog.Builder(context)
                            .setTitle("Xóa phòng")
                            .setMessage("Bạn có chắc chắn muốn xóa phòng \"" + pt.getTenphong() + "\" không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                // ⚠️ Kiểm tra hóa đơn chưa thanh toán
                                boolean hasUnpaid = qlthutienHoaDonDAO.hasUnpaidHoaDonByPhong(pt.getIdphong());

                                if (hasUnpaid) {
                                    Toast.makeText(context, "Không thể xóa! Phòng vẫn còn hóa đơn chưa thanh toán.", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                int result = qlphongtroPhongTroDAO.deletePhongTro(pt.getIdphong());
                                if (result > 0) {
                                    list.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xóa phòng!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Lỗi khi xóa phòng!", Toast.LENGTH_SHORT).show();
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
        return convertView;
    }
    public void updateList(List<PhongTro> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
}
