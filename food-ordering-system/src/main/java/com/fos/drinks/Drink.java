package com.fos.drinks;

import com.fos.foods.Food;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Drink {

    private int amount;
    private boolean isCompleted = false;
    
    public static final double price = 10;
    public static final int production = 1;
    public static final double prepareTime = 10;

    public static double remainingTime;
    public static boolean isAvailable = true;
    public static int availableInventory = 0;

    public Drink(int amount) {
        this.amount = amount;
    }

    public boolean isPending() {
        return !isCompleted;
    }

    public void cook(double time) {
        remainingTime -= time;
    }

    public double getPrice() {
        return Drink.price;
    }

    public void make() {
        Drink.availableInventory =+ production;
    }

    public int getAvailableInventory() {
        return Drink.availableInventory;
    }
}
