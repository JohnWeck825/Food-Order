package com.example.foodorder.Database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorder.Model.Food;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FoodDAO {
    @Insert
    void insertFood(Food food);

    @Query("SELECT * FROM tbFood")
    List<Food> getListFoodCart();

    @Query("SELECT * FROM tbFood WHERE id=:id")
    List<Food> checkFoodInCart(int id);

    @Delete
    void deleteFood(Food food);

    @Update
    void updateFood(Food food);

    @Query("DELETE from tbFood")
    void deleteAllFood();
}
