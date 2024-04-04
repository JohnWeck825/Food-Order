package com.example.foodorder.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodorder.Adapter.CartAdapter;
import com.example.foodorder.Constants.Constant;

import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Database.DatabaseFood;
import com.example.foodorder.Model.Food;
import com.example.foodorder.Model.Order;
import com.example.foodorder.R;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentCartBinding;
import com.example.foodorder.function.ContactFunction;
import com.example.foodorder.utils.StringUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private void InitToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(Frag.CART, "Food");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding = FragmentCartBinding.inflate(inflater, container, false);
        GetDataFoodCart();
        cartBinding.tvOrderCart.setOnClickListener(v -> onClickOrderCart());
        return cartBinding.getRoot();
    }


    void GetDataFoodCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cartBinding.rcvFoodCart.setLayoutManager(linearLayoutManager);
        listFoods = DatabaseFood.getInstance(this.getActivity()).foodDAO().getListFoodCart();
        if (listFoods == null || listFoods.isEmpty()) return;
        cartAdapter = new CartAdapter(listFoods, new CartAdapter.IClickListener() {
            @Override
            public void clickDeteteFood(Food food, int position) {
                DeleteFoodCart(food, position);
            }


            @Override
            public void updateItemFood(Food food, int position) {
                UpdateFoodItem(food, position);
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

    @SuppressLint("NotifyDataSetChanged")
    private void clearCartList(){
        if(listFoods!=null){
            listFoods.clear();
        }
        cartAdapter.notifyDataSetChanged();
        UpdateTotalPrice();
    }

    private void UpdateTotalPrice() {
        List<Food> lst = DatabaseFood.getInstance(getActivity()).foodDAO().getListFoodCart();
        if (lst == null || lst.isEmpty()) {
            cartBinding.tvTotalPrice.setText(0 + Constant.CURRENCY);
            return;
        }
        int totalprice = 0;
        for (Food food : lst) {
            totalprice += food.getPriceSale() * food.getCount();
        }
        cartBinding.tvTotalPrice.setText(totalprice + Constant.CURRENCY);
        amount = totalprice;
    }

    private void DeleteFoodCart(Food food, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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


    private void onClickOrderCart() {
        if (getActivity() == null) {
            return;
        }
        if (listFoods == null || listFoods.isEmpty()) {
            return;
        }

        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_order, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        TextView tvFoodOrderDetail = viewDialog.findViewById(R.id.food_order_details);
        TextView tvOrderPrice = viewDialog.findViewById(R.id.order_price);
        TextView edtOrderName = viewDialog.findViewById(R.id.order_name);
        TextView edtOrderPhone = viewDialog.findViewById(R.id.order_phone);
        TextView edtOrderAddress = viewDialog.findViewById(R.id.order_address);
        TextView tvCancelOrder = viewDialog.findViewById(R.id.tv_cancel_order);
        TextView tvCreateOrder = viewDialog.findViewById(R.id.tv_create_order);

        tvFoodOrderDetail.setText(getStringFoodOrderList());
        tvOrderPrice.setText(cartBinding.tvTotalPrice.getText().toString());

        tvCancelOrder.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvCreateOrder.setOnClickListener(v -> {
            String strOrderName = edtOrderName.getText().toString().trim();
            String strOrderPhone = edtOrderPhone.getText().toString().trim();
            String strOrderAddress = edtOrderAddress.getText().toString().trim();

            if (StringUtils.isEmpty(strOrderName) || StringUtils.isEmpty(strOrderPhone) || StringUtils.isEmpty(strOrderAddress)){
                ContactFunction.showToastMessage(getActivity(), "Vui lòng điền đầy đủ thông tin giao hàng!");
            } else{
                long orderID = System.currentTimeMillis();
                Order order = new Order(orderID, strOrderName, strOrderPhone, strOrderAddress,
                        amount, getStringFoodOrderList(), Constant.TYPE_PAYMENT_CASH);
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = firebaseUser.getUid();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(uid).child("orders");
                databaseReference.push().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ContactFunction.showToastMessage(getActivity(), "Đặt hàng thành công");
                        bottomSheetDialog.dismiss();
                        DatabaseFood.getInstance(getActivity()).foodDAO().deleteAllFood();
                        clearCartList();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
        bottomSheetDialog.show();
    }

    private String getStringFoodOrderList() {
        if (listFoods == null || listFoods.isEmpty()) {
            return "";
        }
        String orderResult = "";
        for (Food food : listFoods) {
            if (StringUtils.isEmpty(orderResult)) {
                orderResult = "- " + food.getName() + " (" + food.getPrice() + Constant.CURRENCY + ") "
                        + "- " + "Số lượng: " + food.getCount();
            } else {
                orderResult = orderResult + "\n" + "- " + food.getName() + " (" + food.getPrice() + Constant.CURRENCY + ") "
                        + "- " + "Số lượng: " + food.getCount();
            }
        }
        return orderResult;
    }
}