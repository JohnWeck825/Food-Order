package com.example.foodorder.Adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Constants.Constant;

import com.example.foodorder.EventClickHandle.IonClickListener;
import com.example.foodorder.Model.Food;
import com.example.foodorder.databinding.ItemGridFoodBinding;
import com.example.foodorder.utils.GlideUtilis;

import java.util.List;

public class GridfoodAdapter extends RecyclerView.Adapter<GridfoodAdapter.ViewHolder>{
//    public static  GridfoodAdapter ins;
    List<Food> mlistFood;
    Activity context;
    IonClickListener onClickItem;
    public GridfoodAdapter(List<Food> lstFood, IonClickListener click) {
        this.mlistFood = lstFood;
        this.onClickItem=click;
        notifyDataSetChanged();
    }
    public List<Food> getListFood(){
        return mlistFood;
    }

    public void setFilterList(List<Food> filterList){
        this.mlistFood=filterList;
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
        holder.mItemGrid.txtSaleoff.setText("Sale off: "+food.getSale()+"% ");
        holder.mItemGrid.tvFoodname.setText(food.getName());
        holder.mItemGrid.tvprice.setText(food.getPrice()+ Constant.CURRENCY);
        holder.mItemGrid.tvprice.setPaintFlags(holder.mItemGrid.tvprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.mItemGrid.tvsaleprice.setText(food.getPriceSale()+Constant.CURRENCY);
        holder.mItemGrid.layoutItem.setOnClickListener(v -> {
            onClickItem.onclick(food);
        });
    }
    void action(){}

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
