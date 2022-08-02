package com.example.duanmau.Fragment;

import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.Adapter.BookAdapter;
import com.example.duanmau.Adapter.LoanSlipAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.Modul.LoaiSach;
import com.example.duanmau.Modul.PhieuMuon;
import com.example.duanmau.Modul.Sach;
import com.example.duanmau.Modul.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LoanslipFragment extends Fragment {
    private RecyclerView rcv;
    private FloatingActionButton fab;
    private LoanSlipAdapter adapter;
    private ArrayList<PhieuMuon> list;
    private PhieuMuonDAO phieuMuonDAO;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loanslip, container, false);
        rcv = view.findViewById(R.id.rcvLoanslip);
        fab = view.findViewById(R.id.fab_add_loanslip);

        list = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddLoanslip();
            }
        });
        return view;
    }

    private void getData(){
        if(list.size()>0){
            list.clear();
        }
        list = phieuMuonDAO.getAllPhieuMuon();
        adapter = new LoanSlipAdapter();
        adapter.getData(list, getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
    }
    private void getdataSpnMember(Spinner spn) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getActivity());
        ArrayList<ThanhVien> listTV = thanhVienDAO.getAllThanhVien();
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (ThanhVien thanhVien : listTV) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("MATHANHVIEN", String.valueOf(thanhVien.getMaThanhVien()));
            hashMap.put("TENTHANHVIEN", thanhVien.getTenThanhVien());
            listHM.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listHM, R.layout.item_spn_book,
                new String[]{"TENTHANHVIEN"}, new int[]{R.id.tvSpnBook});
        spn.setAdapter(adapter);
    }
    private void getdataSpnSach(Spinner spn) {
        SachDAO sachDAO = new SachDAO(getActivity());
        ArrayList<Sach> listS = sachDAO.getAllSach();
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (Sach sach : listS) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("MASACH", String.valueOf(sach.getMaSach()));
            hashMap.put("TENSACH", sach.getTenSach());
            hashMap.put("MALOAISACH", String.valueOf(sach.getMaLoaiSach()));
            listHM.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listHM, R.layout.item_spn_book,
                new String[]{"TENSACH"}, new int[]{R.id.tvSpnBook});
        spn.setAdapter(adapter);
    }
    private void dialogAddLoanslip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_loanslip, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Spinner spnBook = view.findViewById(R.id.spnNameBookLoanslip);
        Spinner spnMember = view.findViewById(R.id.spnNameTVLoanslip);
        TextInputEditText edtDate = view.findViewById(R.id.edtAddDateLoanslip);
        TextInputEditText edtPrice = view.findViewById(R.id.edtAddPriceLoanslip);
        TextInputEditText edtNameManager = view.findViewById(R.id.edtAddNameManagerLoanslip);
        Button btnSubmit = view.findViewById(R.id.btnSubmitAddLoanslipDialog);
        Button btnCancel = view.findViewById(R.id.btnCancelAddLoanslipDialog);
        getdataSpnSach(spnBook);
        getdataSpnMember(spnMember);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);

        intent = getActivity().getIntent();
        String tenTT = intent.getStringExtra("USERNAME");
        edtNameManager.setText(tenTT);
        edtDate.setText(strDate);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMapBook = (HashMap<String, String>) spnBook.getSelectedItem();
                HashMap<String, String> hashMapMember = (HashMap<String, String>) spnMember.getSelectedItem();
                if(edtDate.getText().toString().isEmpty() || edtPrice.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter enough information", Toast.LENGTH_SHORT).show();
                }
                int maSach = Integer.parseInt(hashMapBook.get("MASACH"));
                int maTV = Integer.parseInt(hashMapMember.get("MATHANHVIEN"));
                double tienThue = Double.parseDouble(edtPrice.getText().toString());

                PhieuMuon phieuMuon = new PhieuMuon(strDate, "(Unpain)", tienThue, maSach, tenTT, maTV);
                phieuMuonDAO.insertPhieuMuon(phieuMuon);
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