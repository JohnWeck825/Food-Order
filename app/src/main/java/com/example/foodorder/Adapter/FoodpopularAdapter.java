package com.example.foodorder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.EventClickHandle.IonClickListener;
import com.example.foodorder.Model.Food;
import com.example.foodorder.databinding.ItemFoodPopularBinding;
import com.example.foodorder.utils.GlideUtilis;

import java.util.List;

public class FoodpopularAdapter extends RecyclerView.Adapter<FoodpopularAdapter.ViewHolder>{
    List<Food> lstFood;
    IonClickListener clickListener;

    public FoodpopularAdapter(List<Food> lstFood, IonClickListener clickListener) {
        this.lstFood = lstFood;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodPopularBinding itemFoodPopularBinding=ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemFoodPopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food=lstFood.get(position);
        if(food==null)return ;
        GlideUtilis.loadUrlImage(food.getImage(),holder.itemFoodPopularBinding.imgPopular);
        holder.itemFoodPopularBinding.txtsalePopular.setText("Sale off:"+food.getSale()+"%");
        holder.itemFoodPopularBinding.layoutFoodPopular.setOnClickListener(v -> {
            clickListener.onclick(food);
        });
    }

    @Override
    public int getItemCount() {
        return lstFood==null?0:lstFood.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemFoodPopularBinding itemFoodPopularBinding;
        public ViewHolder(@NonNull ItemFoodPopularBinding itemView) {
            super(itemView.getRoot());
            itemFoodPopularBinding=itemView;
        }
    }

}
