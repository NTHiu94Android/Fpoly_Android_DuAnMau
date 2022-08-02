package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.Modul.ThuThu;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;

public class CreateAccountFragment extends Fragment {

    private TextInputEditText edtUsernameNewSignup, edtPasswordNewSignup, edtRePasswordNewSignup, edtNameNewSignup;
    private Button btnSignup, btnCancelSignup;
    private ThuThuDAO thuThuDAO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        edtUsernameNewSignup = view.findViewById(R.id.edtUsernameNewSignup);
        edtPasswordNewSignup = view.findViewById(R.id.edtPasswordNewSignup);
        edtRePasswordNewSignup = view.findViewById(R.id.edtRePasswordNewSignup);
        edtNameNewSignup = view.findViewById(R.id.edtNameNewSignup);
        btnSignup = view.findViewById(R.id.btnSignup);
        btnCancelSignup = view.findViewById(R.id.btnCancelSignup);

        thuThuDAO = new ThuThuDAO(getActivity());

        btnCancelSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsernameNewSignup.setText("");
                edtPasswordNewSignup.setText("");
                edtRePasswordNewSignup.setText("");
                edtNameNewSignup.setText("");
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignup();
            }
        });
        return view;
    }

    private void checkSignup() {
        String user = edtUsernameNewSignup.getText().toString().trim();
        String tenTT = edtNameNewSignup.getText().toString().trim();
        String pass = edtPasswordNewSignup.getText().toString().trim();
        String repass = edtRePasswordNewSignup.getText().toString();
        if(user.equals("")||pass.equals("")||repass.equals("")||tenTT.equals(""))
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        else{
            if(pass.equals(repass)){
                Boolean checkuser = thuThuDAO.checkusername(user);
                if(!checkuser){
                    ThuThu thuThu = new ThuThu(user, tenTT, pass);
                    thuThuDAO.insertThuThu(thuThu);
                    Toast.makeText(getActivity(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(), "Repass không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}