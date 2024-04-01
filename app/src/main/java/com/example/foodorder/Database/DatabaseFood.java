package com.example.foodorder.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.foodorder.Model.Food;

@Database(entities = {Food.class},version = 1)
public abstract class DatabaseFood extends RoomDatabase {
    public static DatabaseFood ins;
    private static final String DATABASE_NAME = "Food.db";
    public static synchronized DatabaseFood getInstance(Context context) {
        if (ins == null) {
            ins = Room.databaseBuilder(context.getApplicationContext(), DatabaseFood.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return ins;
    }

    public abstract FoodDAO foodDAO();

}
