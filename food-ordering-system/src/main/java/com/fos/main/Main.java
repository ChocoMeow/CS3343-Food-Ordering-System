package com.fos.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fos.Task.Task;
import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.member.Bartender;
import com.fos.member.Chef;

public class Main {
    private static Kitchen kitchen;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd HH:mm:ss");

    public static void main(String[] args) {
        // Create chefs and bartenders
        Chef chef1 = new Chef("Gordon");
        Chef chef2 = new Chef("Jamie");
        Bartender bartender1 = new Bartender("Mix Master");

        // Initialize kitchen
        kitchen = new Kitchen(Arrays.asList(chef1, chef2), Arrays.asList(bartender1));

        // Start the background task to process orders
        Task orderProcessingTask = new Task(0, 1) {
            @Override
            public void execute() {
                kitchen.processOrders();
            }
        };
        orderProcessingTask.start();

        // Command menu
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearConsole(); // Clear the console at the start of each menu

            System.out.println("\n--- Restaurant Simulator Menu ---");
            System.out.println("1. Create an order");
            System.out.println("2. View kitchen process list");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createOrder(scanner);
                    break;
                case 2:
                    viewKitchenProcesses(scanner);
                    break;
                case 3:
                    orderProcessingTask.stop();
                    System.out.println("Simulator stopped.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createOrder(Scanner scanner) {
        Order order = new Order();
        boolean ordering = true;
    
        while (ordering) {
            clearConsole(); // Clear console when creating an order
    
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
            System.out.print("Enter food number to add (or type 'done' to finish): ");
            String foodChoice = scanner.nextLine();
            if (foodChoice.equalsIgnoreCase("done")) {
                break;
            }
    
            try {
                int foodIndex = Integer.parseInt(foodChoice) - 1;
                if (foodIndex >= 0 && foodIndex < foods.size()) {
                    Food selectedFood = foods.get(foodIndex);
                    order.addFood(selectedFood);
                    System.out.println(selectedFood.getName() + " added to the order.");
                } else {
                    System.out.println("Invalid food selection. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
    
            // Adding drink to the order
            System.out.print("Enter drink number to add (or type 'done' to finish): ");
            String drinkChoice = scanner.nextLine();
            if (drinkChoice.equalsIgnoreCase("done")) {
                break;
            }
    
            try {
                int drinkIndex = Integer.parseInt(drinkChoice) - 1;
                if (drinkIndex >= 0 && drinkIndex < drinks.size()) {
                    Drink selectedDrink = drinks.get(drinkIndex);
                    order.addDrink(selectedDrink);
                    System.out.println(selectedDrink.getName() + " added to the order.");
                } else {
                    System.out.println("Invalid drink selection. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    
        // Finalize the order
        if (!order.getFoods().isEmpty() || !order.getDrinks().isEmpty()) {
            kitchen.placeOrder(order);
        } else {
            System.out.println("No items in the order. Order cancelled.");
        }
    }

    private static void viewKitchenProcesses(Scanner scanner) {
        System.out.println("\n--- View Kitchen Process List ---");

        // Start a new thread for refreshing the kitchen process view
        Thread refreshThread = new Thread(() -> {
            while (true) {
                clearConsole(); // Clear the console each refresh
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
            System.out.println("Press 'e' to exit view...");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("e")) {
                refreshThread.interrupt(); // Stop the refresh thread
                break;
            }
        }
    }

    private static void displayKitchenProcesses() {
        System.out.println("\nCurrent Kitchen Processes:");
        System.out.printf("%-20s %-15s %-15s %-20s %-20s%n", "Order Time", "Waiting Time", "Expected Finish", "Details", "Pending Items");
        System.out.println("------------------------------------------------------------------------------------------------");
    
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
    
        System.out.println("------------------------------------------------------------------------------------------------");
        
        // Display current processing order
        Order currentOrder = kitchen.getProcessingOrder();
        if (currentOrder != null) {
            displayCurrentOrderDetails(currentOrder);
        }
    
        // Show chef activity
        displayChefActivities();
    
        // Show bartender activity
        displayBartenderActivities();
    }
    
    private static void displayCurrentOrderDetails(Order currentOrder) {
        System.out.printf("%n%-15s %-15s%n", "Completed Foods", "Completed Drinks");
        System.out.println("-------------------------------------");
    
        // Display completed foods
        System.out.printf("%-15s %-15s%n", 
                       kitchen.getCompletedFoods().isEmpty() ? "None" : String.join(", ", kitchen.getCompletedFoods().stream().map(Food::getName).toArray(String[]::new)),
                       kitchen.getCompletedDrinks().isEmpty() ? "None" : String.join(", ", kitchen.getCompletedDrinks().stream().map(Drink::getName).toArray(String[]::new)));
    }
    
    private static void displayChefActivities() {
        System.out.printf("%n%-15s %-25s %-15s%n", "Chef", "Current Task", "Time Left");
        System.out.println("-----------------------------------------------");
        for (Chef chef : kitchen.getChefs()) {
            Food currentFood = chef.getCurrentFood();
            if (currentFood != null) {
                long remainingTime = chef.getRemainingCookingTime();
                System.out.printf("%-15s %s %-15s%n", chef.getName(), "Cooking " + currentFood.getName(), formatTime(remainingTime));
            } else {
                System.out.printf("%-15s %s %-15s%n", chef.getName(), "Free", "N/A");
            }
        }
    }
    
    private static void displayBartenderActivities() {
        System.out.printf("%n%-15s %-25s %-15s%n", "Bartender", "Current Task", "Time Left");
        System.out.println("-------------------------------------------");
        for (Bartender bartender : kitchen.getBartenders()) {
            Drink currentDrink = bartender.getCurrentDrink();
            if (currentDrink != null) {
                long remainingTime = bartender.getRemainingMixingTime();
                System.out.printf("%-15s %s %-15s%n", bartender.getName(), "Mixing " + currentDrink.getName(), formatTime(remainingTime));
            } else {
                System.out.printf("%-15s %s %-15s%n", bartender.getName(), "Free", "N/A");
            }
        }
    }
    
    private static void processOrdersList(List<Order> orders) {
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
    
            String pendingItems = "";
            for (Food food : order.getFoods()) {
                if (!food.isInStock()) {
                    pendingItems += food.getName() + " ";
                }
            }
            for (Drink drink : order.getDrinks()) {
                if (!drink.isInStock()) {
                    pendingItems += drink.getName() + " ";
                }
            }
    
            System.out.printf("%-20s %-15s %-15s %-20s %-20s%n",
                    formatDate(order.getOrderTime()),
                    formatTime(waitingTime),
                    formatTimeLeft(timeLeft),
                    details.toString().trim(),
                    pendingItems.trim());
        }
    }

    private static String formatDate(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    private static String formatTime(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String formatTimeLeft(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", Math.max(minutes, 0), Math.max(seconds, 0));
    }

    private static void clearConsole() {
        // Clear console screen for Windows and Unix-based systems
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Could not clear the console.");
        }
    }
}
