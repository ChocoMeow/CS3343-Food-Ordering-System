package com.fos.commands;

import java.util.Scanner;

import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

public class CreateReport extends Command {

    private static String commandName = "Create report";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("%n%-30s: %d%n", "Total Handled Orders", kitchen.getTotalHandledOrder());
        System.out.printf("%-30s: $%.2f%n", "Total Profit", kitchen.getTotalProfit());
        
        // Calculate average waiting time
        long totalHandledOrders = kitchen.getTotalHandledOrder();
        long totalWaitingTime = kitchen.getTotalWaitingTime();

        if (totalHandledOrders > 0) {
            long averageWaitingTime = totalWaitingTime / totalHandledOrders;
            System.out.printf("%-30s: %s%n", "Average Waiting Time", Utils.formatTime(averageWaitingTime));
        } else {
            System.out.printf("%-30s: N/A%n", "Average Waiting Time"); // Handle case when no orders are handled
        }

        System.out.println("\nChefs' Contributions:");
        System.out.printf("%-20s %-20s%n", "Chef Name", "Total Handled Foods");
        System.out.println("------------------------------------");
        for (Chef chef : kitchen.getChefs()) {
            System.out.printf("%-20s %-20d%n", chef.getName(), chef.getTotalHandledFoods());
        }

        System.out.println("\nBartenders' Contributions:");
        System.out.printf("%-20s %-20s%n", "Bartender Name", "Total Handled Drinks");
        System.out.println("------------------------------------");
        for (Bartender bartender : kitchen.getBartenders()) {
            System.out.printf("%-20s %-20d%n", bartender.getName(), bartender.getTotalHandledDrinks());
        }

        System.out.print("\nPress Enter to exit...");
        String exit = scanner.nextLine();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
