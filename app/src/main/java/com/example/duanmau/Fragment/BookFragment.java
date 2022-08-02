package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.Adapter.BookAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.Modul.LoaiSach;
import com.example.duanmau.Modul.Sach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class BookFragment extends Fragment {

    private RecyclerView rcv;
    private FloatingActionButton fab;
    private ArrayList<Sach> list;
    private SachDAO sachDAO;
    private BookAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        rcv = view.findViewById(R.id.rcvBook);
        fab = view.findViewById(R.id.fab_add_book);
        list = new ArrayList<>();
        sachDAO = new SachDAO(getActivity());
        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddBook();
            }
        });
        return view;
    }

    private void getData(){
        if(list.size()>0){
            list.clear();
        }
        list = sachDAO.getAllSach();
        adapter = new BookAdapter();
        adapter.getData(list, getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
    }
    private void getdataSpn(Spinner spn) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getActivity());
        ArrayList<LoaiSach> listLS = loaiSachDAO.getAllLoaiSach();
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (LoaiSach loaiSach : listLS) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("MALOAISACH", String.valueOf(loaiSach.getMaLoaiSach()));
            hashMap.put("TENLOAISACH", loaiSach.getTenLoaiSach());
            listHM.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listHM, R.layout.item_spn_book,
                new String[]{"TENLOAISACH"}, new int[]{R.id.tvSpnBook});
        spn.setAdapter(adapter);
    }

    private void dialogAddBook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_book, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Spinner spn = view.findViewById(R.id.spnBook);
        TextInputEditText edtTenSach = view.findViewById(R.id.edtAddNameBookDialog);
        TextInputEditText edtGiaThue = view.findViewById(R.id.edtPriceBookDialog);
        Button btnSubmit = view.findViewById(R.id.btnSubmitAddBookrDialog);
        Button btnCancel = view.findViewById(R.id.btnCancelAddBookDialog);
        getdataSpn(spn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spn.getSelectedItem();
                if(edtTenSach.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter enough information", Toast.LENGTH_SHORT).show();
                }
                String tenSach = edtTenSach.getText().toString().trim();
                double giaSach = Double.parseDouble(edtGiaThue.getText().toString().trim());
                int maLoaiSach = Integer.parseInt(hashMap.get("MALOAISACH"));
                Sach sach = new Sach(tenSach, giaSach, maLoaiSach);

                sachDAO.insertSach(sach);
                Toast.makeText(getActivity(), "Insert book successfully", Toast.LENGTH_SHORT).show();
                getData();
                alertDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}