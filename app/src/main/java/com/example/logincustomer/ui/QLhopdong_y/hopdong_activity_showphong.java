package com.example.logincustomer.ui.QLhopdong_y;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincustomer.R;
import com.example.logincustomer.data.Adapter.qlhopdong_showphongAdapter;
import com.example.logincustomer.data.DAO.qlhopdong_hopdongDAO;
import com.example.logincustomer.data.Model.hopdong_ifRoom;
import com.example.logincustomer.ui.Manager_Home.activity_manager;

import java.util.List;

public class hopdong_activity_showphong extends AppCompatActivity {
    private TextView tvTitle;
    private ListView lvPhong;
    private Button btnDS;
    private qlhopdong_hopdongDAO hdDAO;
    private qlhopdong_showphongAdapter adapter;
    private List<hopdong_ifRoom> listPhong;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.qlhopdong_activity_showphong);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anhxa();
        loadListView();
        btnshowdsHD();
        listcontrol();
    }

    private void listcontrol() {
        lvPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hopdong_ifRoom infOnlist = listPhong.get(position);
                hopdong_ifRoom ifRoom = hdDAO.getinfRoombyID(infOnlist.getIdphong());
                if(ifRoom.getSonguoi()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");
                    builder.setIcon(R.drawable.img_wrong);
                    builder.setMessage("Phòng này chưa được ký hợp đồng, bạn có muốn ký hợp đồng với phòng này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, hopdong_activity_addPerson.class);
                            intent.putExtra("idphong", infOnlist.getIdphong());
                            intent.putExtra("tenphong", ifRoom.getTenphong());
                            startActivity(intent);
                        }

                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
                else{
                    Intent intent = new Intent(context, hopdong_activity_chucnang.class);
                    intent.putExtra("check",2);
                    intent.putExtra("idphong",infOnlist.getIdphong());
                    startActivity(intent);
                }
            }
        });
    }


    private void loadListView() {
        listPhong = hdDAO.getInfPhongTro();

        if (adapter == null) {
            adapter = new qlhopdong_showphongAdapter(context, hdDAO, listPhong);
            lvPhong.setAdapter(adapter);
        } else {
            adapter.updateData(listPhong);
        }
    }


    private void btnshowdsHD() {
        btnDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, hopdong_activity_dshopdong.class);
                startActivity(intent);
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_manager.class);
                startActivity(intent);
            }
        });
    }
    private void anhxa() {
        tvTitle = findViewById(R.id.hopdong_showp_tv);
        lvPhong = findViewById(R.id.hopdong_lvphong);
        btnDS = findViewById(R.id.hopdong_btn_xemds);
        hdDAO = new qlhopdong_hopdongDAO(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }
}
