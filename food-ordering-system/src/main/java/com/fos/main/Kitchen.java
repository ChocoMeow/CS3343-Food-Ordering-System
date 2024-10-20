package com.fos.main;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.member.Bartender;
import com.fos.member.Chef;

public class Kitchen {
    private final List<Chef> chefs;
    private final List<Bartender> bartenders;
    private final Queue<Order> orders;
    private Order processingOrder = null;

    private int lastChefIndex = 0; // Track the last assigned chef
    private int lastBartenderIndex = 0; // Track the last assigned bartender

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
        
        this.availableDrinks = Arrays.asList(
            new Drink("Cocktail", 10,0),
            new Drink("Soda", 20, 0)
        );
    }

    public void placeOrder(Order order) {
        orders.add(order);
        // System.out.println("Order placed!");
    }

    public void processOrders() {
        ExecutorService chefExecutor = Executors.newFixedThreadPool(1); // Adjust the number of threads as needed
        ExecutorService bartenderExecutor = Executors.newFixedThreadPool(1); // Adjust the number of threads as needed

        while (!orders.isEmpty()) {
            this.processingOrder = orders.poll();
            if (this.processingOrder != null) {
                for (Food food : this.processingOrder.getFoods()) {
                    chefExecutor.submit(() -> {
                        Chef chef = findAvailableChef();

                        // Wait for an available chef if none are available
                        while (chef == null) {
                            try {
                                Thread.sleep(1000); // Wait for a second before checking again
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            chef = findAvailableChef(); // Check again for available chef
                        }

                        if (food.isInStock()) {
                            food.use(); // Use stock instead of cooking
                        } else {
                            chef.cook(food); // Start cooking if not in stock
                        }
                    });
                }

                for (Drink drink : this.processingOrder.getDrinks()) {
                    bartenderExecutor.submit(() -> {
                        System.out.print("Finding Bartender");
                        Bartender bartender = findAvailableBartender(); // Using the first bartender for simplicity
                        System.out.print("Finding Bartender 2");
                        while (bartender == null) {
                            try {
                                Thread.sleep(1000); // Wait for a second before checking again
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            bartender = findAvailableBartender(); // Check again for available bartender
                            System.out.println("Bartender Found");
                        }

                        if (drink.isInStock()) {
                            drink.use(); // Use stock instead of mixing
                        } else {
                            bartender.mix(drink);
                        }
                    });
                }
            }
        }

        // Shutdown the executors gracefully when done
        chefExecutor.shutdown();
        bartenderExecutor.shutdown();
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

    private Bartender findAvailableBartender() {
        for (int i = 0; i < bartenders.size(); i++) {
            int index = (lastBartenderIndex + i) % bartenders.size();
            if (!bartenders.get(index).isBusy()) {
                lastBartenderIndex = index; // Update the last assigned index
                return bartenders.get(index);
            }
        }
        return null; // No available bartenders
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public Order getProcessingOrder() {
        return processingOrder;
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
