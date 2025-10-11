package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.baocao_hopdongAdapter;
import com.example.logincustomer.data.Adapter.baocao_phongAdapter;
import com.example.logincustomer.data.DAO.baocao_doanhthuDAO;
import com.example.logincustomer.data.DAO.baocao_hopdongDAO;
import com.example.logincustomer.data.DAO.qlphongtro_PhongTroDAO;
import com.example.logincustomer.data.Model.PhongTro;
import com.example.logincustomer.data.Model.baocao_doanhthu;
import com.example.logincustomer.data.Model.baocao_hopdong;
import com.example.logincustomer.ui.QLhopdong_y.hopdong_activity_chucnang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class baocao_activity_homeBC extends AppCompatActivity {
    private TabHost tabHost;
    private TextView tvTitle_baocao;

    private Context context = baocao_activity_homeBC.this;
    // DT
    private BarChart barChartDT;
    private ListView lv_namDT;
    private Button btnThuChi;
    private baocao_doanhthuDAO dtDAO;

    // Phong
    private ListView lv_Phong;
    private qlphongtro_PhongTroDAO bc_ptDAO;
    private baocao_phongAdapter ptAdapter;
    //Hop dong
    private ListView lv_Hopdong;
    private baocao_hopdongDAO bc_hdDAO;
    private baocao_hopdongAdapter hdAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activity_home_baocao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainbc), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxaID();
        back_manager();
        //tab doanh thu
        gotoThuchi_startMenu();
        listviewYear();
        //tab phong
        listviewphong();

        //tab hop dong
        listviewHD();
        listHDcontrol();
    }
//DT
    private void listviewYear() {
        dtDAO= new baocao_doanhthuDAO(context);
        List<Integer> years= dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,years);
        lv_namDT.setAdapter(adapter);

        lv_namDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String year = years.get(position)+"";
                showBarchart(year);
            }
        });
        showBarchart(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }
    private void showBarchart(String year) {
        // 1️⃣ Mảng 12 tháng
        double[] tongHD = new double[12];
        double[] tongThu = new double[12];
        double[] tongChi = new double[12];
        double[] loiNhuan = new double[12];

        // 2️⃣ Lấy dữ liệu từ DAO
        List<baocao_doanhthu> listHD = dtDAO.getdtHDbyYear(year);
        List<baocao_doanhthu> listThu = dtDAO.getThubyYear(year);
        List<baocao_doanhthu> listChi = dtDAO.getChibyYear(year);

        // Gán dữ liệu từng loại vào mảng
        for (baocao_doanhthu x : listHD) {
            int thang = x.getThang() - 1;
            if (thang >= 0 && thang < 12) tongHD[thang] = x.getTongtienHD();
        }
        for (baocao_doanhthu x : listThu) {
            int thang = x.getThang() - 1;
            if (thang >= 0 && thang < 12) tongThu[thang] = x.getTongthu();
        }
        for (baocao_doanhthu x : listChi) {
            int thang = x.getThang() - 1;
            if (thang >= 0 && thang < 12) tongChi[thang] = x.getTongchi();
        }

        // 3️⃣ Tính lợi nhuận = Hóa đơn + Thu - Chi
        for (int i = 0; i < 12; i++) {
            loiNhuan[i] = tongHD[i] + tongThu[i] - tongChi[i];
        }

        // 4️⃣ Chuyển dữ liệu sang dạng BarEntry để hiển thị
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i + 1, (float) loiNhuan[i]));
        }

        // 5️⃣ Tạo DataSet cho biểu đồ
        BarDataSet dataSet = new BarDataSet(entries, "Lợi nhuận năm " + year);
        dataSet.setValueTextSize(10f);

        // Màu nổi bật cho tháng hiện tại
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (loiNhuan[i] < 0)
                colors.add(Color.parseColor("#F44336"));
            else if (i + 1 == currentMonth)
                colors.add(Color.parseColor("#2196F3"));
            else
                colors.add(Color.parseColor("#4CAF50"));
        }
        dataSet.setColors(colors);
        //dinh dang hienthi
        dataSet.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                String dau = value < 0 ? "-" : "";
                float giatri = Math.abs(value);

                if (giatri >= 1_000_000f) {
                    return dau + String.format("%.1f Triệu", giatri / 1_000_000f);
                } else if (giatri >= 1_000f) {
                    return dau + String.format("%.0f Nghìn", giatri / 1_000f);
                } else {
                    return dau + String.format("%.0f", giatri);
                }
            }
        });
        // 6️⃣ Tạo BarData và cài đặt cho BarChart
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barChartDT.setData(barData);
        barChartDT.setFitBars(true);

        // 7️⃣ Cấu hình trục
        XAxis xAxis = barChartDT.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setDrawGridLines(false);

        barChartDT.getAxisLeft().setAxisMinimum(-1000000f); // cho phép giá trị âm
        barChartDT.getAxisRight().setEnabled(false);
        barChartDT.getDescription().setEnabled(false);

        // 8️⃣ Cập nhật hiển thị
        barChartDT.invalidate();
    }

    //HD
    private void listHDcontrol() {
        lv_Hopdong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                baocao_hopdong bchd = (baocao_hopdong) hdAdapter.getItem(position);
                Intent intent = new Intent(context, hopdong_activity_chucnang.class);
                intent.putExtra("check",2);
                intent.putExtra("idphong",bchd.getIdp());
                startActivity(intent);
            }
        });
    }

    private void listviewHD() {
        bc_hdDAO = new baocao_hopdongDAO(context);
        List<baocao_hopdong> listHD = bc_hdDAO.getAllbc_hd();

        hdAdapter = new baocao_hopdongAdapter(context,listHD,bc_hdDAO);
        lv_Hopdong.setAdapter(hdAdapter);
    }


    private void listviewphong() {
        bc_ptDAO = new qlphongtro_PhongTroDAO(context);
        List<PhongTro> listPT = bc_ptDAO.getAllPhongTro();
        ptAdapter =new baocao_phongAdapter(context,listPT, bc_ptDAO);
        lv_Phong.setAdapter(ptAdapter);
    }


    private void gotoThuchi_startMenu() {
        btnThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, btnThuChi);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_btnthuchi, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.menu_btnthuchi_them){
                            Intent intent = new Intent(context, baocao_activity_chitietthuchi.class);
                            intent.putExtra("check",1);
                            startActivity(intent);
                            return true;
                        }else if(itemId==R.id.menu_btnthuchi_ds){
                            Intent intent = new Intent(context, baocao_activity_homedsthuchi.class);
                            startActivity(intent);
                            return true;
                        }else if(itemId==R.id.menu_btnthuchi_bieudo){
                        Intent intent = new Intent(context, baocao_activiity_bieudothuchi.class);
                        startActivity(intent);
                        return true;
                    }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void back_manager() {
        tvTitle_baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhxaID() {
        //chung
        tvTitle_baocao = findViewById(R.id.baocao_tvTitle);
        tabHost = findViewById(R.id.baocao_tabhost_DT);
        tabHost.setup();

        // 4 Tab
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("Doanh thu");
        tab1.setContent(R.id.tab_DT);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("Phòng");
        tab2.setContent(R.id.tab_phong);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4");
        tab4.setIndicator("Hợp đồng");
        tab4.setContent(R.id.tab_HD);
        tabHost.addTab(tab4);
        //set tab mac dinh
        tabHost.setCurrentTab(0);

        // Tab Doanh thu
        barChartDT = findViewById(R.id.baocao_barChartDT);
        lv_namDT = findViewById(R.id.baocao_doanhthu_listnamdt);
        btnThuChi = findViewById(R.id.baocao_btn_thuchi);

        // Tab Phòng
        lv_Phong = findViewById(R.id.baocao_phong_listview);


        // Tab Hợp đồng
        lv_Hopdong = findViewById(R.id.baocao_hopdong_listview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại DAO để lấy dữ liệu mới
        dtDAO = new baocao_doanhthuDAO(this);

        // Làm mới danh sách năm nếu có thêm năm mới
        List<Integer> years = dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, years);
        lv_namDT.setAdapter(adapter);
        // Hiển thị lại biểu đồ của năm hiện tại
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        showBarchart(currentYear);


        listviewphong();
        listviewHD();
    }

}
