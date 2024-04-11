package com.example.foodorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Database.Constant;
import com.example.foodorder.Database.DatabaseFavorite;
import com.example.foodorder.EventClickHandle.IonClickListener;
import com.example.foodorder.Model.ConvertClass;
import com.example.foodorder.Model.Food;
import com.example.foodorder.Model.FoodFavorite;
import com.example.foodorder.databinding.ItemFavoriteBinding;
import com.example.foodorder.databinding.ItemFoodPopularBinding;
import com.example.foodorder.utils.GlideUtilis;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    List<Food> lstFood;
    IonClickListener clickListener;
    IClickDelete clickDelete;
    Context context;
    public interface IClickDelete {
        void clickDeteteFood(Food food, int position);

    }
    public FavoriteAdapter(List<Food> lstFood ,IonClickListener clickListener,IClickDelete clickdelete) {
        this.lstFood = lstFood;
        this.clickListener=clickListener;
        this.clickDelete=clickdelete;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteBinding itemFavoriteBinding=ItemFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FavoriteAdapter.ViewHolder(itemFavoriteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food=lstFood.get(position);
        if(food==null)return ;
        GlideUtilis.loadUrlImage(food.getImage(),holder.itemFavoriteBinding.imgItemFavorite);
        holder.itemFavoriteBinding.tvFname.setText(food.getName());
        holder.itemFavoriteBinding.tvFprice.setText(food.getPrice()+Constant.CURRENCY);
        holder.itemFavoriteBinding.tvFpricesale.setText(food.getPriceSale()+ Constant.CURRENCY);

        holder.itemFavoriteBinding.btnDeleteFavorite.setOnClickListener(v ->
                clickDelete.clickDeteteFood(food,holder.getAdapterPosition()));

        holder.itemFavoriteBinding.layoutItemFavorite.setOnClickListener(v -> {
            clickListener.onclick(food);
        });
    }

    @Override
    public int getItemCount() {
        return lstFood!=null?lstFood.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemFavoriteBinding itemFavoriteBinding;
        public ViewHolder(@NonNull ItemFavoriteBinding itemView) {
            super(itemView.getRoot());
            itemFavoriteBinding=itemView;
        }
    }
}
