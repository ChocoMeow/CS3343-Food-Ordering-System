package com.fos.main;

import java.util.ArrayList;

import com.fos.drinks.Drink;
import com.fos.foods.Food;
import com.fos.member.Bartender;
import com.fos.member.Chef;

public class Kitchen {
    public static ArrayList<Chef> chefs = new ArrayList<Chef>();
    public static ArrayList<Bartender> bartenders = new ArrayList<Bartender>();

    public static void addNewChef(int amount) {
        for (int i = 0; i < amount; i++) {
            chefs.add(new Chef(null));
        }
    }
    
    public static void addNewBartender(int amount) {
        for (int i = 0; i < amount; i++) {
            bartenders.add(new Bartender(null));
        }
    }

    public static void assign(Food food) {
        for (Chef chef : chefs) {
            if (chef.isAvailable()) {
                chef.assign(food);
                return;
            }
        }
    }

    public static void assign(Drink drink) {
        for (Bartender bartender : bartenders) {
            if (bartender.isAvailable()) {
                bartender.assign(drink);
                return;
            }
        }
    }

    public static void processFood() {
        for (Chef chef : chefs) {
            Food processFood = chef.getProcessFood();
            if (processFood.getAvailableInventory() == 0) {
                processFood.make();
                continue;
            }
            // if (processFood.getRemainingTime() > 0) {
            //     processFood.cook(0);
            //     continue;
            // }
        }
    }
    
}