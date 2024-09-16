package com.fos.main;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fos.drinks.Drink;
import com.fos.foods.Food;

public abstract class Order {

    private ArrayList<Food> foods;
    private ArrayList<Drink> drinks;

    private LocalDateTime createdTime;

    private int status = 0;
    private int type = 0;

    private Member customer;

    public Order(ArrayList<Food> foods, ArrayList<Drink> drinks, Member customer) {
        this.foods = foods;
        this.drinks = drinks;
        this.customer = customer;
        
        this.createdTime = LocalDateTime.now();
    }

    public void updateStatus(int status) {
        if (status < 0 || status > 3) {
            new Exception("Invalid status code. Please enter a number between 0 and 3!");
        }
        this.status = status;
    }

    public void updateType(int type) {
        if (status < 1 || status > 2) {
            new Exception("Invalid type code. Please enter a number 0 or 1!");
        }
        this.type = type;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public Member getCustomer() {
        return this.customer;
    }
    
    public double getTotalPrice() {
        double totalPrice = 0;

        // Loop through foods and add their prices
        for (Food food : foods) {
            totalPrice += food.getPrice();
        }

        // Loop through drinks and add their prices
        for (Drink drink : drinks) {
            totalPrice += drink.getPrice();
        }
        return totalPrice;
    }

    public String getStatus() {
        switch (this.status) {
            case 1:
                return "In Progress";

            case 2:
                return "Cancelled";

            case 3:
                return "Completed";

            default:
                return "Pending";
        }
    }

    public String getType() {
        switch (this.type) {
            case 1:
                return "Emergency";
        
            default:
                return "Normal";
        }
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public double getExceptedTime() {
        return 0.0;
    }
}
