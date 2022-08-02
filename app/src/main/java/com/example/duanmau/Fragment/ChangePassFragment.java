package com.example.duanmau.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.Modul.ThuThu;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangePassFragment extends Fragment {
    private TextInputEditText edtOldPassChangeFM, edtNewPassChangeFM, edtReNewPassChangeFM;
    private ThuThuDAO thuThuDAO;
    private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edtOldPassChangeFM = view.findViewById(R.id.edtOldPassChangeFM);
        edtNewPassChangeFM = view.findViewById(R.id.edtNewPassChangeFM);
        edtReNewPassChangeFM = view.findViewById(R.id.edtReNewPassChangeFM);
        Button btnSubmitChangPW_FM = view.findViewById(R.id.btnSubmitChangPW_FM);
        Button btnCancelChangePW_FM = view.findViewById(R.id.btnCancelChangePW_FM);

        thuThuDAO = new ThuThuDAO(getActivity());

        btnCancelChangePW_FM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtOldPassChangeFM.setText("");
                edtNewPassChangeFM.setText("");
                edtReNewPassChangeFM.setText("");
            }
        });
        btnSubmitChangPW_FM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
        return view;
    }
    private void changePassword(){
        //lay ma thu thu tu login
        intent = getActivity().getIntent();
        String usnameLogin = intent.getStringExtra("USERNAME");
        ThuThu thuThu = thuThuDAO.getThuThu(usnameLogin);

        String old_pass_thuthu = thuThu.getMatKhau();
        String old_pass = edtOldPassChangeFM.getText().toString().trim();
        String new_pass = edtNewPassChangeFM.getText().toString().trim();
        String renew_pass = edtReNewPassChangeFM.getText().toString().trim();
        if(old_pass.equals("")||new_pass.equals("")||renew_pass.equals("")){
            Toast.makeText(getActivity(), "Dien day du thong tin", Toast.LENGTH_SHORT).show();
        }else {
            if(!old_pass_thuthu.equals(old_pass)){
                Toast.makeText(getActivity(), "Mat khau cu khong dung", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!new_pass.equals(renew_pass)){
                Toast.makeText(getActivity(), "Nhap lai mat khau khong khop", Toast.LENGTH_SHORT).show();
                return;
            }
            if(new_pass.equals(old_pass_thuthu)){
                Toast.makeText(getActivity(), "Mat khau moi trung voi mat khau cu", Toast.LENGTH_SHORT).show();
                return;
            }
            if(old_pass_thuthu.equals(old_pass) && new_pass.equals(renew_pass)){
                thuThu.setMatKhau(new_pass);
                thuThuDAO.updateThuThu(thuThu);
                Toast.makeText(getActivity(), "Doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}