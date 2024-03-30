package com.example.foodorder.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import com.example.foodorder.Adapter.GridfoodAdapter;
import com.example.foodorder.Constants.Frag;
import com.example.foodorder.Database.DatabaseFood;
import com.example.foodorder.EventClickHandle.IonClickListioner;
import com.example.foodorder.Model.Food;
import com.example.foodorder.activity.FoodDetailActivity;
import com.example.foodorder.activity.MainActivity;
import com.example.foodorder.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;
    private List<Food> mlstFood;
    private GridfoodAdapter gridfoodAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding=FragmentHomeBinding.inflate(inflater, container, false);
        GetFoodFromFirebase();
//        AddFoodtoFirebase();
        return homeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        InitToolbar();

    }
    private void InitToolbar(){
        if(getActivity()!=null){
        ((MainActivity)getActivity()).setToolBar(Frag.HOME,"Food");

        }
    }

    void GetFoodFromFirebase(){
        this.mlstFood=new ArrayList<>();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dbReference=firebaseDatabase.getReference("food");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlstFood.clear();
                for(DataSnapshot foodSnap:snapshot.getChildren()){
                    Food food=foodSnap.getValue(Food.class);
                    mlstFood.add(food);

                }

                gridfoodAdapter=new GridfoodAdapter(mlstFood,(Food food)->{
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("food", food);
                    Intent it=new Intent(getActivity(), FoodDetailActivity.class);
                    it.putExtra("bundleFood",bundle);
                    startActivity(it);
                });

                GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                homeBinding.rcvGridfood.setLayoutManager(gridLayoutManager);

                homeBinding.rcvGridfood.setAdapter(gridfoodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void AddFoodtoFirebase(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dbReference=firebaseDatabase.getReference("food");
        mlstFood=new ArrayList<>();
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlstFood.clear();
                for(DataSnapshot foodSnap:snapshot.getChildren()){
                    Food food=foodSnap.getValue(Food.class);
                    mlstFood.add(food);
                }

                for(int i=0;i<mlstFood.size();i++){
                    Random rd=new Random();
                    int price=rd.nextInt(1000)+100;
                    int sale=rd.nextInt(20);
                    mlstFood.get(i).setPrice(price);
                    mlstFood.get(i).setSale(sale);

                }
                if(mlstFood.size()==20)
                    dbReference.setValue(mlstFood);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}