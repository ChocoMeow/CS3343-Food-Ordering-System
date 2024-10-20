package com.fos.member;

import com.fos.item.Drink;

public class Bartender {
    private final String name;
    private Drink currentDrink;
    private boolean isBusy = false;
    private long mixingStartTime;

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
            isBusy = false;
            currentDrink = null;
        }).start();

        isBusy = false;  // Set busy back to false after mixing
        currentDrink = null; // Reset after mixing
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
}
