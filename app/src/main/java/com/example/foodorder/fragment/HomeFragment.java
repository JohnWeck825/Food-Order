package com.example.foodorder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.Adapter.FoodpopularAdapter;
import com.example.foodorder.Adapter.GridfoodAdapter;
import com.example.foodorder.Constants.Frag;
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
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;
    private List<Food> mlstFood;
    private GridfoodAdapter gridfoodAdapter;
    private FoodpopularAdapter foodpopularAdapter;
    SearchView searchView;
    private final Handler mHandlerBanner = new Handler(Looper.myLooper());

    private final Runnable mRunnableBanner = new Runnable() {
        @Override
        public void run() {
            if (mlstFood == null || mlstFood.isEmpty()) {
                return;
            }
            if (homeBinding.viewPager2.getCurrentItem() == mlstFood.size() - 1) {
                homeBinding.viewPager2.setCurrentItem(0);
                return;
            }
            homeBinding.viewPager2.setCurrentItem(homeBinding.viewPager2.getCurrentItem() + 1);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding=FragmentHomeBinding.inflate(inflater, container, false);

        homeBinding.searchView.clearFocus();
        homeBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FiltetList(newText);
                return true;
            }
        });

        GetFoodFromFirebase("");
//        AddFoodtoFirebase();
        return homeBinding.getRoot();
    }

    private void FiltetList(String txt) {

        List<Food> filterList=new ArrayList<>();
        if(gridfoodAdapter==null){
            return;
        }
        List<Food> rootList=gridfoodAdapter.getListFood();
        for(Food food:rootList){
            if(food.getName().toLowerCase().contains(txt.toLowerCase())){
                filterList.add(food);
            }
        }
        if(filterList.isEmpty()|| txt.isEmpty()){
            GetFoodFromFirebase("");
        }else{
            gridfoodAdapter.setFilterList(filterList);
        }
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

    void GetFoodFromFirebase(String key){
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
                SetupSlideImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SetupSlideImage() {
        foodpopularAdapter=new FoodpopularAdapter(mlstFood,(Food food)->{
            Bundle bundle=new Bundle();
            bundle.putSerializable("food", food);
            Intent it=new Intent(getActivity(), FoodDetailActivity.class);
            it.putExtra("bundleFood",bundle);
            startActivity(it);
        });

        homeBinding.viewPager2.setPageTransformer(new CompositePageTransformer());
        homeBinding.viewPager2.setAdapter(foodpopularAdapter);
        homeBinding.indicator3.setViewPager(homeBinding.viewPager2);
        homeBinding.indicator3.setClipToOutline(true);

        homeBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandlerBanner.removeCallbacks(mRunnableBanner);
                mHandlerBanner.postDelayed(mRunnableBanner, 3000);
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
                    mlstFood.get(i).setCount(1);

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