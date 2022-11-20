package com.example.hifza.instantmet;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hifza.instantmet.adapter.Fragment_Adapter;
import com.example.hifza.instantmet.fragments.Month;
import com.example.hifza.instantmet.fragments.Week;
import com.example.hifza.instantmet.fragments.Year;
import com.example.hifza.instantmet.fragments.Yesterday;

/**
 * Created by Fareeha on 1/8/2018.
 */

public class LeaderActivity extends AppCompatActivity{
    Toolbar mtoolbar;
    TabLayout tabLayout;
    Fragment_Adapter menuPagerAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_layout);
        mtoolbar=findViewById(R.id.toolbar);

        viewPager = (ViewPager) findViewById(R.id.container);
        menuPagerAdapter = new Fragment_Adapter(getSupportFragmentManager());
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        Fragment_Adapter adapter = new Fragment_Adapter(getSupportFragmentManager());
        adapter.addFragment(new Yesterday(), "Yesterday");
        adapter.addFragment(new Week(), "Weekly");
        adapter.addFragment(new Month(), "Monthly");
        adapter.addFragment(new Year(), "Yearly");


        viewPager.setAdapter(adapter);
    }
    public void goBack(View view)
    {
     onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }
}