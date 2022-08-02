package com.example.duanmau.Fragment;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duanmau.Adapter.KindOfBookAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.Modul.LoaiSach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class KindOfBookFragment extends Fragment implements SearchView.OnQueryTextListener{
    //private TextInputEditText textInputEditText;
    private RecyclerView rcv;
    private KindOfBookAdapter adapter;
    private ArrayList<LoaiSach> listLS;
    private LoaiSachDAO loaiSachDAO;
    private LinearLayoutManager manager;
    private SearchView sv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kind_of_book, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_kindofbook);
        //textInputEditText = view.findViewById(R.id.tiedt_search_kindofbook);
        rcv = view.findViewById(R.id.rcvKindOfBook);
        sv = view.findViewById(R.id.svKOB);

        listLS = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity());
        loaiSachDAO = new LoaiSachDAO(getActivity());
        getData(manager);

        sv.setOnQueryTextListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKOF();
            }
        });

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    //Add loai sách
    private void addKOF() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_kobook, null);
        builder.setView(view);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextInputEditText edt = view.findViewById(R.id.edtAddNameKOBDialog);
        Button btnSubmit = view.findViewById(R.id.btnSubmitAddKOBrDialog);
        Button btnCancel = view.findViewById(R.id.btnCancelAddKOBDialog);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameKOB = edt.getText().toString().trim();
                LoaiSach loaiSach = new LoaiSach(nameKOB);
                listLS.add(loaiSach);
                loaiSachDAO.insertLoaiSach(loaiSach);
                getData(manager);
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
    //Lấy dữ liệu
    private void getData(LinearLayoutManager manager){
        if(listLS.size()>0){
            listLS.clear();
        }
        listLS = loaiSachDAO.getAllLoaiSach();

        adapter = new KindOfBookAdapter();
        adapter.setData(listLS, getActivity());

        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }
}