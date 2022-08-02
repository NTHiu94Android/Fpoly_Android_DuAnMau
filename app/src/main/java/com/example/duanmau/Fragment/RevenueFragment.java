package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RevenueFragment extends Fragment {
    private TextInputLayout tilFromDate, tilToDate;
    private TextInputEditText tieFromDate, tieToDate;
    private TextView tvDoanhThu;
    private Button btn;
    private Calendar time = Calendar.getInstance();
    private ThongKeDAO thongKeDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        tilFromDate = view.findViewById(R.id.tilFromDate);
        tilToDate = view.findViewById(R.id.tilToDate);
        tieFromDate = view.findViewById(R.id.edtFromDate);
        tieToDate = view.findViewById(R.id.edtToDate);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThuFM);
        btn = view.findViewById(R.id.btnRevenueFM);

        thongKeDAO = new ThongKeDAO(getActivity());

        tilFromDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tieFromDate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tilToDate.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tieToDate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double doanhThu = thongKeDAO.getRevenue(tieFromDate.getText().toString(), tieToDate.getText().toString());
                tvDoanhThu.setText(String.valueOf(doanhThu));
                tieFromDate.setText(null);
                tieToDate.setText(null);
            }
        });

        return view;
    }
}