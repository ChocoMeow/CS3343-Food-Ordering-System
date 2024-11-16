package com.fos.main;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Kitchen {
    private final List<Chef> chefs;
    private final List<Bartender> bartenders;
    private final Queue<Order> orders;
    private Order processingOrder = null;

    private int totalHandledOrder = 0;
    private float totalProfit = 0;

    private int lastChefIndex = 0; // Track the last assigned chef
    private int lastBartenderIndex = 0; // Track the last assigned bartender

    private int processingFoodIndex = 0;
    private int processingDrinkIndex = 0;
    private long totalWaitingTime = 0;

    private List<Food> availableFoods;
    private List<Drink> availableDrinks;

    public Kitchen(Config config) {
        this.chefs = config.getChefs();
        this.bartenders = config.getBartenders();
        this.availableFoods = config.getItems().getFoods();
        this.availableDrinks = config.getItems().getDrinks();
        this.orders = new LinkedList<>();
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public void processOrders() {
        ExecutorService chefExecutor = Executors.newFixedThreadPool(1);
        ExecutorService bartenderExecutor = Executors.newFixedThreadPool(1);

        while (!orders.isEmpty()) {
            processingFoodIndex = 0;
            processingDrinkIndex = 0;
            processingOrder = null;

            this.processingOrder = orders.poll();
            if (this.processingOrder != null) {
                totalWaitingTime += this.processingOrder.getWaitingTime();

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
                            totalProfit += food.getPrice();
                            processingFoodIndex++;

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
                            totalProfit += drink.getPrice();
                            processingDrinkIndex++;

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
                totalHandledOrder++;
                processingOrder = null;
            }   
        }

        // Shutdown the executors gracefully when done
        chefExecutor.shutdown();
        bartenderExecutor.shutdown();
    }

    public Chef findAvailableChef() {
        for (int i = 0; i < chefs.size(); i++) {
            int index = (lastChefIndex + i) % chefs.size();
            if (!chefs.get(index).isBusy()) {
                lastChefIndex = index; // Update the last assigned index
                return chefs.get(index);
            }
        }
        return null; // No available chefs
    }

    public Bartender findAvailableBartender() {
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

    public int getProcessingFoodIndex() {
        return processingFoodIndex;
    }

    public int getProcessingDrinkIndex() {
        return processingDrinkIndex;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public long getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public int getTotalHandledOrder() {
        return totalHandledOrder;
    }
}
