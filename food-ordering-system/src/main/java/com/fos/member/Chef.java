package com.fos.member;

import com.fos.foods.Food;

public class Chef extends Member {
    private Food processFood;

    public Chef(String name) {
        super(name);
    }

    public boolean isAvailable() {
        return processFood == null;
    }

    public void assign(Food food) {
        this.processFood = food;
    }

    @Override
    public String getType() {
        return "Cherf";
    }
    
}
