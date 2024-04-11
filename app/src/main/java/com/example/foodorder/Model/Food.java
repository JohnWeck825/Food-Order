package com.example.foodorder.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
@Entity(tableName = "tbFood")
public class Food implements Serializable {
    @PrimaryKey
    int id;
    String name;
    String image;
    int price;
    int sale;
    int count;
    int totalPrice;

    public Food() {

    }

    public Food(int id, String name, String image, int price, int sale, int count, int totalPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sale = sale;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getPriceSale(){
        return getPrice()- (getPrice()*getSale())/100;
    }
}
