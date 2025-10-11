package com.example.logincustomer.ui.QLbaocao_canh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.DAO.baocao_doanhthuDAO;
import com.example.logincustomer.data.Model.baocao_doanhthu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class baocao_activiity_bieudothuchi extends AppCompatActivity {
    private Context context = baocao_activiity_bieudothuchi.this;
    private TextView tvMenu, tvInfo;
    private BarChart chartThu, chartChi;
    private ListView lvNam;
    private baocao_doanhthuDAO dtDAO;
    private List<baocao_doanhthu> listDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.baocao_activiity_bieudothuchi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhXa();
        menu();
        listviewYear();
    }

    private void listviewYear() {
        dtDAO= new baocao_doanhthuDAO(context);
        List<Integer> years= dtDAO.getYears();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,years);
        lvNam.setAdapter(adapter);

        lvNam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String year = years.get(position)+"";
                showBarchartChi(year);
            }
        });
        showBarchartChi(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }
    private void showBarchartChi(String year){
        ArrayList<Double> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(0.0);
        }
        List<baocao_doanhthu> listBC_DT = dtDAO.getChibyYear(year);
        for(baocao_doanhthu x: listBC_DT){
            int thang=x.getThang()-1;
            Double tongchi= x.getTongchi();
            if(thang>=0 && thang <12){
                data.set(thang,tongchi);
            }
        }
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            entries.add(new BarEntry(i + 1, data.get(i).floatValue()));
        }
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu " + year);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i + 1 == currentMonth) {
                colors.add(Color.parseColor("#2196F3"));
            } else {
                colors.add(Color.parseColor("#FF5722"));
            }
        }
        dataSet.setColors(colors);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        chartChi.getAxisLeft().setAxisMinimum(0f);
        chartChi.getAxisRight().setEnabled(false);

        XAxis xAxis = chartChi.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chartChi.setData(barData);
        chartChi.setFitBars(true);
        chartChi.getDescription().setEnabled(false);
        chartChi.invalidate();

    }

    private void menu() {
        tvMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, tvMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_bieudothuchi, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_bieudothuchi_quaylai) {
                    finish();
                    return true;
                } else if (itemId == R.id.menu_bieudothuchi_xemchitiet) {
                    Intent intent = new Intent(context, baocao_activity_homedsthuchi.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_bieudothuchi_chonnam) {
                    if (lvNam.getVisibility() == View.INVISIBLE) {
                        lvNam.setVisibility(View.VISIBLE);
                    } else {
                        lvNam.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void anhXa() {
        tvMenu = findViewById(R.id.baocao_bieudothuchi_tvmenu);
        chartChi = findViewById(R.id.baocao_bieudochi);
        chartThu = findViewById(R.id.baocao_bieudothu);
        tvInfo = findViewById(R.id.baocao_bieudothuchi_tvinf);
        lvNam = findViewById(R.id.baocao_bieudothuchi_listviewnam);
        lvNam.setVisibility(View.VISIBLE);
        tvInfo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showBarchartChi(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }
}
