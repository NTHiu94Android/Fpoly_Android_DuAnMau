package com.example.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.Modul.ThanhVien;
import com.example.duanmau.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberAdapter extends BaseAdapter {
    private ArrayList<ThanhVien> list;
    private Context context;
    private ThanhVienDAO thanhVienDAO;

    public MemberAdapter(ArrayList<ThanhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder{
        CircleImageView cimv;
        TextView tvName, tvBirthday;
        ImageView imvUpdate, imvDelete;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(view == null){
            view = inflater.inflate(R.layout.item_member, viewGroup, false);
            holder = new ViewHolder();
            holder.cimv = view.findViewById(R.id.civMember);
            holder.tvName = view.findViewById(R.id.tvNameMember);
            holder.tvBirthday = view.findViewById(R.id.tvBirthdayMember);
            holder.imvUpdate = view.findViewById(R.id.imvUpdateMember);
            holder.imvDelete = view.findViewById(R.id.imvDeleteMember);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        thanhVienDAO = new ThanhVienDAO(context);

        holder.cimv.setImageResource(R.drawable.ic_profile);
        holder.tvName.setText(list.get(i).getTenThanhVien());
        holder.tvBirthday.setText(list.get(i).getNamSinh());

        holder.imvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(inflater, i);
            }
        });
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(i);
            }
        });
        return view;
    }

    private void dialogUpdate(LayoutInflater inflater, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = inflater.inflate(R.layout.dialog_update_member, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnClear = v.findViewById(R.id.btnCancelUpdateMemberDialog);
        Button btnSubmit = v.findViewById(R.id.btnSubmitUpdateMemberDialog);
        EditText edtNewNameMember = v.findViewById(R.id.edtNewNameMemberDialog);
        EditText edtNewBirthday = v.findViewById(R.id.edtNewBirthdayMemberDialog);
        EditText edtOldNameMember = v.findViewById(R.id.edtOldNameMemberDialog);
        EditText edtOldBirthdayMember = v.findViewById(R.id.edtOldBirthdayMemberDialog);

        edtOldNameMember.setText(list.get(i).getTenThanhVien());
        edtOldBirthdayMember.setText(list.get(i).getNamSinh());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNewNameMember.getText().toString().isEmpty() || edtNewBirthday.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                int maTV = list.get(i).getMaThanhVien();
                String tenMoiTV = edtNewNameMember.getText().toString();
                String nsMoiTV = edtNewBirthday.getText().toString();

                ThanhVien thanhVien = new ThanhVien(maTV, tenMoiTV, nsMoiTV);
                thanhVienDAO.updateThanhVien(thanhVien);
                list.clear();
                list = thanhVienDAO.getAllThanhVien();
                notifyDataSetChanged();
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

    private void dialogDelete(int i) {
        int maTV = list.get(i).getMaThanhVien();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to delete members?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                thanhVienDAO.deleteThanhVien(maTV);
                list.clear();
                list = thanhVienDAO.getAllThanhVien();
                notifyDataSetChanged();
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
}
