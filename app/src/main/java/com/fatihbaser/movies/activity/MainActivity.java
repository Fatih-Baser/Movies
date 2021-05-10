package com.fatihbaser.movies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.fatihbaser.movies.R;
import com.fatihbaser.movies.adapter.ViewPageAdapter;
import com.fatihbaser.movies.view.fragment.AccountFragment;
import com.fatihbaser.movies.view.fragment.FavoriteFragment;
import com.fatihbaser.movies.view.fragment.HomeFragment;
import com.fatihbaser.movies.view.fragment.SerachFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark_Moviesfaction);
        } else{
            setTheme(R.style.Theme_Movies);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager = findViewById(R.id.viewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        setAdapter(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void setAdapter(ViewPager viewPager){

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPageAdapter.addFragment(new HomeFragment());
        viewPageAdapter.addFragment(new FavoriteFragment());
        viewPageAdapter.addFragment(new SerachFragment());
        viewPageAdapter.addFragment(new AccountFragment());

        viewPager.setAdapter(viewPageAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.navigator_home :
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigator_list :
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigator_search :
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigator_acocunt :
                    viewPager.setCurrentItem(3);
                    return true;
                default:
                    return false;
            }
        }
    };
}