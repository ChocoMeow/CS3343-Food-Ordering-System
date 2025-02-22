package com.fos.item;

import lombok.ToString;

@ToString
public class Drink {
    private String name;
    private float price;
    private int mixingTime;
    private int stock;

    public Drink(String name, float price, int mixingTime, int stock) {
        this.name = name;
        this.price = price;
        this.mixingTime = mixingTime;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
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
        stock++;
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

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPrice(Float newPrice) {
        this.price = newPrice;
    }

    public void setMixingTime(Integer newMixingTime) {
        this.mixingTime = newMixingTime;
    }

    public void setStock(Integer newStock) {
        this.stock = newStock;
    }
}