package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        },2000);
//        setContentView(R.layout.onboarding_main_layout);
//        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView),
//                getDataForOnboarding(), getApplicationContext());
//
//        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
//            @Override
//            public void onRightOut() {
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
//    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
//        // prepare data
//        PaperOnboardingPage scr1 = new PaperOnboardingPage("Hotels", "All hotels and hostels are sorted by hospitality rating",
//                Color.parseColor("#678FB4"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_circle_icon);
//        PaperOnboardingPage scr2 = new PaperOnboardingPage("Banks", "We carefully verify all banks before add them into the app",
//                Color.parseColor("#65B0B4"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_circle_icon);
//        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores", "All local stores are categorized for your convenience",
//                Color.parseColor("#9B90BC"), R.drawable.onboarding_pager_circle_icon, R.drawable.onboarding_pager_circle_icon);
//
//        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
//        elements.add(scr1);
//        elements.add(scr2);
//        elements.add(scr3);
//        return elements;
//    }
}