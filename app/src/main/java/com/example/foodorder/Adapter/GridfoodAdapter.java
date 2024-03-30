package com.example.foodorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Constant;
import com.example.foodorder.Model.Food;
import com.example.foodorder.databinding.ItemGridFoodBinding;
import com.example.foodorder.utils.GlideUtilis;

import java.util.ArrayList;
import java.util.List;

public class GridfoodAdapter extends RecyclerView.Adapter<GridfoodAdapter.ViewHolder>{
    ArrayList<Food> mlistFood;

    public GridfoodAdapter(ArrayList<Food> lstFood) {
        this.mlistFood = lstFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGridFoodBinding itemGridFoodBinding=ItemGridFoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemGridFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food=mlistFood.get(position);
        GlideUtilis.loadUrlImage(food.getImage(),holder.mItemGrid.imgFood);
        holder.mItemGrid.txtSaleoff.setText(food.getSale()+"%");
        holder.mItemGrid.tvFoodname.setText(food.getName());
        holder.mItemGrid.tvprice.setText(food.getPrice()+ Constant.CURRENCY);
        holder.mItemGrid.tvsaleprice.setText(food.getPriceSale()+Constant.CURRENCY);
    }

    @Override
    public int getItemCount() {
        if(mlistFood!=null)
            return mlistFood.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemGridFoodBinding mItemGrid;
        public ViewHolder(ItemGridFoodBinding itemView) {
            super(itemView.getRoot());
            mItemGrid=itemView;
        }
    }
}
