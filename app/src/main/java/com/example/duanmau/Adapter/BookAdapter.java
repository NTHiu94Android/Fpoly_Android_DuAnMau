package com.example.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.Modul.Sach;
import com.example.duanmau.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private Context context;
    private ArrayList<Sach> listSach;
    private SachDAO sachDAO;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public void getData(ArrayList<Sach> listSach, Context context){
        this.listSach = listSach;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        sachDAO = new SachDAO(context);
        Sach sach = listSach.get(position);
        int maloaisach = sach.getMaLoaiSach();
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        String tenloaisach = loaiSachDAO.getNameBook(maloaisach);
        if(sach == null){
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(sach.getMaSach()));
        viewBinderHelper.setOpenOnlyOne(true);

        holder.tvNameBook.setText(sach.getTenSach());
        holder.tvNameKOB_Book.setText(tenloaisach);


        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdateSAch(sach);
                viewBinderHelper.closeLayout(String.valueOf(sach.getMaSach()));
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteSach(sach);
            }
        });
    }

    //Load ds
    public void loadDs(){
        if(listSach.size()>0){
            listSach.clear();
        }
        listSach = sachDAO.getAllSach();
        notifyDataSetChanged();
    }
    private void dialogDeleteSach(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to delete book?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sachDAO.deleteSach(sach.getMaSach());
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

    private void dialogUpdateSAch(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_update_book, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnClear = v.findViewById(R.id.btnCancelUpdateBookDialog);
        Button btnSubmit = v.findViewById(R.id.btnSubmitUpdateBookDialog);
        EditText edtNewNameB = v.findViewById(R.id.edtNewNameBookUpdateDialog);
        EditText edtOldNameB = v.findViewById(R.id.edtOldNameBookUpdateDialog);
        EditText edtOldPriceB = v.findViewById(R.id.edtOldPriceBookUpdateDialog);
        EditText edtNewPriceB = v.findViewById(R.id.edtNewPriceBookUpdateDialog);

        edtOldNameB.setText(sach.getTenSach());
        edtOldPriceB.setText(String.valueOf(sach.getGiaThue()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNewNameB.getText().toString().isEmpty() || edtNewPriceB.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                int masach = sach.getMaSach();
                String tensach = edtNewNameB.getText().toString();
                double giathue = Double.parseDouble(edtNewPriceB.getText().toString());
                int maLoaiSach = sach.getMaLoaiSach();

                Sach sach1 = new Sach(masach, tensach, giathue, maLoaiSach);
                sachDAO.updateSach(sach1);

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

    @Override
    public int getItemCount() {
        if(listSach != null){
            return listSach.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private SwipeRevealLayout swipeRevealLayout;
        private TextView tvEdit, tvDelete, tvNameKOB_Book, tvNameBook;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayoutBook);
            tvEdit = itemView.findViewById(R.id.tvEditBook);
            tvDelete = itemView.findViewById(R.id.tvDeleteBook);
            tvNameKOB_Book = itemView.findViewById(R.id.tvNameKOBook_Book);
            tvNameBook = itemView.findViewById(R.id.tvNameBook);
        }
    }
}
