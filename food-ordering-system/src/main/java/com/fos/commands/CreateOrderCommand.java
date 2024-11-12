package com.fos.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Kitchen;
import com.fos.main.Order;
import com.fos.main.Utils;

public class CreateOrderCommand extends Command {

    private static String commandName = "Create an order";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen) {
        Order order = new Order();
        boolean ordering = true;
    
        while (ordering) {
            Utils.clearConsole(); // Clear console when creating an order
    
            // Show current shopping list
            System.out.println("\n--- Current Shopping List ---");
            if (!order.getFoods().isEmpty()) {
                System.out.println("Foods:");
                Map<String, Integer> foodCount = new HashMap<>();
                for (Food food : order.getFoods()) {
                    foodCount.put(food.getName(), foodCount.getOrDefault(food.getName(), 0) + 1);
                }
                foodCount.forEach((name, count) -> System.out.printf("  - [%s] x%d%n", name, count));
            }
            if (!order.getDrinks().isEmpty()) {
                System.out.println("Drinks:");
                Map<String, Integer> drinkCount = new HashMap<>();
                for (Drink drink : order.getDrinks()) {
                    drinkCount.put(drink.getName(), drinkCount.getOrDefault(drink.getName(), 0) + 1);
                }
                drinkCount.forEach((name, count) -> System.out.printf("  - [%s] x%d%n", name, count));
            }
    
            System.out.println("\n--- Create Order ---");
            System.out.println("Available Foods:");
            List<Food> foods = kitchen.getAvailableFoods();
            System.out.printf("%-5s %-20s %-15s %-10s%n", "No", "Name", "Cooking Time", "In Stock");
            System.out.println("-----------------------------------------------------------");
            for (int i = 0; i < foods.size(); i++) {
                Food food = foods.get(i);
                System.out.printf("%-5d %-20s %-15d %-10s%n", i + 1, food.getName(),
                        food.getCookingTime(), food.isInStock() ? "Yes" : "No");
            }
    
            System.out.println("\nAvailable Drinks:");
            List<Drink> drinks = kitchen.getAvailableDrinks();
            System.out.printf("%-5s %-20s %-10s%n", "No", "Name", "In Stock");
            System.out.println("--------------------------------------");
            for (int i = 0; i < drinks.size(); i++) {
                Drink drink = drinks.get(i);
                System.out.printf("%-5d %-20s %-10s%n", i + 1, drink.getName(),
                        drink.isInStock() ? "Yes" : "No");
            }
    
            // Adding food to the order
            boolean validFoodSelection = false;
            boolean validDrinkSelection = false;
            while (!validFoodSelection) {
                System.out.print("Enter food number to add (or type 'd' to finish): ");
                String foodChoice = scanner.nextLine();
                
                if (foodChoice.equalsIgnoreCase("d")) {
                    ordering = false;
                    validDrinkSelection = true;
                    break;
                }

                try {
                    int foodIndex = Integer.parseInt(foodChoice) - 1;
                    if (foodIndex >= 0 && foodIndex < foods.size()) {
                        Food selectedFood = foods.get(foodIndex);
                        order.addFood(selectedFood);
                        System.out.println(selectedFood.getName() + " added to the order.");
                        validFoodSelection = true;  // 成功添加食物后，继续询问下一个食物
                    } else {
                        System.out.println("Invalid food selection. Please try again.");
                        // validFoodSelection 保持为 false，循环会继续
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    // validFoodSelection 保持为 false，循环会继续
                }
            }

            // Adding drink to the order
            while (!validDrinkSelection) {
                System.out.print("Enter drink number to add (or type 'd' to finish): ");
                String drinkChoice = scanner.nextLine();
                if (drinkChoice.equalsIgnoreCase("d")) {
                    ordering = false;
                    break;
                }
        
                try {
                    int drinkIndex = Integer.parseInt(drinkChoice) - 1;
                    if (drinkIndex >= 0 && drinkIndex < drinks.size()) {
                        Drink selectedDrink = drinks.get(drinkIndex);
                        order.addDrink(selectedDrink);
                        System.out.println(selectedDrink.getName() + " added to the order.");
                        validDrinkSelection = true; 
                    } else {
                        System.out.println("Invalid drink selection. Try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
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
