package com.fos.item;

public class Drink {
    private final String name;
    private int mixingTime;
    private int stock;

    public Drink(String name, int mixingTime, int stock) {
        this.name = name;
        this.mixingTime = mixingTime;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getMixingTime() {
        return mixingTime;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public void use() {
        if (stock > 0) {
            stock--;
        }
    }

    public void restock() {
        stock ++;
    }

    public void mix() {
        try {
            Thread.sleep(mixingTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        restock();
        use();
    }
}