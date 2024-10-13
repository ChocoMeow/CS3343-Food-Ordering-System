package com.fos.item;

public class Food {
    private final String name;
    private final int cookingTime;
    private int stock;

    public Food(String name, int cookingTime, int stock) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.stock = stock;
    }

    public String getName() {
        return name;
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
    }
}