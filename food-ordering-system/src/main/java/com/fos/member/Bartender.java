package com.fos.member;

import com.fos.item.Drink;

public class Bartender {
    private final String name;
    private Drink currentDrink;
    private boolean isBusy = false;

    public Bartender(String name) {
        this.name = name;
    }

    public void mix(Drink drink) {
        isBusy = true;
        currentDrink = drink;

        // Start mixing in a separate thread and wait for it to finish
        Thread mixingThread = new Thread(() -> {
            System.out.printf("Bartender %s is mixing %s.%n", name, drink.getName());
            System.out.println(drink.getName() + " is ready!");
        });
        mixingThread.start();
        try {
            mixingThread.join();  // Wait for mixing to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

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
}
