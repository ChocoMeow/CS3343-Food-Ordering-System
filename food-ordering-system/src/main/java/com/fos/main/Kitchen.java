package com.fos.main;

import java.util.ArrayList;

import com.fos.drinks.Drink;
import com.fos.foods.Food;
import com.fos.member.Bartender;
import com.fos.member.Chef;

public class Kitchen {
    private ArrayList<Chef> chefs = new ArrayList<Chef>();
    private ArrayList<Bartender> bartenders = new ArrayList<Bartender>();

    public Kitchen() {
        
    }

    public void assign(Food food) {
        for (Chef chef : chefs) {
            if (chef.isAvailable()) {
                chef.assign(food);
            }
        }
    }

    public void assign(Drink drink) {
        for (Bartender bartender : bartenders) {
            if (bartender.isAvailable()) {
                bartender.assign(drink);
            }
        }
    }
    
}