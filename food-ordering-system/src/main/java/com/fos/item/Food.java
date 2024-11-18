package com.fos.item;

import lombok.ToString;

@ToString
public class Food {
    private String name;
    private float price;
    private int cookingTime;
    private int stock;

    public Food(String name, float price, int cookingTime, int stock) {
        this.name = name;
        this.price = price;
        this.cookingTime = cookingTime;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getStock(){
        return stock;
    }

    public int getCookingTime() {
        return cookingTime;
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

    public void cook() {
        // System.out.printf("Cooking %s for %d seconds...%n", name, cookingTime);
        try {
            Thread.sleep(cookingTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        restock(); // Add back to stock after cooking
        // System.out.printf("%s is ready and added to stock!%n", name);
        use();
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPrice(Float newPrice) {
        this.price = newPrice;
    }

    public void setCookingTime(Integer newCookingTime) {
        this.cookingTime = newCookingTime;
    }

    public void setStock(Integer newStock) {
        this.stock = newStock;
    }
}