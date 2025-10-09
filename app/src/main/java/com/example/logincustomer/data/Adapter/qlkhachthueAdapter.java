    package com.example.logincustomer.data.Adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.TextView;

    import com.example.logincustomer.data.DAO.qlkhachthue_khachthueDAO;
    import com.example.logincustomer.data.Model.khachthue;
    import com.example.logincustomer.R;

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

            String tenphong = ktDAO.getTenphongbyID(khach.getIdphong());

            tvHoTen.setText(khach.getHoten());
            tvGioiTinh.setText(khach.getGioitinh());
            tvNgaySinh.setText(khach.getNgaysinh());
            tvPhong.setText(tenphong != null ? tenphong : "Chưa có phòng");

            return convertView;
        }
    }
