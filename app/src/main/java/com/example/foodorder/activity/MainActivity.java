package com.example.foodorder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.Adapter.ViewpagerAdapter;
import com.example.foodorder.Constants.Constant;
import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Constants.StateDownload;
import com.example.foodorder.R;
import com.example.foodorder.SharePreference.PreferenceDownload;
import com.example.foodorder.databinding.ActivityMainBinding;
import com.example.foodorder.databinding.DrawHeaderBinding;
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
    private DrawHeaderBinding drawHeaderBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        setSupportActionBar(mainBinding.toolbarMain.optionToolbar);
        setUpViewPager();
        SetupDrawerLayout();




    }
    void SetupDrawerLayout(){
        mainBinding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                mainBinding.drawerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mainBinding.toolbarMain.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.drawerLayout.setVisibility(View.VISIBLE);
                mainBinding.drawerLayout.open();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.option_menu,menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Clear the title of the action bar
        getSupportActionBar().setTitle(""); // or getSupportActionBar().setTitle(null);
        return super.onPrepareOptionsMenu(menu);
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
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Thông Báo");
            alert.setMessage("Bạn có muốn Đăng Xuất?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferenceDownload preferenceDownload = new PreferenceDownload(MainActivity.this);
                    preferenceDownload.setValueDownload(Constant.KEY, StateDownload.FIRSTDOWLOAD);
                    ContactFunction.showToastMessage(MainActivity.this, "Ok");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

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
        return super.onOptionsItemSelected(item);
    }

    public void setToolBar(Frag value, String title) {
        if (value == Frag.HOME) {

            mainBinding.toolbarMain.optionToolbar.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.imgBack.setVisibility(View.GONE);


            mainBinding.toolbarMain.btnMenu.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.imgCart.setVisibility(View.GONE);
            //            mainBinding.txtUser.setVisibility(View.VISIBLE);


        }
        if (value == Frag.CART) {
            mainBinding.toolbarMain.optionToolbar.setVisibility(View.GONE);
            mainBinding.toolbarMain.btnMenu.setVisibility(View.GONE);
            mainBinding.toolbarMain.imgBack.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.imgCart.setVisibility(View.VISIBLE);

        }
        if (value == Frag.FEEDBACK ||value == Frag.CONTACT||value == Frag.HISTORY) {
            mainBinding.toolbarMain.optionToolbar.setVisibility(View.GONE);
            mainBinding.toolbarMain.btnMenu.setVisibility(View.GONE);
            mainBinding.toolbarMain.imgBack.setVisibility(View.VISIBLE);
            mainBinding.toolbarMain.imgCart.setVisibility(View.GONE);
        }

        mainBinding.toolbarMain.layoutToolbar.setVisibility(View.VISIBLE);
        mainBinding.toolbarMain.tvTitle.setText(title);
    }
    void SetupHeaderNavigationView(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Account").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    drawHeaderBinding.tvUser.setText(snapshot.child("username").getValue(String.class));
                    drawHeaderBinding.tvEmail.setText(snapshot.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}