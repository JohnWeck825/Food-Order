package com.example.foodorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Constants.Constant;
import com.example.foodorder.Model.Order;
import com.example.foodorder.databinding.ItemHistoryBinding;
import com.example.foodorder.utils.DateTimeUtils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Order> historyList;

    public HistoryAdapter(List<Order> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding itemHistoryBinding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(itemHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Order order = historyList.get(position);
        if (order == null) {
            return;
        }

        holder.mItemHistoryBinding.htrOrderId.setText(String.valueOf(order.getId()));
        holder.mItemHistoryBinding.htrOrderName.setText(order.getName());
        holder.mItemHistoryBinding.htrOrderPhone.setText(order.getPhone());
        holder.mItemHistoryBinding.htrOrderAddress.setText(order.getAddress());
        holder.mItemHistoryBinding.htrOrderDish.setText(order.getFoods());
        holder.mItemHistoryBinding.htrOrderDate.setText(DateTimeUtils.convertTimeStampToDate(order.getId()));

        String strTotalPrice = order.getAmount() + Constant.CURRENCY;
        holder.mItemHistoryBinding.htrOrderPrice.setText(strTotalPrice);

        String paymentMethod = "";
        if(Constant.TYPE_PAYMENT_CASH == order.getPayment()){
            paymentMethod = Constant.PAYMENT_METHOD_CASH;
        }
        holder.mItemHistoryBinding.htrOrderPay.setText(paymentMethod);
    }

    @Override
    public int getItemCount() {
        return historyList == null ? 0 : historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding mItemHistoryBinding;

        public HistoryViewHolder(@NonNull ItemHistoryBinding mItemHistoryBinding) {
            super(mItemHistoryBinding.getRoot());
            this.mItemHistoryBinding = mItemHistoryBinding;
        }
    }
}
