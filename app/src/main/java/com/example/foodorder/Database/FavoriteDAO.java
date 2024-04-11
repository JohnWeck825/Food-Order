package com.example.foodorder.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodorder.Model.Food;
import com.example.foodorder.Model.FoodFavorite;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Insert
    void insertFavorite(FoodFavorite food);

    @Query("SELECT * FROM tbFoodFavorite")
    List<FoodFavorite> getListFoodFavorite();

    @Query("SELECT * FROM tbFoodFavorite WHERE id=:id")
    List<FoodFavorite> checkFoodInFavorite(int id);

    @Delete
    void deleteFood(FoodFavorite food);

    @Update
    void updateFavorite(FoodFavorite food);

    @Query("DELETE from tbFoodFavorite")
    void deleteAllFood();
}
