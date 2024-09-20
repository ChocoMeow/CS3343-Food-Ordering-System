package com.fos.member;

import com.fos.drinks.Drink;

public class Bartender extends Member {
    private Drink processDrink;

    public Bartender(String name) {
        super(name);
    }

    public boolean isAvailable() {
        return processDrink == null;
    }

    public void assign(Drink drink) {
        this.processDrink = drink;
    }

    @Override
    public String getType() {
        return "Bartender";
    }

}
