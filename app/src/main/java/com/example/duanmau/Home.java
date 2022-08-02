package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.Fragment.BookFragment;
import com.example.duanmau.Fragment.ChangePassFragment;
import com.example.duanmau.Fragment.CreateAccountFragment;
import com.example.duanmau.Fragment.HomeFragment;
import com.example.duanmau.Fragment.KindOfBookFragment;
import com.example.duanmau.Fragment.LoanslipFragment;
import com.example.duanmau.Fragment.MemberFragment;
import com.example.duanmau.Fragment.RevenueFragment;
import com.example.duanmau.Fragment.Top10Fragment;
import com.example.duanmau.Modul.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private long back;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Fragment fragment;
    private Class fragmentClass = null;
    private View mHeader;
    private TextView tvUserHeader, tvHeaderNameUserNav;
    private Intent intent;
    private ThuThuDAO thuThuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        mHeader = navigationView.getHeaderView(0);
        tvUserHeader = mHeader.findViewById(R.id.tvHeaderNameNav);
        tvHeaderNameUserNav = mHeader.findViewById(R.id.tvHeaderNameUserNav);
        toolbar = findViewById(R.id.toolbar);
        //Add toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //Lay username tu login gan van header_nav_view
        intent = getIntent();
        String usnameLogin = intent.getStringExtra("USERNAME");

        thuThuDAO = new ThuThuDAO(getApplicationContext());
        ThuThu thuThu = thuThuDAO.getThuThu(usnameLogin.trim());
        tvUserHeader.setText(thuThu.getTenThuThu());
        if(usnameLogin.equals("admin")){
            tvHeaderNameUserNav.setText("(Admin)");
        }
        if(!usnameLogin.equals("admin")){
            navigationView.getMenu().findItem(R.id.menuAddAccount).setVisible(false);
        }
        //Chon HomeFragment lam trang chu
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.contenLayout, HomeFragment.class.newInstance()).commit();
        } catch (Exception e) { }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHome:
                        fragmentClass = HomeFragment.class;
                        break;
                    case R.id.menuLoanslipManager:
                        fragmentClass = LoanslipFragment.class;
                        break;
                    case R.id.menuKindOfBook:
                        fragmentClass = KindOfBookFragment.class;
                        break;
                    case R.id.menuBookManager:
                        fragmentClass = BookFragment.class;
                        break;
                    case R.id.menuMemberManager:
                        fragmentClass = MemberFragment.class;
                        break;
                    case R.id.menuTop10Book:
                        fragmentClass = Top10Fragment.class;
                        break;
                    case R.id.menuRevenue:
                        fragmentClass = RevenueFragment.class;
                        break;
                    case R.id.menuAddAccount:
                        fragmentClass = CreateAccountFragment.class;
                        break;
                    case R.id.menuChangePass:
                        fragmentClass = ChangePassFragment.class;
                        break;
                    case R.id.menuLogout:
                        finish();
                        break;
                    default:
                        fragmentClass = HomeFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contenLayout, fragment).commit();
                    setTitle(item.getTitle());
                    drawerLayout.closeDrawer(GravityCompat.START);
                } catch (Exception exception) {
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(back + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(Home.this, "Trở lại lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        back = System.currentTimeMillis();
    }
}