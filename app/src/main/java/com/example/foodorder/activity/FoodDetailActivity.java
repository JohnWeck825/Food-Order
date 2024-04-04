package com.example.foodorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorder.Constants.Constant;

import com.example.foodorder.Database.DatabaseFood;
import com.example.foodorder.Model.Food;
import com.example.foodorder.R;
import com.example.foodorder.databinding.ActivityFoodDetailBinding;
import com.example.foodorder.utils.GlideUtilis;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
    ActivityFoodDetailBinding foodDetailBinding;
    Food mfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodDetailBinding=ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(foodDetailBinding.getRoot());
        getDataIntent();
        SetupLayout();
        InitToolbar();
        InitListener();
        SetStateBtnAddCart();

    }


    private void getDataIntent(){
        Intent it=getIntent();
        Bundle bundle=it.getBundleExtra("bundleFood");
        mfood=(Food)bundle.getSerializable("food");
    }
    private void SetupLayout(){
        GlideUtilis.loadUrlImage(mfood.getImage(),foodDetailBinding.imgDisplay);
        foodDetailBinding.tvName.setText(mfood.getName());
        foodDetailBinding.tvPrice.setText(mfood.getPrice()+ Constant.CURRENCY);
        foodDetailBinding.tvPrice.setPaintFlags(foodDetailBinding.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        foodDetailBinding.tvPricesale.setText(mfood.getPriceSale()+ Constant.CURRENCY);
        foodDetailBinding.tvSale.setText("Giảm giá" +mfood.getSale()+"%");
    }
    private void InitToolbar(){
        foodDetailBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        foodDetailBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        foodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);

    }
    private void InitListener() {
        foodDetailBinding.toolbar.imgCart.setOnClickListener(v->AddtoCart());
        foodDetailBinding.btnAddcard.setOnClickListener(v->AddtoCart());
        foodDetailBinding.toolbar.imgBack.setOnClickListener(v->onBackPressed());

    }
    private void AddtoCart(){
        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_cart, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        ImageView imgFood=viewDialog.findViewById(R.id.imageSheet);
        TextView name=viewDialog.findViewById(R.id.txt_name);
        TextView price=viewDialog.findViewById(R.id.txt_price);
        Button cancel=viewDialog.findViewById(R.id.btnCancel);
        Button addtocard=viewDialog.findViewById(R.id.btnAddtoCart);
        TextView btnminus=viewDialog.findViewById(R.id.btn_minus);
        TextView btnplus=viewDialog.findViewById(R.id.btnplus);
        TextView number=viewDialog.findViewById(R.id.txt_number);
        number.setText(mfood.getCount()+"");

        GlideUtilis.loadUrlImage(mfood.getImage(),imgFood);
        name.setText(mfood.getName()+"");
        price.setText(mfood.getPriceSale()+Constant.CURRENCY);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        addtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseFood.getInstance(FoodDetailActivity.this).foodDAO().insertFood(mfood);
                SetStateBtnAddCart();
                Toast.makeText(FoodDetailActivity.this,"Added to Cart",Toast.LENGTH_LONG).show();

            }
        });
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(number.getText().toString());
                if(count<=1)
                    return;
                count-=1;
                number.setText(count+"");
                mfood.setCount(count);
                price.setText(mfood.getCount()*mfood.getPriceSale()+""+Constant.CURRENCY);
            }
        });
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(number.getText().toString());
                count+=1;
                number.setText(count+"");
                mfood.setCount(count);
                price.setText(mfood.getCount()*mfood.getPriceSale()+Constant.CURRENCY);
            }
        });


        bottomSheetDialog.show();
    }
    private boolean CheckFoodInCart(){
        List<Food> lst= DatabaseFood.getInstance(this).foodDAO().checkFoodInCart(mfood.getId());
        return lst!=null && lst.size()>0;
    }
    private void SetStateBtnAddCart(){
        if(CheckFoodInCart()){
            foodDetailBinding.btnAddcard.setText("Đã Thêm Vào Giỏ Hàng");
            foodDetailBinding.btnAddcard.setBackgroundResource(R.drawable.bg_btn_unactive);
            foodDetailBinding.btnAddcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FoodDetailActivity.this,"Đã có trong giỏ hàng",Toast.LENGTH_LONG).show();
                }
            });
            foodDetailBinding.toolbar.imgCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FoodDetailActivity.this,"Đã có trong giỏ hàng",Toast.LENGTH_LONG).show();
                }
            });
//            foodDetailBinding.btnAddcard.setVisibility(View.GONE);
        }
        else {
            foodDetailBinding.btnAddcard.setText("Thêm Vào Giỏ Hàng");
            foodDetailBinding.btnAddcard.setBackgroundResource(R.drawable.bg_btn_active);
        }
    }


}