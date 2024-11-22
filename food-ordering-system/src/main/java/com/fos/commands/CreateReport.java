package com.fos.commands;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

public class CreateReport extends Command {

    private static String commandName = "Create Report";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        
        String title = """
        ▗▄▄▖ ▗▄▄▄▖▗▄▄▖  ▗▄▖ ▗▄▄▖▗▄▄▄▖
        ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌ █  
        ▐▛▀▚▖▐▛▀▀▘▐▛▀▘ ▐▌ ▐▌▐▛▀▚▖ █  
        ▐▌ ▐▌▐▙▄▄▖▐▌   ▝▚▄▞▘▐▌ ▐▌ █
                """;
        System.out.println(title);
        System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %s", "Total Handled Orders", kitchen.getTotalHandledOrder());

        float totalProfit = 0;
        float totalFoodProfit = 0;
        float totalDrinkProfit = 0;

        for (String itemName : kitchen.getHistoryItems().keySet()) {
            // Check for Food items
            Food food = kitchen.getFood(itemName);
            if (food != null) {
                totalProfit += food.getPrice();
                totalFoodProfit += food.getPrice();
            } else {
                // Check for Drink items if Food is not found
                Drink drink = kitchen.getDrink(itemName);
                if (drink != null) {
                    totalProfit += drink.getPrice();
                    totalDrinkProfit += drink.getPrice();
                }
            }
        }

        // Print the total profit with formatting
        System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %.2f", "Total Profit", totalProfit);
        
        // Calculate average waiting time
        long totalHandledOrders = kitchen.getTotalHandledOrder();
        long totalWaitingTime = kitchen.getTotalWaitingTime();

        if (totalHandledOrders > 0) {
            long averageWaitingTime = totalWaitingTime / totalHandledOrders;
            System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %s", "Average Waiting Time", Utils.formatTime(averageWaitingTime));
        } else {
            System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " N/A", "Average Waiting Time");
        }

        System.out.printf(Utils.addColor("%n%n%-20s | %-20s%n", Utils.MAGENTA), "Chef Name", "Total Handled Foods");
        System.out.println("-------------------------------------------");
        for (Chef chef : kitchen.getChefs()) {
            System.out.printf("%-20s | %-20d%n", chef.getName(), chef.getTotalHandledFoods());
        }

        System.out.printf(Utils.addColor("%n%-20s | %-20s%n", Utils.MAGENTA), "Bartender Name", "Total Handled Drinks");
        System.out.println("-------------------------------------------");
        for (Bartender bartender : kitchen.getBartenders()) {
            System.out.printf("%-20s | %-20d%n", bartender.getName(), bartender.getTotalHandledDrinks());
        }

        System.out.printf(Utils.addColor("%n%-20s | %-20s | (HKD) %-20s%n", Utils.MAGENTA), "Food Name", "Total Orders", "Total Profit");
        System.out.println("----------------------------------------------------------------");
        List<Entry<String, Integer>> foodOrders = new ArrayList<>();

        Integer totalFoodOrders = 0;
        Integer totalDrinkOrders = 0;

        // Collect food names and their total orders into a list
        for (Food food : kitchen.getAvailableFoods()) {
            Integer totalOrder = kitchen.getHistoryItems().getOrDefault(food.getName(), 0);
            totalFoodOrders += totalOrder;
            foodOrders.add(new AbstractMap.SimpleEntry<>(food.getName(), totalOrder));
        }

        // Sort the list by totalOrder in descending order
        foodOrders.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Print the sorted results
        for (Entry<String, Integer> entry : foodOrders) {
            String foodName = entry.getKey();
            Integer totalOrder = entry.getValue();
            System.out.printf("%-20s | %-20s | $ %-20s%n", foodName, totalOrder, totalOrder * kitchen.getFood(foodName).getPrice());
        }
        System.out.println("----------------------------------------------------------------");
        System.out.printf(Utils.addColor("%-20s | %-20s | $ %-20s%n", Utils.CYAN), "Total", totalFoodOrders, totalFoodProfit);

        System.out.printf(Utils.addColor("%n%-20s | %-20s | (HKD) %-20s%n", Utils.MAGENTA), "Drink Name", "Total Orders", "Total Profit");
        System.out.println("----------------------------------------------------------------");
        List<Entry<String, Integer>> drinkOrders = new ArrayList<>();

        // Collect food names and their total orders into a list
        for (Drink drink : kitchen.getAvailableDrinks()) {
            Integer totalOrder = kitchen.getHistoryItems().getOrDefault(drink.getName(), 0);
            totalDrinkOrders += totalOrder;
            drinkOrders.add(new AbstractMap.SimpleEntry<>(drink.getName(), totalOrder));
        }

        // Sort the list by totalOrder in descending order
        drinkOrders.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Print the sorted results
        for (Entry<String, Integer> entry : drinkOrders) {
            String drinkName = entry.getKey();
            Integer totalOrder = entry.getValue();
            System.out.printf("%-20s | %-20s | $ %-20s%n",drinkName, totalOrder, totalOrder * kitchen.getDrink(drinkName).getPrice());
        }
        System.out.println("----------------------------------------------------------------");
        System.out.printf(Utils.addColor("%-20s | %-20s | $ %-20s%n", Utils.CYAN), "Total", totalDrinkOrders, totalDrinkProfit);
        
        System.out.print(Utils.addColor("\nPress 'ENTER' to exit...", Utils.YELLOW));
        scanner.nextLine();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
