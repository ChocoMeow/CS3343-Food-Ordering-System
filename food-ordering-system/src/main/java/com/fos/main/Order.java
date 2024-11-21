package com.fos.main;

import java.util.ArrayList;
import java.util.List;

import com.fos.item.Drink;
import com.fos.item.Food;

public class Order {
    private final List<Food> foods;
    private final List<Drink> drinks;
    private final long orderTime;

    public Order() {
        this.foods = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.orderTime = System.currentTimeMillis();
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public List<Food> getFoods() {
        return foods;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public float getTotalCost() {
        float totalCost = 0;
        for (Food food : foods) {
            totalCost += food.getPrice();
        }
        for (Drink drink : drinks) {
            totalCost += drink.getPrice();
        }
        return totalCost;
    }

    public long getTotalPerparingTime() {
        long totalCookingTime = foods.stream().filter(f -> !f.isInStock()).mapToLong(Food::getCookingTime).sum();
        long totalMixingTime = drinks.stream().filter(f -> !f.isInStock()).mapToLong(Drink::getMixingTime).sum();
        return (totalCookingTime * 1000) + (totalMixingTime * 1000);
    }

    public long getExpectedFinishTime() {
        return orderTime + this.getTotalPerparingTime();
    }

    public long getWaitingTime() {
        return (System.currentTimeMillis() - orderTime) / 1000;
    }

    public boolean isEmergency() {
        return getWaitingTime() > 180; // More than 3 minutes
    }
}