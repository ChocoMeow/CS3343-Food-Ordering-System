package com.fos.main;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fos.drinks.Drink;
import com.fos.foods.Food;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Order {

    private ArrayList<Food> foods;
    private ArrayList<Drink> drinks;

    private LocalDateTime createdTime;

    private boolean isCancelled = false;
    private boolean isEmergency = false;

    private Member customer;

    public Order(ArrayList<Food> foods, ArrayList<Drink> drinks, Member customer) {
        this.foods = foods;
        this.drinks = drinks;
        this.customer = customer;
        this.createdTime = LocalDateTime.now();
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

    public Food getPendingFoodItem() {
        for (Food food : foods) {
            if (food.isPending()) {
                return food;
            }
        }
        return null;
    }

    public Drink getPendingDrinkItem() {
        for (Drink drink : drinks) {
            if (drink.isPending()) {
                return drink;
            }
        }
        return null;
    }

    public String getFoodStatus() {
        if (isCancelled)
            return "Cancelled";

        boolean allPending = true;
        boolean allCompleted = true;

        for (Food food : foods) {
            if (food.isPending())
                allCompleted = false; // Found a pending item
            else
                allPending = false; // Found a completed item
        }

        if (allPending)
            return "Pending";
        else if (allCompleted)
            return "Completed";
        else
            return "In Progress";
    }

    public String getDrinkStatus() {
        if (isCancelled)
            return "Cancelled";

        boolean allPending = true;
        boolean allCompleted = true;

        for (Drink drink : drinks) {
            if (drink.isPending())
                allCompleted = false; // Found a pending item
            else
                allPending = false; // Found a completed item
        }

        if (allPending)
            return "Pending";
        else if (allCompleted)
            return "Completed";
        else
            return "In Progress";
    }

    public boolean isOrderCompleted() {
        return getFoodStatus() == "Completed" && getDrinkStatus() == "Completed";
    }

    public double getExceptedTime() {
        return 0.0;
    }
}
