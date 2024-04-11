package com.example.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorder.Adapter.FavoriteAdapter;
import com.example.foodorder.Database.DatabaseFavorite;
import com.example.foodorder.Model.ConvertClass;
import com.example.foodorder.Model.Food;
import com.example.foodorder.Model.FoodFavorite;
import com.example.foodorder.R;
import com.example.foodorder.databinding.ActivityFavoriteAtivityBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteAtivity extends AppCompatActivity {
    ActivityFavoriteAtivityBinding favoriteAtivityBinding;
    List<FoodFavorite> lstFavorite;
    FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteAtivityBinding = ActivityFavoriteAtivityBinding.inflate(getLayoutInflater());
        setContentView(favoriteAtivityBinding.getRoot());
        InitToolbar();
        SetupUI();
    }
    private void InitToolbar(){
        favoriteAtivityBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        favoriteAtivityBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        favoriteAtivityBinding.toolbar.imgCart.setVisibility(View.GONE);
        favoriteAtivityBinding.toolbar.imgBack.setOnClickListener(v->onBackPressed());
    }
    private void SetupUI(){
        List<Food> lstFood=new ArrayList<>();
        lstFavorite=new ArrayList<>();
        lstFavorite= DatabaseFavorite.getInstance(FavoriteAtivity.this).favoriteDAO().getListFoodFavorite();
        for(FoodFavorite favorite:lstFavorite){
            lstFood.add(ConvertClass.FavoriteToFood(favorite));
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FavoriteAtivity.this);
        favoriteAtivityBinding.rcvFavorite.setLayoutManager(linearLayoutManager);

        favoriteAdapter=new FavoriteAdapter(lstFood ,(Food food)->{
            Bundle bundle=new Bundle();
            bundle.putSerializable("food", food);
            Intent it=new Intent(FavoriteAtivity.this, FoodDetailActivity.class);
            it.putExtra("bundleFood",bundle);
            startActivity(it);
        });

        favoriteAtivityBinding.rcvFavorite.setAdapter(favoriteAdapter);

    }
}