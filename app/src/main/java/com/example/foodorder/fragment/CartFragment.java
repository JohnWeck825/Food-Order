package com.example.foodorder.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodorder.Adapter.CartAdapter;
import com.example.foodorder.Constant;
import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Database.DatabaseFood;
import com.example.foodorder.Model.Food;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding cartBinding;
    private CartAdapter cartAdapter;
    private List<Food> listFoods;
    private int amount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        InitToolbar();
        GetDataFoodCart();
    }

    private void InitToolbar(){
        if(getActivity()!=null){
            ((MainActivity)getActivity()).setToolBar(Frag.CART,"Food");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding=FragmentCartBinding.inflate(inflater,container,false);

        GetDataFoodCart();
        return cartBinding.getRoot();
    }
    void GetDataFoodCart(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cartBinding.rcvFoodCart.setLayoutManager(linearLayoutManager);
        listFoods= DatabaseFood.getInstance(this.getActivity()).foodDAO().getListFoodCart();
        if(listFoods==null || listFoods.isEmpty())return;
        cartAdapter=new CartAdapter(listFoods, new CartAdapter.IClickListener() {
            @Override
            public void clickDeteteFood(Food food, int position) {
                DeleteFoodCart(food,position);
            }


            @Override
            public void updateItemFood(Food food, int position) {
                UpdateFoodItem(food,position);
            }
        });

        cartBinding.rcvFoodCart.setAdapter(cartAdapter);
        UpdateTotalPrice();
    }

    private void UpdateFoodItem(Food food, int position) {
        DatabaseFood.getInstance(getActivity()).foodDAO().updateFood(food);
        cartAdapter.notifyItemChanged(position);
        UpdateTotalPrice();
    }

    private void UpdateTotalPrice() {
        List<Food> lst=DatabaseFood.getInstance(getActivity()).foodDAO().getListFoodCart();
        if(lst==null ||lst.isEmpty()){
            cartBinding.tvTotalPrice.setText(0+ Constant.CURRENCY);
            return;
        }
        int totalprice=0;
        for(Food food:lst){
            totalprice+=food.getPriceSale()*food.getCount();
        }
        cartBinding.tvTotalPrice.setText(totalprice+ Constant.CURRENCY);
    }

    private void DeleteFoodCart(Food food, int position) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
        alert.setTitle("Thông Báo");
        alert.setMessage("Bạn Có Chắc Muốn Xóa");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseFood.getInstance(getActivity()).foodDAO().deleteFood(food);
                listFoods.remove(position);
                cartAdapter.notifyItemChanged(position);
                UpdateTotalPrice();
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