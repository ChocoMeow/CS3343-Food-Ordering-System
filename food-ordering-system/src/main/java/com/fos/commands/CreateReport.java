package com.fos.commands;

import java.util.Scanner;

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
        System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %s", "Total Handled Orders", kitchen.getTotalHandledOrder());
        System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %.2f", "Total Profit", kitchen.getTotalProfit());
        
        // Calculate average waiting time
        long totalHandledOrders = kitchen.getTotalHandledOrder();
        long totalWaitingTime = kitchen.getTotalWaitingTime();

        if (totalHandledOrders > 0) {
            long averageWaitingTime = totalWaitingTime / totalHandledOrders;
            System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " %s", "Average Waiting Time", Utils.formatTime(averageWaitingTime));
        } else {
            System.out.printf(Utils.addColor("%n%-22s:", Utils.YELLOW) + " N/A%n%n", "Average Waiting Time");
        }

        System.out.printf(Utils.addColor("%-20s | %-20s%n", Utils.MAGENTA), "Chef Name", "Total Handled Foods");
        System.out.println("-------------------------------------------");
        for (Chef chef : kitchen.getChefs()) {
            System.out.printf("%-20s | %-20d%n", chef.getName(), chef.getTotalHandledFoods());
        }

        System.out.printf(Utils.addColor("%n%-20s | %-20s%n", Utils.MAGENTA), "Bartender Name", "Total Handled Drinks");
        System.out.println("-------------------------------------------");
        for (Bartender bartender : kitchen.getBartenders()) {
            System.out.printf("%-20s | %-20d%n", bartender.getName(), bartender.getTotalHandledDrinks());
        }

        System.out.print(Utils.addColor("\nPress 'ENTER' to exit...", Utils.YELLOW));
        scanner.nextLine();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
