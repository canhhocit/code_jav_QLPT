    package com.example.logincustomer.ui.QLbaocao_canh;

    import static android.text.TextUtils.split;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.ListView;
    import android.widget.PopupMenu;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.example.logincustomer.R;
    import com.example.logincustomer.data.Adapter.baocao_thuchiAdapter;
    import com.example.logincustomer.data.DAO.baocao_thuchiDAO;
    import com.example.logincustomer.data.Model.baocao_thuchi;
    import com.example.logincustomer.ui.QLkhachthue_trang.qlkhachthue_activity_chucnang;

    import java.util.List;

    public class baocao_activity_homedsthuchi extends AppCompatActivity {

        private TextView tvTitle, tvMenu;
        private EditText edt_timkiem;
        private ListView lv_thuchi;
        private LinearLayout lnLoc;
        private RadioGroup rdogrLoai, rdogrMoney, rdogrDate;
        private RadioButton rdoThu, rdoChi, rdoMoneyASC, rdoMoneyDSC, rdoDateASC, rdoDateDSC;
        private baocao_thuchiDAO thuchiDAO;
        private baocao_thuchiAdapter thuchiAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.baocao_activity_home_dsthuchi);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maindsthuchi), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            anhxaID();
            //cac thao tac them,sua,xoa,timkiem
            back_homebc();
            checklist();
            menu();
            listviewThuchi();
            listcontrol();
            xoa();
            findByNameorByDate();
            //loc va sapxep
            loc();
            sapxep();

        }

        private void sapxep() {
            rdogrDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                    List<baocao_thuchi> list;
                    thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);

                    if (checkedId == R.id.baocao_dsthuchi_rdo_dateASC) {
                        list = thuchiDAO.orderbyDate("ASC");
                    } else if (checkedId == R.id.baocao_dsthuchi_rdo_dateDSC) {
                        list = thuchiDAO.orderbyDate("DESC");
                    } else {
                        list = thuchiDAO.getAllthuchi();
                    }

                    thuchiAdapter = new baocao_thuchiAdapter(baocao_activity_homedsthuchi.this, list, thuchiDAO);
                    lv_thuchi.setAdapter(thuchiAdapter);
                }
            });
            rdogrMoney.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                    List<baocao_thuchi> list;
                    thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);

                    if (checkedId == R.id.baocao_dsthuchi_rdo_moneyASC) {
                        list = thuchiDAO.orderbyMoney("ASC");
                    } else if (checkedId == R.id.baocao_dsthuchi_rdo_moneyDSC) {
                        list = thuchiDAO.orderbyMoney("DESC");
                    } else {
                        list = thuchiDAO.getAllthuchi();
                    }
                    thuchiAdapter = new baocao_thuchiAdapter(baocao_activity_homedsthuchi.this, list, thuchiDAO);
                    lv_thuchi.setAdapter(thuchiAdapter);
                }
            });
        }

        private void loc() {
            rdogrLoai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                    List<baocao_thuchi> list;
                    thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);

                    if (checkedId == R.id.baocao_dsthuchi_rdothu) {
                        list = thuchiDAO.searchByLoai("Thu");
                    } else if (checkedId == R.id.baocao_dsthuchi_rdochi) {
                        list = thuchiDAO.searchByLoai("Chi");
                    } else {
                        list = thuchiDAO.getAllthuchi();
                    }
                    thuchiAdapter = new baocao_thuchiAdapter(baocao_activity_homedsthuchi.this, list, thuchiDAO);
                    lv_thuchi.setAdapter(thuchiAdapter);
                }
            });
        }

        private void findByNameorByDate() {
            edt_timkiem.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    rdogrLoai.clearCheck();
                    rdogrDate.clearCheck();
                    rdogrMoney.clearCheck();

                    String keyw = s.toString().trim();
                    List<baocao_thuchi> list;
                    thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);
                    String[] str = keyw.split("/");
                    if (keyw.isEmpty()) {
                        list = thuchiDAO.getAllthuchi();
                    }
                    else if (str.length>1) {
                        list = thuchiDAO.searchByDate(keyw);
                    }
                    else {
                        list = thuchiDAO.searchByName(keyw);
                    }

                    thuchiAdapter = new baocao_thuchiAdapter(baocao_activity_homedsthuchi.this, list, thuchiDAO);
                    lv_thuchi.setAdapter(thuchiAdapter);
                }

            });
        }


        private void checklist() {
            thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);
            int check= thuchiDAO.chechlist();
            if(check ==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(baocao_activity_homedsthuchi.this);
                builder.setTitle("Thông báo");
                builder.setIcon(R.drawable.recommmend_img);
                builder.setMessage("Bạn chưa có mục thu chi nào, có muốn chuyển đến mục tạo thu chi không?");
                builder.setPositiveButton("Tạo thu chi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(baocao_activity_homedsthuchi.this, baocao_activity_chitietthuchi.class);
                        intent.putExtra("check",1);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.show();
            }
        }

        private void xoa() {
            lv_thuchi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    int idthuchi = (int) thuchiAdapter.getItemId(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(baocao_activity_homedsthuchi.this);
                    builder.setTitle("Lưu ý");
                    builder.setIcon(R.drawable.warning_img);
                    builder.setMessage("Xóa mục này?");
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            thuchiDAO.deleteThuchi(idthuchi);
                            Toast.makeText(baocao_activity_homedsthuchi.this, "Xóa thành công! ", Toast.LENGTH_SHORT).show();
                            listviewThuchi();checklist();
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
                    builder.show();
                    return true;
                }
            });
        }


        private void listviewThuchi() {
            thuchiDAO = new baocao_thuchiDAO(baocao_activity_homedsthuchi.this);
            List<baocao_thuchi> list = thuchiDAO.getAllthuchi();
            thuchiAdapter = new baocao_thuchiAdapter(baocao_activity_homedsthuchi.this,list,thuchiDAO);
            lv_thuchi.setAdapter(thuchiAdapter);
        }
        private void listcontrol() {
            lv_thuchi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    baocao_thuchi thuchi = (baocao_thuchi) thuchiAdapter.getItem(position);
                    Intent intent = new Intent(baocao_activity_homedsthuchi.this, baocao_activity_chitietthuchi.class);
                    intent.putExtra("check",2);
                    //sent data
                    intent.putExtra("idthuchi", thuchi.getIdthuchi());
                    intent.putExtra("tenthuchi", thuchi.getTenthuchi());
                    intent.putExtra("sotien", thuchi.getSotienthuchi());
                    intent.putExtra("ngaythuchi", thuchi.getNgaythuchi());
                    intent.putExtra("loai", thuchi.getLoaithuchi());

                    startActivity(intent);
                }
            });
        }
        private void menu() {
            tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(baocao_activity_homedsthuchi.this, tvMenu);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_option_thuchi, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int itemId = item.getItemId();
                            if (itemId == R.id.menu_thuchi_them) {
                                Intent intent = new Intent(baocao_activity_homedsthuchi.this, baocao_activity_chitietthuchi.class);
                                intent.putExtra("check",1);
                                startActivity(intent);
                            } else if (itemId == R.id.menu_thuchi_xembieudo) {
                                Intent intent = new Intent(baocao_activity_homedsthuchi.this, baocao_activiity_bieudothuchi.class);
                                startActivity(intent);
                            } else if (itemId == R.id.menu_thuchi_sua) {
                                Toast.makeText(baocao_activity_homedsthuchi.this, "Hãy click vào dòng muốn sửa", Toast.LENGTH_SHORT).show();
                                return true;
                            } else if (itemId == R.id.menu_thuchi_xoa1) {
                                Toast.makeText(baocao_activity_homedsthuchi.this, "Hãy nhấn giữ vào dòng muốn xóa", Toast.LENGTH_SHORT).show();
                                return true;
                            } else if (itemId == R.id.menu_thuchi_xoaall) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(baocao_activity_homedsthuchi.this);
                                builder.setTitle("Lưu ý");
                                builder.setIcon(R.drawable.warning_img);
                                builder.setMessage("Xóa tất cả dữ liệu ?");
                                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        thuchiDAO.deleteAll();
                                        Toast.makeText(baocao_activity_homedsthuchi.this, "Dữ liệu được xóa hết !!", Toast.LENGTH_SHORT).show();
                                        listviewThuchi();
                                        checklist();
                                    }
                                });
                                builder.setNegativeButton("Hủy",null);
                                builder.show();
                                return true;
                            } else if (itemId == R.id.menu_thuchi_chedoloc) {
                                if(lnLoc.getVisibility()==View.INVISIBLE){
                                    lnLoc.setVisibility(View.VISIBLE); rdogrLoai.clearCheck();rdogrDate.clearCheck();rdogrMoney.clearCheck();
                                    Toast.makeText(baocao_activity_homedsthuchi.this, "Chế độ lọc & sắp xếp: ON", Toast.LENGTH_SHORT).show();
                                }else{
                                    lnLoc.setVisibility(View.INVISIBLE);
                                    listviewThuchi();
                                    Toast.makeText(baocao_activity_homedsthuchi.this, "Chế độ lọc & sắp xếp: OFF", Toast.LENGTH_SHORT).show();
                                }

                                return true;
                            }
                            return false;
                        }
                    });

                    popupMenu.show();
                }
            });
        }

        private void back_homebc() {
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        private void anhxaID() {
            tvTitle = findViewById(R.id.baocao_dsthuchi_tvTitle);
            tvMenu = findViewById(R.id.baocao_dsthuchi_tvmenu);
            edt_timkiem = findViewById(R.id.baocao_dsthuchi_edt_timkiem);
            lv_thuchi = findViewById(R.id.baocao_listv_dsthuchi);

            rdogrLoai = findViewById(R.id.baocao_dsthuchi_rdogr_locthuchi);
            rdoThu = findViewById(R.id.baocao_dsthuchi_rdothu);
            rdoChi = findViewById(R.id.baocao_dsthuchi_rdochi);

            rdogrMoney = findViewById(R.id.baocao_dsthuchi_rdogr_money);
            rdoMoneyASC = findViewById(R.id.baocao_dsthuchi_rdo_moneyASC);
            rdoMoneyDSC = findViewById(R.id.baocao_dsthuchi_rdo_moneyDSC);

            rdogrDate = findViewById(R.id.baocao_dsthuchi_rdogr_date);
            rdoDateASC = findViewById(R.id.baocao_dsthuchi_rdo_dateASC);
            rdoDateDSC = findViewById(R.id.baocao_dsthuchi_rdo_dateDSC);
            lnLoc = findViewById(R.id.layout_baocao_dsthuchi_loc_sapxep);
            lnLoc.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onResume() {
            super.onResume();
            listviewThuchi();
        }
    }
