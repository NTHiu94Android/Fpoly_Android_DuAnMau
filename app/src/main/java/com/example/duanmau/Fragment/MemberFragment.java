package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.duanmau.Adapter.MemberAdapter;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.Modul.ThanhVien;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MemberFragment extends Fragment {
    private TextInputEditText textInputEditText;
    private GridView gridView;
    private MemberAdapter adapter;
    private ArrayList<ThanhVien> listTV;
    private ThanhVienDAO thanhVienDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        textInputEditText = view.findViewById(R.id.tiedt_search_member);
        gridView = view.findViewById(R.id.grvMemberFM);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_member);

        listTV = new ArrayList<>();
        thanhVienDAO = new ThanhVienDAO(getActivity());
        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberDialog();
            }
        });
        return view;
    }

    public void getData(){
        if(listTV.size()>0){
            listTV.clear();
        }
        listTV = thanhVienDAO.getAllThanhVien();
        adapter = new MemberAdapter(listTV, getActivity());
        gridView.setAdapter(adapter);
    }
    private void addMemberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_member, null);

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnClear = v.findViewById(R.id.btnCancelMemberDialog);
        Button btnAdd = v.findViewById(R.id.btnAddMemberDialog);
        EditText edtNameMember = v.findViewById(R.id.edtNameMemberDialog);
        EditText edtBirthday = v.findViewById(R.id.edtBirthdayMemberDialog);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNameMember.getText().toString().isEmpty() || edtBirthday.getText().toString().isEmpty() ){
                    Toast.makeText(getActivity(), "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tenTV = edtNameMember.getText().toString();
                String nsTV = edtBirthday.getText().toString();

                ThanhVien thanhVien = new ThanhVien(tenTV, nsTV);
                thanhVienDAO.insertThanhVien(thanhVien);
                Toast.makeText(getActivity(), "Add member succesfully!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                getData();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

}