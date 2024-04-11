package com.example.foodorder.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.foodorder.Model.FoodFavorite;

@Database(entities = {FoodFavorite.class},version = 1)
public abstract class DatabaseFavorite extends RoomDatabase {
    public static DatabaseFavorite ins;
    private static final String DATABASE_NAME = "Favorite.db";
    public static synchronized DatabaseFavorite getInstance(Context context) {
        if (ins == null) {
            ins = Room.databaseBuilder(context.getApplicationContext(), DatabaseFavorite.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return ins;
    }

    public abstract FavoriteDAO favoriteDAO();
}