package com.example.foodorder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.Constants.Constant;
import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Constants.StateDownload;
import com.example.foodorder.R;
import com.example.foodorder.Adapter.ViewpagerAdapter;
import com.example.foodorder.SharePreference.PreferenceDownload;
import com.example.foodorder.databinding.ActivityMainBinding;
import com.example.foodorder.function.ContactFunction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setSupportActionBar(mainBinding.optionToolbar);
        setUpViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.option_menu,menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid= item.getItemId();
        if(itemid==R.id.item_logout){
            PreferenceDownload preferenceDownload = new PreferenceDownload(this);
            preferenceDownload.setValueDownload(Constant.KEY, StateDownload.FIRSTDOWLOAD);
//            mAuth.signOut();
            ContactFunction.showToastMessage(this, "Ok");
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolBar(Frag value, String title) {
        if (value == Frag.HOME) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.imgCart.setVisibility(View.GONE);
            mainBinding.toolbarMain.imgBack.setVisibility(View.GONE);
            mainBinding.optionToolbar.setVisibility(View.VISIBLE);
            mainBinding.txtUser.setVisibility(View.VISIBLE);

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = firebaseUser.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Account").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        mainBinding.txtUser.setText(snapshot.child("username").getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return;
        }
        if (value == Frag.CART) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.optionToolbar.setVisibility(View.GONE);
        }
        if (value == Frag.FEEDBACK) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
            mainBinding.optionToolbar.setVisibility(View.GONE);
        }
        if (value == Frag.CONTACT) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
            mainBinding.optionToolbar.setVisibility(View.GONE);
        }
        if (value == Frag.HISTORY) {
            mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.tvTitle.setText(title);
            mainBinding.optionToolbar.setVisibility(View.GONE);
        }
        mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
        mainBinding.toolbarMain.tvTitle.setText(title);
    }
}