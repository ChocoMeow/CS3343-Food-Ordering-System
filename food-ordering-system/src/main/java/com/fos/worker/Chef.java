package com.fos.worker;

import com.fos.item.Food;

public class Chef {
    private final String name;
    private Food currentFood;
    private boolean isBusy = false;
    private long cookingStartTime;
    private int totalHandledFoods = 0;

    public Chef(String name) {
        this.name = name;
    }

    public void cook(Food food) {
        isBusy = true;
        currentFood = food;
        cookingStartTime = System.currentTimeMillis();

        // Start cooking in a separate thread
        new Thread(() -> {
            food.cook(); // This will now run in its own thread
            isBusy = false; // Set busy back to false after cooking
            currentFood = null; // Reset after cooking
            totalHandledFoods++;
        }).start();
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getName() {
        return name;
    }

    public Food getCurrentFood() {
        return currentFood;
    }

    public long getRemainingCookingTime() {
        if (currentFood != null) {
            long elapsed = (System.currentTimeMillis() - cookingStartTime) / 1000;
            return Math.max(currentFood.getCookingTime() - elapsed, 0);
        }
        return 0;
    }

    public int getTotalHandledFoods() {
        return totalHandledFoods;
    }

}
