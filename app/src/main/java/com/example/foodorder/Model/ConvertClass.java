package com.example.foodorder.Model;

public class ConvertClass {
    public static Food FavoriteToFood(FoodFavorite favorite){
        Food food=new Food();
        food.setId(favorite.getId());
        food.setCount(favorite.getCount());
        food.setImage(favorite.getImage());
        food.setSale(favorite.getSale());
        food.setName(favorite.getName());
        food.setPrice(favorite.getPrice());
        food.setTotalPrice(favorite.getTotalPrice());

        return food;
    }
    public static FoodFavorite FoodToFavorite(Food food){
        FoodFavorite favorite=new FoodFavorite();
        favorite.setId(food.getId());
        favorite.setCount(food.getCount());
        favorite.setImage(food.getImage());
        favorite.setSale(food.getSale());
        favorite.setName(food.getName());
        favorite.setPrice(food.getPrice());
        favorite.setTotalPrice(food.getTotalPrice());

        return favorite;
    }
}
