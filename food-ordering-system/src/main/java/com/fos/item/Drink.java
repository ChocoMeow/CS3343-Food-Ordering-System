package com.fos.item;

public class Drink {
    private final String name;
    private int stock;

    public Drink(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public void use() {
        if (stock > 0) {
            stock--;
        }
    }
}