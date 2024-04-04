package com.example.foodorder.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.Constants.Frag;
import com.example.foodorder.R;
import com.example.foodorder.Adapter.ViewpagerAdapter;
import com.example.foodorder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setUpViewPager();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Thông Báo");
        alert.setMessage("Bạn có muốn thoát?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
    }

    private void setUpViewPager() {
        mainBinding.viewPager.setUserInputEnabled(true);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        mainBinding.viewPager.setAdapter(viewpagerAdapter);
        mainBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            if (item.getItemId() == R.id.action_home) {
                mainBinding.viewPager.setCurrentItem(0);
            } else if (item.getItemId() == R.id.action_cart) {
                mainBinding.viewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.action_fedback) {
                mainBinding.viewPager.setCurrentItem(2);
            } else if (item.getItemId() == R.id.action_contact) {
                mainBinding.viewPager.setCurrentItem(3);
            } else if (item.getItemId() == R.id.action_history) {
                mainBinding.viewPager.setCurrentItem(4);
            }
            return true;
        });

    }

    public void setToolBar(Frag value, String title) {
        if (value == Frag.HOME) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.GONE);
            return;
        }
        if (value == Frag.CART) {
//            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
        }
        if (value == Frag.FEEDBACK) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
        }
        if (value == Frag.CONTACT) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
        }
        if (value == Frag.HISTORY) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
        }
        mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
        mainBinding.toolbarMain.tvTitle.setText(title);
    }

}