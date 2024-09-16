package com.fos.drinks;

public abstract class Drink {
    private double price;
    private double prepareTime;
    private int production;

    private int availableInventory = 0;

    public Drink(double price, double prepareTime, int production) {
        this.price = price;
        this.prepareTime = prepareTime;
        this.production = production;
    }

    public double getPrice() {
        return price;
    }
    
    public double getPrepareTime() {
        return prepareTime;
    }

    public int getAvailableInventory() {
        return availableInventory;
    }

    public void make() {
        this.availableInventory =+ this.production;
    }
}
