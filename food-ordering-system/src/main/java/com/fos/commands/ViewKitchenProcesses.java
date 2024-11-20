package com.fos.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Order;
import com.fos.main.Utils;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

public class ViewKitchenProcesses extends Command {

    private static String commandName = "View Kitchen Process List";
    private Kitchen kitchen;

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        this.kitchen = kitchen;
        System.out.println("\n--- View Kitchen Process List ---");

        // Start a new thread for refreshing the kitchen process view
        Thread refreshThread = new Thread(() -> {
            while (true) {
                Utils.clearConsole(); // Clear the console each refresh
                displayKitchenProcesses();
                try {
                    Thread.sleep(1000); // Refresh every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        refreshThread.start();

        // Wait for user input to exit
        while (true) {
            scanner.nextLine();
            refreshThread.interrupt(); // Stop the refresh thread
            break;
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private void displayKitchenProcesses() {
        System.out.println("""
            ▗▄▄▄▖▗▖ ▗▖▗▄▄▖ ▗▄▄▖ ▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖    ▗▖ ▗▖▗▄▄▄▖▗▄▄▄▖▗▄▄▖▗▖ ▗▖▗▄▄▄▖▗▖  ▗▖    ▗▄▄▖ ▗▄▄▖  ▗▄▖  ▗▄▄▖▗▄▄▄▖ ▗▄▄▖ ▗▄▄▖▗▄▄▄▖ ▗▄▄▖
            ▐▌   ▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌▐▌   ▐▛▚▖▐▌  █      ▐▌▗▞▘  █    █ ▐▌   ▐▌ ▐▌▐▌   ▐▛▚▖▐▌    ▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌▐▌   ▐▌   ▐▌   ▐▌   ▐▌   ▐▌   
            ▐▌   ▐▌ ▐▌▐▛▀▚▖▐▛▀▚▖▐▛▀▀▘▐▌ ▝▜▌  █      ▐▛▚▖   █    █ ▐▌   ▐▛▀▜▌▐▛▀▀▘▐▌ ▝▜▌    ▐▛▀▘ ▐▛▀▚▖▐▌ ▐▌▐▌   ▐▛▀▀▘ ▝▀▚▖ ▝▀▚▖▐▛▀▀▘ ▝▀▚▖
            ▝▚▄▄▖▝▚▄▞▘▐▌ ▐▌▐▌ ▐▌▐▙▄▄▖▐▌  ▐▌  █      ▐▌ ▐▌▗▄█▄▖  █ ▝▚▄▄▖▐▌ ▐▌▐▙▄▄▖▐▌  ▐▌    ▐▌   ▐▌ ▐▌▝▚▄▞▘▝▚▄▄▖▐▙▄▄▖▗▄▄▞▘▗▄▄▞▘▐▙▄▄▖▗▄▄▞▘
                """);

        System.out.printf(Utils.addColor("%-20s | %-15s | %-20s | %-20s%n", Utils.MAGENTA), "Order Time", "Waiting Time", "Expected Finish", "Details");
        System.out.println("-".repeat(120));
        
        if (kitchen.getOrders().isEmpty()) {
            System.out.println("There is currently no order queued!");

        } else {
            List<Order> emergencyOrders = new ArrayList<>();
            List<Order> normalOrders = new ArrayList<>();
        
            for (Order order : kitchen.getOrders()) {
                if (order.isEmergency()) {
                    emergencyOrders.add(order);
                } else {
                    normalOrders.add(order);
                }
            }
        
            // Process emergency orders first
            processOrdersList(emergencyOrders);
        
            // Process normal orders
            processOrdersList(normalOrders);
        }
    
        System.out.println("-".repeat(120));
        
        // Display current processing order
        Order currentOrder = kitchen.getProcessingOrder();
        if (currentOrder != null) {
            displayCurrentOrderDetails(currentOrder);
        }
    
        // Show chef activity
        displayChefActivities();
    
        // Show bartender activity
        displayBartenderActivities();

        System.out.print(Utils.addColor("\nPress 'ENTER' to exit view...", Utils.YELLOW));
    }

    private void displayCurrentOrderDetails(Order currentOrder) {
        if (currentOrder == null) {
            return;
        }
    
        System.out.printf(Utils.addColor("%n%-15s%n", Utils.MAGENTA), "Current Order");
        System.out.println("-------------------------------------");
        
        displayItems("𓎦 Foods ", currentOrder.getFoods(), kitchen.getProcessingFoodIndex());
        displayItems("☕︎Drinks", currentOrder.getDrinks(), kitchen.getProcessingDrinkIndex());
    }
    
    private <T> void displayItems(String title, List<T> items, int processingIndex) {
        System.out.print(title + ": ");
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (i < processingIndex - 1) {
                System.out.print(Utils.addColor("[✔]", Utils.GREEN));
            } else if (i == processingIndex - 1) {
                System.out.print(Utils.addColor("[↺]", Utils.YELLOW));
            } else {
                System.out.print("[ ]");
            }
            System.out.print((item instanceof Food ? ((Food) item).getName() : ((Drink) item).getName()) + " ");
        }
        System.out.println();
    }

    private void processOrdersList(List<Order> orders) {
        for (Order order : orders) {
            long waitingTime = order.getWaitingTime();
            long expectedFinishTime = order.getExpectedFinishTime();
            long timeLeft = expectedFinishTime - System.currentTimeMillis();
            StringBuilder details = new StringBuilder();
    
            for (Food food : order.getFoods()) {
                details.append(food.getName()).append(" ");
            }
            for (Drink drink : order.getDrinks()) {
                details.append(drink.getName()).append(" ");
            }
    
            System.out.printf("%-20s | %-24s | %-29s | %-20s%n",
                Utils.formatDate(order.getOrderTime()),
                Utils.addColor(Utils.formatTime(waitingTime), order.isEmergency() ? Utils.RED : Utils.WHITE),
                Utils.addColor(Utils.formatTimeLeft(timeLeft), timeLeft <= 0 ? Utils.RED : Utils.WHITE),
                details.toString().trim()
            );
        }
    }

    private void displayChefActivities() {
        System.out.printf(Utils.addColor("%n%-15s | %-25s %-15s%n", Utils.MAGENTA), "Chef", "Current Task", "Time Left");
        System.out.println("-----------------------------------------------------");
        for (Chef chef : kitchen.getChefs()) {
            Food currentFood = chef.getCurrentFood();
            if (currentFood != null) {
                long remainingTime = chef.getRemainingCookingTime();
                System.out.printf("%-15s | %-34s %-15s%n", chef.getName(), Utils.addColor("Cooking " + currentFood.getName(), Utils.BLUE), Utils.formatTime(remainingTime));
            } else {
                System.out.printf("%-15s | %-34s %-15s%n", chef.getName(), Utils.addColor("Free", Utils.GREEN), "N/A");
            }
        }
    }
    
    private void displayBartenderActivities() {
        System.out.printf(Utils.addColor("%n%-15s | %-25s %-15s%n", Utils.MAGENTA), "Bartender", "Current Task", "Time Left");
        System.out.println("-----------------------------------------------------");
        
        for (Bartender bartender : kitchen.getBartenders()) {
            Drink currentDrink = bartender.getCurrentDrink();
            if (currentDrink != null) {
                long remainingTime = bartender.getRemainingMixingTime();
                System.out.printf("%-15s | %-34s %-15s%n", bartender.getName(), Utils.addColor("Mixing " + currentDrink.getName(), Utils.BLUE), Utils.formatTime(remainingTime));
            } else {
                System.out.printf("%-15s | %-34s %-15s%n", bartender.getName(), Utils.addColor("Free", Utils.GREEN), "N/A");
            }
        }
    }

}
