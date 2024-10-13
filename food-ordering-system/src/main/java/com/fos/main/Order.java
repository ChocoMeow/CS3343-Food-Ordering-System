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

    public long getExpectedFinishTime() {
        long totalCookingTime = foods.stream().filter(f -> !f.isInStock()).mapToLong(Food::getCookingTime).sum();
        return orderTime + totalCookingTime * 1000;
    }

    public long getWaitingTime() {
        return (System.currentTimeMillis() - orderTime) / 1000;
    }

    public boolean isEmergency() {
        return getWaitingTime() > 180; // More than 3 minutes
    }
}