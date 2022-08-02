package com.example.duanmau.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.Modul.PhieuMuon;
import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class LoanSlipAdapter extends RecyclerView.Adapter<LoanSlipAdapter.LoanSlipViewHolder>{
    private PhieuMuonDAO phieuMuonDAO;
    private Context context;
    private ArrayList<PhieuMuon> list;

    public void getData(ArrayList<PhieuMuon> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoanSlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loanslip, parent, false);
        return new LoanSlipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanSlipViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        phieuMuonDAO = new PhieuMuonDAO(context);
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        SachDAO sachDAO = new SachDAO(context);

        //Lay ma thanh vien va ma sach
        int mathanhvien = phieuMuon.getMaThanhVien();
        int masach = phieuMuon.getMaSach();

        String nameBook = sachDAO.getNameSach(masach);
        String nameMember = thanhVienDAO.getNameTV(mathanhvien);
        String money = "Price: " + phieuMuon.getTienThue();

        //set info len itemview
        holder.tvNameMember.setText(nameMember);
        holder.tvNameBook.setText(nameBook);
        holder.tvNameMember.setText(nameMember);
        holder.tvNameBook.setText(nameBook);
        holder.tvStatus.setText(phieuMuon.getTrangThai());
        holder.tvMoney.setText(money);
        holder.tvDate.setText(phieuMuon.getNgayMuon());

        if(phieuMuon.getTrangThai().equals("(Pain)")){
            holder.tvStatus.setTextColor(Color.GREEN);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(phieuMuon, holder);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(phieuMuon);
            }
        });
    }
    //lay danh sach
    private void loadDs(){
        if(list.size() > 0){
            list.clear();
        }
        list = phieuMuonDAO.getAllPhieuMuon();
        notifyDataSetChanged();
    }

    //Cap nhat phieu muon
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private void dialogUpdate(PhieuMuon phieuMuon, LoanSlipViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_update_loanslip, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //Anh xa
        Switch sw = v.findViewById(R.id.swLoanslip);
        Button btnClear = v.findViewById(R.id.btnCancelUpdateLoanslipDialog);
        Button btnSubmit = v.findViewById(R.id.btnSubmitUpdateLoanslipDialog);
        TextInputEditText edtNameMember = v.findViewById(R.id.edtOldNameMemberLoanslipUpdateDialog);
        TextInputEditText edtNameBook = v.findViewById(R.id.edtOldNameBookLoanslipUpdateDialog);
        TextInputEditText edtDateLoanslip = v.findViewById(R.id.edtDateLoanslipUpdateDialog);
        TextInputEditText edtPriceLoanslip = v.findViewById(R.id.edtPriceLoanslipUpdateDialog);
        TextInputEditText edtPriceNewLoanslip = v.findViewById(R.id.edtPriceNewLoanslipUpdateDialog);

        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        SachDAO sachDAO = new SachDAO(context);

        if(phieuMuon.getTrangThai().equals("(Pain)")){
            sw.setChecked(true);
        }else {
            sw.setChecked(false);
        }
        //Lay ma thanh vien va ma sach
        int mathanhvien = phieuMuon.getMaThanhVien();
        int masach = phieuMuon.getMaSach();
        //lay ten sach/thanhvien
        String nameBook = sachDAO.getNameSach(masach);
        String nameMember = thanhVienDAO.getNameTV(mathanhvien);

        edtNameMember.setText(nameMember);
        edtNameBook.setText(nameBook);
        edtDateLoanslip.setText(phieuMuon.getNgayMuon());
        edtPriceLoanslip.setText(String.valueOf(phieuMuon.getTienThue()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check info
                if(edtPriceNewLoanslip.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                //set status
                if(sw.isChecked()){
                    phieuMuon.setTrangThai("(Pain)");
                }else {
                    phieuMuon.setTrangThai("(Unpain)");
                }
                //set color
                if(phieuMuon.getTrangThai().equals("(Pain)")){
                    holder.tvStatus.setTextColor(Color.GREEN);
                }else{
                    holder.tvStatus.setTextColor(Color.RED);
                }
                //uddate loanslip
                int maPM = phieuMuon.getMaPhieuMuon();
                String ngayMuon = phieuMuon.getNgayMuon();
                String trangthai = phieuMuon.getTrangThai();
                double tienthue = Double.parseDouble(edtPriceNewLoanslip.getText().toString());
                String maThuThu = phieuMuon.getMaThuThu();

                PhieuMuon phieuMuon1 = new PhieuMuon(maPM, ngayMuon, trangthai, tienthue, masach, maThuThu, mathanhvien);
                phieuMuonDAO.updatePhieuMuon(phieuMuon1);

                loadDs();

                alertDialog.dismiss();
                Toast.makeText(context, "Update member succesfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    //Xoa phieu muon
    private void dialogDelete(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to delete loanslip?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                phieuMuonDAO.deletePhieuMuon(phieuMuon.getMaPhieuMuon());
                loadDs();
                Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        if(list.size() > 0){
            return list.size();
        }
        return 0;
    }

    public class LoanSlipViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameMember, tvNameBook, tvStatus, tvDate, tvMoney;
        private Button btnEdit, btnDelete;
        public LoanSlipViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameMember = itemView.findViewById(R.id.tvNameMemberLoanslip);
            tvNameBook = itemView.findViewById(R.id.tvNameBookLoanSlip);
            tvStatus = itemView.findViewById(R.id.tvStatusLoanslip);
            tvDate = itemView.findViewById(R.id.tvDateLoanslip);
            tvMoney = itemView.findViewById(R.id.tvMoneyLoanSlip);
            btnEdit = itemView.findViewById(R.id.btnEditLoanSlip);
            btnDelete = itemView.findViewById(R.id.btnDeleteLoanSlip);
        }
    }
}
