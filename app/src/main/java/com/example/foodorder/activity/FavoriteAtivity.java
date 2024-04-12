package com.example.foodorder.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorder.Adapter.FavoriteAdapter;
import com.example.foodorder.Database.DatabaseFavorite;
import com.example.foodorder.Database.DatabaseFood;
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

    @Override
    protected void onResume() {
        super.onResume();
        SetupUI();
    }

    private void InitToolbar(){
        favoriteAtivityBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        favoriteAtivityBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        favoriteAtivityBinding.toolbar.imgCart.setVisibility(View.GONE);
        favoriteAtivityBinding.toolbar.imgBack.setOnClickListener(v->onBackPressed());
        favoriteAtivityBinding.toolbar.tvTitle.setText("Favorite Food");
    }
    private void SetupUI(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FavoriteAtivity.this);
        favoriteAtivityBinding.rcvFavorite.setLayoutManager(linearLayoutManager);
        List<Food> lstFood=new ArrayList<>();
        lstFavorite=new ArrayList<>();
        lstFavorite= DatabaseFavorite.getInstance(FavoriteAtivity.this).favoriteDAO().getListFoodFavorite();
        for(FoodFavorite favorite:lstFavorite){
            lstFood.add(ConvertClass.FavoriteToFood(favorite));
        }


        favoriteAdapter=new FavoriteAdapter(lstFood, (Food food) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("food", food);
            Intent it = new Intent(FavoriteAtivity.this, FoodDetailActivity.class);
            it.putExtra("bundleFood", bundle);
            startActivity(it);
        }, this::DeleteFoodFavorite);

        favoriteAtivityBinding.rcvFavorite.setAdapter(favoriteAdapter);

    }

    private void DeleteFoodFavorite(Food food, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông Báo");
        alert.setMessage("Bạn Có Chắc Muốn Xóa");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseFavorite.getInstance(FavoriteAtivity.this).favoriteDAO().deleteFood(ConvertClass.FoodToFavorite(food));
                lstFavorite.remove(position);
                favoriteAdapter.notifyItemChanged(position);
                favoriteAdapter.notifyDataSetChanged();


                List<Food> lstFood=new ArrayList<>();
                lstFavorite=new ArrayList<>();
                lstFavorite= DatabaseFavorite.getInstance(FavoriteAtivity.this).favoriteDAO().getListFoodFavorite();
                for(FoodFavorite favorite:lstFavorite){
                    lstFood.add(ConvertClass.FavoriteToFood(favorite));
                }
                favoriteAdapter.notifyDataSetChanged();
                favoriteAdapter.updateData(lstFood);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }
}