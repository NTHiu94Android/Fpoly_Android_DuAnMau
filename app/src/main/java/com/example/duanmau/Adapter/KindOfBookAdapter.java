package com.example.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.Modul.LoaiSach;
import com.example.duanmau.R;
import java.util.ArrayList;

public class KindOfBookAdapter extends RecyclerView.Adapter<KindOfBookAdapter.kindOfBookHolder> implements Filterable {
    private ArrayList<LoaiSach> listLS;
    private Context context;
    private LoaiSachDAO loaiSachDAO;
    private ArrayList<LoaiSach> listLSFilter = new ArrayList<>();
    //Set data
    public void setData(ArrayList<LoaiSach> list, Context context){
        this.context = context;
        this.listLS = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public kindOfBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kindofbook, parent, false);
        return new kindOfBookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull kindOfBookHolder holder, int position) {
        loaiSachDAO = new LoaiSachDAO(context);
        LoaiSach loaiSach = listLS.get(position);
        holder.tv.setText(loaiSach.getTenLoaiSach());
        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(holder.getPosition());
            }
        });
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(holder.getPosition());
            }
        });
    }

    //Load ds
    public void loadDs(){
        listLS.clear();
        listLS = loaiSachDAO.getAllLoaiSach();
        notifyDataSetChanged();
    }
    //Delete loai sach
    private void dialogDelete(int i) {
        int maLS = listLS.get(i).getMaLoaiSach();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to delete kind of book?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loaiSachDAO.deleteLoaiSach(maLS);
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
    //Update loai sach
    private void dialogUpdate(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_update_kindofbook, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnClear = v.findViewById(R.id.btnCancelUpdateKOBDialog);
        Button btnSubmit = v.findViewById(R.id.btnSubmitUpdateKOBDialog);
        EditText edtNewNameKOB = v.findViewById(R.id.edtNewNameKOBDialog);
        EditText edtOldNameKOB = v.findViewById(R.id.edtOldNameKOBDialog);

        edtOldNameKOB.setText(listLS.get(i).getTenLoaiSach());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNewNameKOB.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                int maLS = listLS.get(i).getMaLoaiSach();
                String tenLS = edtNewNameKOB.getText().toString();

                LoaiSach loaiSach = new LoaiSach(maLS, tenLS);
                loaiSachDAO.updateLoaiSach(loaiSach);

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
    //Tra ve danh sach loai sach
    @Override
    public int getItemCount() {
        if(listLS.size() != 0){
            return listLS.size();
        }
        return 0;
    }
    //Tim kiem loai sach
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listLSFilter = listLS;
                } else {
                    listLSFilter.clear();
                    ArrayList<LoaiSach> filteredList = new ArrayList<>();
                    for (LoaiSach loaiSach : listLS) {
                        if (loaiSach.getTenLoaiSach().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(loaiSach);
                        }
                    }
                    listLSFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listLSFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listLSFilter = (ArrayList<LoaiSach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        if(listLSFilter.size()>0){
//            listLSFilter.clear();
//        }
//        if (charText.length() == 0) {
//            listLSFilter.addAll(listLS);
//        } else {
//            for (LoaiSach wp : listLS) {
//                if (wp.getTenLoaiSach().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    listLSFilter.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
    //View holder
    public class kindOfBookHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private ImageView imvEdit, imvDelete;
        public kindOfBookHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvNameKindOfBook);
            imvEdit = itemView.findViewById(R.id.imvEditKindOfBook);
            imvDelete = itemView.findViewById(R.id.imvDeleteKindOfBook);
        }
    }
}
