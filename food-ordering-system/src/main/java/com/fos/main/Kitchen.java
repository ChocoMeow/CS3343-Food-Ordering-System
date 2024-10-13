package com.fos.main;

import java.util.*;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.member.Bartender;
import com.fos.member.Chef;

public class Kitchen {
    private final List<Chef> chefs;
    private final List<Bartender> bartenders;
    private final Queue<Order> orders;

    private int lastChefIndex = 0; // Track the last assigned chef

    private List<Food> availableFoods;
    private List<Drink> availableDrinks;

    public Kitchen(List<Chef> chefs, List<Bartender> bartenders) {
        this.chefs = chefs;
        this.bartenders = bartenders;
        this.orders = new LinkedList<>();
        this.availableFoods = Arrays.asList(
                new Food("Pasta", 20, 0),
                new Food("Hotdog", 35, 0),
                new Food("ice-cream", 15, 0),
                new Food("apple", 5, 0));
        this.availableDrinks = Arrays.asList(new Drink("Cocktail", 3), new Drink("Soda", 10));
    }

    public void placeOrder(Order order) {
        orders.add(order);
        System.out.println("Order placed!");
    }

    public void processOrders() {
        while (!orders.isEmpty()) {
            Order order = orders.poll();
            if (order != null) {
                for (Food food : order.getFoods()) {
                    Chef chef = findAvailableChef();

                    // Wait for an available chef if none are available
                    while (chef == null) {
                        // System.out.println("All chefs are busy. Waiting for one to finish...");
                        try {
                            Thread.sleep(1000); // Wait for a second before checking again
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        chef = findAvailableChef(); // Check again for available chef
                    }

                    if (food.isInStock()) {
                        food.use(); // Use stock instead of cooking
                        // System.out.printf("%s retrieved %s from stock.%n", chef.getName(),
                        // food.getName());
                    } else {
                        chef.cook(food); // Start cooking if not in stock
                    }
                }

                for (Drink drink : order.getDrinks()) {
                    Bartender bartender = bartenders.get(0); // Using the first bartender for simplicity
                    if (drink.isInStock()) {
                        drink.use(); // Use stock instead of mixing
                        // System.out.printf("%s retrieved %s from stock.%n", bartender.getName(),
                        // drink.getName());
                    } else {
                        bartender.mix(drink);
                    }
                }
            }
        }
    }

    private Chef findAvailableChef() {
        for (int i = 0; i < chefs.size(); i++) {
            int index = (lastChefIndex + i) % chefs.size();
            if (!chefs.get(index).isBusy()) {
                lastChefIndex = index; // Update the last assigned index
                return chefs.get(index);
            }
        }
        return null; // No available chefs
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public List<Bartender> getBartenders() {
        return bartenders;
    }

    public List<Food> getAvailableFoods() {
        return availableFoods;
    }

    public List<Drink> getAvailableDrinks() {
        return availableDrinks;
    }
}
