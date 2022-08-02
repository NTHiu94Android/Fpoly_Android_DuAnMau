package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmau.Adapter.Top10BookAdapter;
import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.Modul.Top10;
import com.example.duanmau.R;

import java.util.ArrayList;

public class Top10Fragment extends Fragment {
    private ThongKeDAO thongKeDAO;
    private Top10BookAdapter adapter;
    private RecyclerView rcv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        rcv = view.findViewById(R.id.rcvTop10Book);
        thongKeDAO = new ThongKeDAO(getActivity());
        ArrayList<Top10> list = thongKeDAO.getTop10();
        adapter = new Top10BookAdapter();
        adapter.getData(list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        return view;
    }
}