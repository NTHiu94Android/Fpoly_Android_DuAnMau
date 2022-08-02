package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duanmau.R;

public class HomeFragment extends Fragment {
    private Button btnMember, btnKindOfBook, btnBook, btnLoanslip, btnRevenue, btnTop10;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnMember = view.findViewById(R.id.btnHomeFMMember);
        btnKindOfBook = view.findViewById(R.id.btnHomeFMKOBook);
        btnBook = view.findViewById(R.id.btnHomeFMBook);
        btnLoanslip = view.findViewById(R.id.btnHomeFMLoanslip);
        btnRevenue = view.findViewById(R.id.btnHomeFMRevenue);
        btnTop10 = view.findViewById(R.id.btnHomeFMTop10);

        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, MemberFragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });
        btnKindOfBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, KindOfBookFragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, BookFragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });
        btnLoanslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, LoanslipFragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });
        btnRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, RevenueFragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });
        btnTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                try {
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, Top10Fragment.class.newInstance()).commit();
                } catch (Exception e) { }
            }
        });

        return view;
    }
}