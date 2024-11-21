package com.fos.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Order;
import com.fos.main.Utils;

public class CreateOrderCommand extends Command {

    private static String commandName = "Create An Order";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Order order = new Order();
        boolean ordering = true;
    
        while (ordering) {
            Utils.clearConsole(); // Clear console when creating an order
            
            System.out.println("""
                ▗▄▄▄▖▗▄▄▖ ▗▄▄▄▖ ▗▄▖▗▄▄▄▖▗▄▄▄▖     ▗▄▖ ▗▄▄▖ ▗▄▄▄ ▗▄▄▄▖▗▄▄▖ 
                ▐▌   ▐▌ ▐▌▐▌   ▐▌ ▐▌ █  ▐▌       ▐▌ ▐▌▐▌ ▐▌▐▌  █▐▌   ▐▌ ▐▌
                ▐▌   ▐▛▀▚▖▐▛▀▀▘▐▛▀▜▌ █  ▐▛▀▀▘    ▐▌ ▐▌▐▛▀▚▖▐▌  █▐▛▀▀▘▐▛▀▚▖
                ▝▚▄▄▖▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌ █  ▐▙▄▄▖    ▝▚▄▞▘▐▌ ▐▌▐▙▄▄▀▐▙▄▄▖▐▌ ▐▌
                    """);

            // Show current shopping list
            Map<String, Integer> foodCount = new HashMap<>();
            Map<String, Integer> drinkCount = new HashMap<>();

            if (!order.getFoods().isEmpty()) {
                for (Food food : order.getFoods()) {
                    foodCount.put(food.getName(), foodCount.getOrDefault(food.getName(), 0) + 1);
                }
            }
            if (!order.getDrinks().isEmpty()) {
                for (Drink drink : order.getDrinks()) {
                    drinkCount.put(drink.getName(), drinkCount.getOrDefault(drink.getName(), 0) + 1);
                }
            }
    
            List<Food> foods = kitchen.getAvailableFoods();
            System.out.printf(Utils.addColor("%-5s | %-20s | %-5s | %-12s | (HKD) %-10s%n", Utils.MAGENTA), "No", "Available Food", "Items", "Cooking Time", "Price");
            System.out.println("---------------------------------------------------------------------");
            for (int i = 0; i < foods.size(); i++) {
                Food food = foods.get(i);
                System.out.printf("%-14s | %-20s | %-14s | %-12s | $ %-10s%n",
                    Utils.addColor(i + 1 + ".", Utils.CYAN),
                    food.getName(),
                    foodCount.getOrDefault(food.getName(), 0) == 0 ? Utils.addColor("---", Utils.WHITE) : "x " + Utils.addColor(String.valueOf(foodCount.get(food.getName())), Utils.GREEN),
                    food.getCookingTime() + " s",
                    food.getPrice()
                );
            }
            System.out.println("---------------------------------------------------------------------\n");
            
            List<Drink> drinks = kitchen.getAvailableDrinks();
            System.out.printf(Utils.addColor("%-5s | %-20s | %-5s | %-12s | (HKD) %-10s%n", Utils.MAGENTA), "No", "Available Drink", "Items", "Cooking Time", "Price");
            System.out.println("---------------------------------------------------------------------");
            for (int i = 0; i < drinks.size(); i++) {
                Drink drink = drinks.get(i);
                System.out.printf("%-14s | %-20s | %-14s | %-12s | $ %-10s%n",
                    Utils.addColor(i + 1 + ".", Utils.CYAN),
                    drink.getName(),
                    drinkCount.getOrDefault(drink.getName(), 0) == 0 ? Utils.addColor("---", Utils.WHITE) : "x " + Utils.addColor(String.valueOf(drinkCount.get(drink.getName())), Utils.GREEN),
                    drink.getMixingTime() + " s",
                    drink.getPrice()
                );
            }
            System.out.println("---------------------------------------------------------------------\n");

            System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %s%n", "Predicted Finish Time", Utils.formatTimeLeft(order.getTotalPerparingTime()));
            System.out.printf(Utils.addColor("%-22s:", Utils.YELLOW) + " %d%n", "Total Items", order.getFoods().size() + order.getDrinks().size());
            System.out.printf(Utils.addColor("%-22s:", Utils.YELLOW) + " $%.2f%n", "Total Cost", order.getTotalCost());
            
            Map<String, Object> formReults = new HashMap<>();
            formReults.putAll(Utils.createInputField(scanner, "foodChoice", "\nEnter food number to add (or type '0' to finish):", "Integer", false));
            Integer foodChoice = (Integer) formReults.get("foodChoice");

            if (foodChoice != null) {
                if (foodChoice == 0) {
                    ordering = false;
                    break;
                }

                int foodIndex = foodChoice - 1;
                if (foodIndex >= 0 && foodIndex < foods.size()) {
                    Food selectedFood = foods.get(foodIndex);
                    order.addFood(selectedFood);
                    System.out.println(Utils.addColor(selectedFood.getName() + " added to the order.", Utils.GREEN));
                } else {
                    System.out.println(Utils.addColor("Invalid food selection. Please try again.", Utils.RED));
                }
                
            }

            formReults.putAll(Utils.createInputField(scanner, "drinkChoice", "Enter drink number to add (or type '0' to finish):", "Integer", false)); 
            Integer drinkChoice = (Integer) formReults.get("drinkChoice");
            if (drinkChoice != null) {
                if (drinkChoice == 0) {
                    ordering = false;
                    break;
                }

                int drinkIndex = drinkChoice - 1;
                if (drinkIndex >= 0 && drinkIndex < drinks.size()) {
                    Drink selectedDrink = drinks.get(drinkIndex);
                    order.addDrink(selectedDrink);
                    System.out.println(Utils.addColor(selectedDrink.getName() + " added to the order.", Utils.GREEN));
                } else {
                    System.out.println(Utils.addColor("Invalid drink selection. Try again.", Utils.RED));
                }
            }
        }
    
        // Finalize the order
        if (!order.getFoods().isEmpty() || !order.getDrinks().isEmpty()) {
            kitchen.placeOrder(order);
        } else {
            System.out.println("No items in the order. Order cancelled.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
