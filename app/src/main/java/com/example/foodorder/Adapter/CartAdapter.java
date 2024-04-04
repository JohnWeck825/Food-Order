package com.example.foodorder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Database.Constant;
import com.example.foodorder.Model.Food;
import com.example.foodorder.databinding.ItemCartBinding;
import com.example.foodorder.utils.GlideUtilis;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    List<Food> lstFoodCart;
    IClickListener clickListener;
    public interface IClickListener {
        void clickDeteteFood(Food food, int position);

        void updateItemFood(Food food, int position);
    }
    public CartAdapter(List<Food> lstFoodCart,IClickListener clickListener) {
        this.lstFoodCart = lstFoodCart;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding itemCartBinding=ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food=lstFoodCart.get(position);
        GlideUtilis.loadUrlImage(food.getImage(), holder.itemCartBinding.imgFoodCart);
        holder.itemCartBinding.tvFoodNameCart.setText(food.getName().toString());
        holder.itemCartBinding.tvCount.setText(food.getCount()+"");
        holder.itemCartBinding.tvFoodPriceCart.setText(food.getPriceSale()*food.getCount()+ Constant.CURRENCY);
        holder.itemCartBinding.tvSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(holder.itemCartBinding.tvCount.getText().toString());
                if(count<=1)
                    return;
                count-=1;
                food.setCount(count);
                holder.itemCartBinding.tvCount.setText(count+"");
                clickListener.updateItemFood(food,holder.getAdapterPosition());
            }
        });
        holder.itemCartBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(holder.itemCartBinding.tvCount.getText().toString());
                count+=1;
                food.setCount(count);
                holder.itemCartBinding.tvCount.setText(count+"");
                clickListener.updateItemFood(food,holder.getAdapterPosition());
            }
        });
        holder.itemCartBinding.tvDelete.setOnClickListener(v ->
            clickListener.clickDeteteFood(food, holder.getAdapterPosition()));

    }

    @Override
    public int getItemCount() {
        return lstFoodCart==null?0: lstFoodCart.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding itemCartBinding;
        public ViewHolder(@NonNull ItemCartBinding itemView) {
            super(itemView.getRoot());
            itemCartBinding=itemView;
        }
    }
}
