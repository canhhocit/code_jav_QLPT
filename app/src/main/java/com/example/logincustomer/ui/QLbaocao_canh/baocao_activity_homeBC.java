package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.baocao_hopdongAdapter;
import com.example.logincustomer.data.DAO.baocao_doanhthuDAO;
import com.example.logincustomer.data.DAO.baocao_hopdongDAO;
import com.example.logincustomer.data.Model.baocao_doanhthu;
import com.example.logincustomer.data.Model.baocao_hopdong;
import com.example.logincustomer.ui.QLhopdong_y.hopdong_activity_chucnang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class baocao_activity_homeBC extends AppCompatActivity {
    private TabHost tabHost;
    private TextView tvTitle_baocao;
    private Context context = baocao_activity_homeBC.this;

    // Doanh thu
    private BarChart barChartDT;
    private ListView lv_namDT;
    private Button btnThuChi, btnFile;
    private baocao_doanhthuDAO dtDAO;
    private String selectedYear = "";

    // Hợp đồng
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

        // Tab doanh thu
        menuThuchi_menuFile();
        listviewYear();

        // Tab hợp đồng
        listviewHD();
        listHDcontrol();
    }

    //Doanh thu

    private void listviewYear() {
        dtDAO = new baocao_doanhthuDAO(context);
        List<Integer> years = dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, years);
        lv_namDT.setAdapter(adapter);

       //hien thi bieu do mac dinh nam hientai
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        selectedYear = String.valueOf(currentYear);
        showBarchart(selectedYear);

        // Xử lý click chọn năm
        lv_namDT.setOnItemClickListener((parent, view, position, id) -> {
            selectedYear = years.get(position).toString();
            showBarchart(selectedYear);
        });
    }

    private void showBarchart(String year) {

        double[] tongHD = new double[12];
        double[] tongThu = new double[12];
        double[] tongChi = new double[12];
        double[] loiNhuan = new double[12];

        List<baocao_doanhthu> listHD = dtDAO.getdtHDbyYear(year);
        List<baocao_doanhthu> listThu = dtDAO.getThubyYear(year);
        List<baocao_doanhthu> listChi = dtDAO.getChibyYear(year);

        for (baocao_doanhthu item : listHD) {
            int thang = item.getThang();
            if (thang >= 1 && thang <= 12) {
                tongHD[thang - 1] = item.getTongtienHD();
            }
        }
        for (baocao_doanhthu item : listThu) {
            int thang = item.getThang();
            if (thang >= 1 && thang <= 12) {
                tongThu[thang - 1] = item.getTongthu();
            }
        }
        for (baocao_doanhthu item : listChi) {
            int thang = item.getThang();
            if (thang >= 1 && thang <= 12) {
                tongChi[thang - 1] = item.getTongchi();
            }
        }

        for (int i = 0; i < 12; i++) {
            loiNhuan[i] = tongHD[i] + tongThu[i] - tongChi[i];
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i + 1, (float) loiNhuan[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Lợi nhuận năm " + year);
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.BLACK);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        boolean isCurrentYear = year.equals(String.valueOf(currentYear));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (loiNhuan[i] < 0) {
                colors.add(Color.parseColor("#F44336"));// ddor
            } else if (isCurrentYear && (i + 1) == currentMonth) {
                colors.add(Color.parseColor("#2196F3"));// tháng hiện tại
            } else {
                colors.add(Color.parseColor("#4CAF50"));// loinhuan>0
            }
        }
        dataSet.setColors(colors);


        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) {
                    return "";
                }

                String prefix = value < 0 ? "-" : "";
                float absValue = Math.abs(value);

                if (absValue >= 1_000_000f) {
                    return prefix + String.format("%.1fTr", absValue / 1_000_000f);
                } else if (absValue >= 100_000f) {
                    return prefix + String.format("%.0fK", absValue / 1_000f);
                } else {
                    return prefix + String.format("%.0f", absValue);
                }
            }
        });


        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.8f);


        XAxis xAxis = barChartDT.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(12.5f);
        xAxis.setLabelCount(12, false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10f);


        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "T" + (int) value;
            }
        });


        barChartDT.getAxisLeft().setDrawGridLines(true);
        barChartDT.getAxisLeft().setTextColor(Color.BLACK);
        barChartDT.getAxisLeft().setTextSize(10f);
        barChartDT.getAxisLeft().setDrawZeroLine(true);
        barChartDT.getAxisLeft().setZeroLineWidth(2f);
        barChartDT.getAxisLeft().setZeroLineColor(Color.GRAY);

        // Tự động điều chỉnh min/max dựa trên dữ liệu
        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;
        for (double val : loiNhuan) {
            if (val < minValue) minValue = (float) val;
            if (val > maxValue) maxValue = (float) val;
        }

        // Thêm buffer 10% cho đẹp
        float buffer = Math.abs(maxValue - minValue) * 0.1f;
        barChartDT.getAxisLeft().setAxisMinimum(minValue - buffer);
        barChartDT.getAxisLeft().setAxisMaximum(maxValue + buffer);

        barChartDT.getAxisRight().setEnabled(false);

        barChartDT.setData(barData);
        barChartDT.setFitBars(true);
        barChartDT.getDescription().setEnabled(false);
        barChartDT.getLegend().setEnabled(true);
        barChartDT.getLegend().setTextSize(12f);
        barChartDT.setDrawGridBackground(false);
        barChartDT.setDrawBorders(false);
        barChartDT.animateY(800);
        barChartDT.setExtraBottomOffset(10f);


        barChartDT.invalidate();

    }

    // ==================== PHẦN HỢP ĐỒNG ====================

    private void listHDcontrol() {
        lv_Hopdong.setOnItemClickListener((parent, view, position, id) -> {
            baocao_hopdong bchd = (baocao_hopdong) hdAdapter.getItem(position);
            Intent intent = new Intent(context, hopdong_activity_chucnang.class);
            intent.putExtra("check", 2);
            intent.putExtra("idphong", bchd.getIdp());
            startActivity(intent);
        });
    }

    private void listviewHD() {
        bc_hdDAO = new baocao_hopdongDAO(context);
        List<baocao_hopdong> listHD = bc_hdDAO.getAllbc_hd();
        hdAdapter = new baocao_hopdongAdapter(context, listHD, bc_hdDAO);
        lv_Hopdong.setAdapter(hdAdapter);
    }

    // ------MENU

    private void menuThuchi_menuFile() {
        btnThuChi.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, btnThuChi);
            popupMenu.getMenuInflater().inflate(R.menu.menu_option_btnthuchi, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_btnthuchi_them) {
                    Intent intent = new Intent(context, baocao_activity_chitietthuchi.class);
                    intent.putExtra("check", 1);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_btnthuchi_ds) {
                    Intent intent = new Intent(context, baocao_activity_homedsthuchi.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_btnthuchi_bieudo) {
                    Intent intent = new Intent(context, baocao_activiity_bieudothuchi.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        btnFile.setOnClickListener(v -> {
            if (selectedYear.isEmpty()) {
                Toast.makeText(context, "Vui lòng chọn năm trước!", Toast.LENGTH_SHORT).show();
                return;
            }

            PopupMenu popupMenu = new PopupMenu(context, btnFile);
            popupMenu.getMenuInflater().inflate(R.menu.menu_doanhthu_file, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_savefile) {
                    saveReportToFile();
                    return true;
                } else if (itemId == R.id.menu_deletefile) {
                    deleteReportFile();
                    return true;
                } else if (itemId == R.id.menu_openfile) {
                    openReportFile();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void saveReportToFile() {
        try {
            String suggestedName = "baocao_doanhthu_" + selectedYear + ".txt";
            final EditText input = new EditText(context);
            input.setText(suggestedName);

            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Đặt tên file báo cáo")
                    .setView(input)
                    .setPositiveButton("Lưu", (dialog, which) -> {
                        String fileName = input.getText().toString().trim();
                        if (!fileName.endsWith(".txt")) fileName += ".txt";

                        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File file = new File(downloads, fileName);

                        if (file.exists()) {
                            new androidx.appcompat.app.AlertDialog.Builder(context)
                                    .setTitle("Tệp đã tồn tại")
                                    .setMessage("File này đã tồn tại. Bạn có muốn ghi đè không?")
                                    .setPositiveButton("Ghi đè", (d, w) -> writeReportToFile(file, selectedYear))
                                    .setNegativeButton("Hủy", null)
                                    .show();
                        } else {
                            writeReportToFile(file, selectedYear);
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi khi lưu file!", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeReportToFile(File file, String year) {
        try {
            List<baocao_doanhthu> listHD = dtDAO.getdtHDbyYear(year);
            List<baocao_doanhthu> listThu = dtDAO.getThubyYear(year);
            List<baocao_doanhthu> listChi = dtDAO.getChibyYear(year);

            double[] tongHD = new double[12];
            double[] tongThu = new double[12];
            double[] tongChi = new double[12];
            double[] loiNhuan = new double[12];

            for (baocao_doanhthu x : listHD) {
                int thang = x.getThang();
                if (thang >= 1 && thang <= 12) tongHD[thang - 1] = x.getTongtienHD();
            }
            for (baocao_doanhthu x : listThu) {
                int thang = x.getThang();
                if (thang >= 1 && thang <= 12) tongThu[thang - 1] = x.getTongthu();
            }
            for (baocao_doanhthu x : listChi) {
                int thang = x.getThang();
                if (thang >= 1 && thang <= 12) tongChi[thang - 1] = x.getTongchi();
            }
            for (int i = 0; i < 12; i++) {
                loiNhuan[i] = tongHD[i] + tongThu[i] - tongChi[i];
            }

            StringBuilder content = new StringBuilder();
            content.append("\tBÁO CÁO DOANH THU NĂM ").append(year).append("\n");
            content.append("===========================================\n\n");

            for (int i = 0; i < 12; i++) {
                content.append("Tháng ").append(i + 1).append(":\n");
                content.append("  - Tổng thu phòng: ").append(String.format("%,d", (long) tongHD[i])).append(" VNĐ\n");
                content.append("  - Thu khác: ").append(String.format("%,d", (long) tongThu[i])).append(" VNĐ\n");
                content.append("  - Chi: ").append(String.format("%,d", (long) tongChi[i])).append(" VNĐ\n");
                content.append("  => Lợi nhuận: ").append(String.format("%,d", (long) loiNhuan[i])).append(" VNĐ\n\n");
            }

          //-----------
            double tongHDNam = 0, tongThuNam = 0, tongChiNam = 0;
            for (int i = 0; i < 12; i++) {
                tongHDNam += tongHD[i];
                tongThuNam += tongThu[i];
                tongChiNam += tongChi[i];
            }
            double loiNhuanNam = tongHDNam + tongThuNam - tongChiNam;

            content.append("========================================\n");
            content.append("TỔNG KẾT NĂM ").append(year).append(":\n");
            content.append("  - Tổng thu phòng: ").append(String.format("%,d", (long) tongHDNam)).append(" VNĐ\n");
            content.append("  - Tổng thu khác: ").append(String.format("%,d", (long) tongThuNam)).append(" VNĐ\n");
            content.append("  - Tổng chi: ").append(String.format("%,d", (long) tongChiNam)).append(" VNĐ\n");
            content.append("  => LỢI NHUẬN: ").append(String.format("%,d", (long) loiNhuanNam)).append(" VNĐ\n");

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.toString().getBytes());
            fos.close();

            Toast.makeText(context, "Đã lưu: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi khi ghi file!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openReportFile() {
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloads, "baocao_doanhthu_" + selectedYear + ".txt");

        if (!file.exists()) {
            Toast.makeText(context, "Không tìm thấy file báo cáo năm " + selectedYear, Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/plain");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Không có ứng dụng nào mở được file .txt!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteReportFile() {
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloads, "baocao_doanhthu_" + selectedYear + ".txt");

        if (!file.exists()) {
            Toast.makeText(context, "Năm " + selectedYear + " chưa lưu file nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Xóa file?")
                .setIcon(R.drawable.img_trash)
                .setMessage("Bạn có chắc muốn xóa \"" + file.getName() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (file.delete()) {
                        Toast.makeText(context, "Đã xóa " + file.getName(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Không thể xóa file!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void back_manager() {
        tvTitle_baocao.setOnClickListener(v -> finish());
    }

    private void anhxaID() {
        // Chung
        tvTitle_baocao = findViewById(R.id.baocao_tvTitle);
        tabHost = findViewById(R.id.baocao_tabhost_DT);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("Doanh thu");
        tab1.setContent(R.id.tab_DT);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4");
        tab4.setIndicator("Hợp đồng");
        tab4.setContent(R.id.tab_HD);
        tabHost.addTab(tab4);

        tabHost.setCurrentTab(0);

        // Tab Doanh thu
        barChartDT = findViewById(R.id.baocao_barChartDT);
        lv_namDT = findViewById(R.id.baocao_doanhthu_listnamdt);
        btnThuChi = findViewById(R.id.baocao_btn_thuchi);
        btnFile = findViewById(R.id.baocao_btn_file);

        // Tab Hợp đồng
        lv_Hopdong = findViewById(R.id.baocao_hopdong_listview);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dtDAO = new baocao_doanhthuDAO(this);

        List<Integer> years = dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, years);
        lv_namDT.setAdapter(adapter);

        // Hiển thị lại biểu đồ
        if (selectedYear.isEmpty()) {
            selectedYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        }
        showBarchart(selectedYear);

        listviewHD();
    }
}