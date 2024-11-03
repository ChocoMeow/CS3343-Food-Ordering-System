package com.fos.main;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private List<Food> completedFoods = new ArrayList<>();
    private List<Drink> completedDrinks = new ArrayList<>();

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
            new Drink("Cocktail", 40,0),
            new Drink("Soda", 20, 0)
        );
    }

    public void placeOrder(Order order) {
        orders.add(order);
        // System.out.println("Order placed!");
    }

    public void processOrders() {
        ExecutorService chefExecutor = Executors.newFixedThreadPool(1);
        ExecutorService bartenderExecutor = Executors.newFixedThreadPool(1);

        while (!orders.isEmpty()) {
            completedFoods.clear();
            completedDrinks.clear();
            this.processingOrder = orders.poll();
            if (this.processingOrder != null) {
                List<Future<?>> futures = new ArrayList<>();

                // Process foods
                for (Food food : this.processingOrder.getFoods()) {
                    futures.add(chefExecutor.submit(() -> {
                        try {
                            Chef chef = findAvailableChef();
                            while (chef == null) {
                                Thread.sleep(1000);
                                chef = findAvailableChef();
                            }
                            if (food.isInStock()) {
                                food.use();
                            } else {
                                chef.cook(food);
                            }
                            completedFoods.add(food);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } catch (Exception e) {
                            // Handle exceptions appropriately
                            e.printStackTrace();
                        }
                    }));
                }

                // Process drinks
                for (Drink drink : this.processingOrder.getDrinks()) {
                    futures.add(bartenderExecutor.submit(() -> {
                        try {
                            Bartender bartender = findAvailableBartender();
                            while (bartender == null) {
                                Thread.sleep(1000);
                                bartender = findAvailableBartender();
                            }
                            if (drink.isInStock()) {
                                drink.use();
                            } else {
                                bartender.mix(drink);
                            }
                            completedDrinks.add(drink);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } catch (Exception e) {
                            // Handle exceptions appropriately
                            e.printStackTrace();
                        }
                    }));
                }

                // Wait for all tasks to finish before moving to the next order
                for (Future<?> future : futures) {
                    try {
                        future.get(); // This will block until the task is complete
                    } catch (InterruptedException | ExecutionException e) {
                        // Handle exceptions appropriately
                        e.printStackTrace();
                    }
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

    public List<Food> getCompletedFoods() {
        return completedFoods;
    }

    public List<Drink> getCompletedDrinks() {
        return completedDrinks;
    }
}
