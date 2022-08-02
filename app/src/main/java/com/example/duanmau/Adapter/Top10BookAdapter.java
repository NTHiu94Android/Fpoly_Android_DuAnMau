package com.example.duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Modul.Top10;
import com.example.duanmau.R;

import java.util.ArrayList;

public class Top10BookAdapter extends RecyclerView.Adapter<Top10BookAdapter.Top10ViewHolder>{
    private ArrayList<Top10> list;

    public void getData(ArrayList<Top10> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top10, parent, false);
        return new Top10ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10ViewHolder holder, int position) {
        Top10 top10 = list.get(position);
        holder.tvNameBook.setText(top10.getTenSach());
        holder.tvNumberBook.setText(String.valueOf(top10.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class Top10ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameBook, tvNumberBook;
        public Top10ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameBook = itemView.findViewById(R.id.tvNameTop10);
            tvNumberBook = itemView.findViewById(R.id.tvNumberTop10);
        }
    }
}
