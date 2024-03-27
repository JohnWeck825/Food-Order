package com.example.foodorder.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.R;
import com.example.foodorder.fragment.ViewpagerAdapter;
import com.example.foodorder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setUpViewPager();

    }
    private void setUpViewPager(){
        mainBinding.viewPager.setUserInputEnabled(true);
        ViewpagerAdapter viewpagerAdapter= new ViewpagerAdapter(this);
        mainBinding.viewPager.setAdapter(viewpagerAdapter);
        mainBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mainBinding.bottomNavigation.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;

                    case 1:
                        mainBinding.bottomNavigation.getMenu().findItem(R.id.action_cart).setChecked(true);
                        break;

                    case 2:
                        mainBinding.bottomNavigation.getMenu().findItem(R.id.action_fedback).setChecked(true);
                        break;

                    case 3:
                        mainBinding.bottomNavigation.getMenu().findItem(R.id.action_contact).setChecked(true);
                        break;

                    case 4:
                        mainBinding.bottomNavigation.getMenu().findItem(R.id.action_history).setChecked(true);
                        break;
                }
            }
        });
        mainBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.action_home){
                mainBinding.viewPager.setCurrentItem(0);
            }
            else if(item.getItemId()==R.id.action_cart){
                mainBinding.viewPager.setCurrentItem(1);
            }
            else if(item.getItemId()==R.id.action_fedback){
                mainBinding.viewPager.setCurrentItem(2);
            }
            else if(item.getItemId()==R.id.action_contact){
                mainBinding.viewPager.setCurrentItem(3);
            }
            else if(item.getItemId()==R.id.action_history){
                mainBinding.viewPager.setCurrentItem(4);
            }
            return true;
        });

    }

    public void setToolBar(boolean isHome, String title) {
        if (isHome) {
            mainBinding.toolbar.layoutToolbar.setVisibility(View.GONE);
            return;
        }
        mainBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mainBinding.toolbar.tvTitle.setText(title);
    }

}