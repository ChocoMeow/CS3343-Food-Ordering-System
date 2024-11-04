package com.fos.worker;

import com.fos.item.Drink;

public class Bartender {
    private final String name;
    private Drink currentDrink;
    private boolean isBusy = false;
    private long mixingStartTime;
    private int totalHandledDrinks = 0;

    public Bartender(String name) {
        this.name = name;
    }
    
    public void mix(Drink drink) {
        isBusy = true;
        currentDrink = drink;
        mixingStartTime = System.currentTimeMillis();

        // Start mixing in a separate thread and wait for it to finish
        new Thread(() -> {
            drink.mix();
            isBusy = false; // Set busy back to false after mixing
            currentDrink = null; // Reset after mixing
            totalHandledDrinks += 1;
        }).start();
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getName() {
        return name;
    }

    public Drink getCurrentDrink() {
        return currentDrink;
    }

    public long getRemainingMixingTime() {
        if (currentDrink != null) {
            long elapsed = (System.currentTimeMillis() - mixingStartTime) / 1000;
            return Math.max(currentDrink.getMixingTime() - elapsed, 0);
        }
        return 0;
    }

    public int getTotalHandledDrinks() {
        return totalHandledDrinks;
    }
}
