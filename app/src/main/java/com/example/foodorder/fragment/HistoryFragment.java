package com.example.foodorder.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorder.Adapter.HistoryAdapter;
import com.example.foodorder.Model.Order;
import com.example.foodorder.databinding.FragmentHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding fragmentHistoryBinding;
    private List<Order> historyList;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false);
        setupUI();
        getListOrders();
        return fragmentHistoryBinding.getRoot();
    }

    private void setupUI() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentHistoryBinding.rcvOrderHistory.setLayoutManager(linearLayoutManager);

        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        fragmentHistoryBinding.rcvOrderHistory.setAdapter(historyAdapter);
    }

    private void getListOrders() {
        if (getActivity() == null) {
            return;
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(uid).child("orders");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if (order == null | historyList == null || historyAdapter == null) {
                    return;
                }
                historyList.add(0, order);
                historyAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if (order == null | historyList == null || historyList.isEmpty() || historyAdapter == null) {
                    return;
                }
                for (int i = 0; i < historyList.size(); i++) {
                    if (order.getId() == historyList.get(i).getId()) {
                        historyList.set(i, order);
                        break;
                    }
                }
                historyAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                if (order == null | historyList == null || historyList.isEmpty() || historyAdapter == null) {
                    return;
                }
                for (Order orderObject : historyList) {
                    if(order.getId()==orderObject.getId()){
                        historyList.remove(orderObject);
                        break;
                    }
                }
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot orderSnapshot : snapshot.getChildren()){
//                    Order order = snapshot.getValue(Order.class);
//                    if (order == null | historyList == null || historyList.isEmpty() || historyAdapter == null) {
//                        return;
//                    }
//                    for (int i = 0; i < historyList.size(); i++) {
//                        if (order.getId() == historyList.get(i).getId()) {
//                            historyList.set(i, order);
//                            break;
//                        }
//                    }
//                    historyAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}