package com.fos.commands.updatesettingscommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.commands.updatesettingscommand.bartendercommand.BartenderMenuCommand;
import com.fos.commands.updatesettingscommand.chefcommand.ChefMenuCommand;
import com.fos.commands.updatesettingscommand.drinkcommand.DrinkMenuCommand;
import com.fos.commands.updatesettingscommand.foodcommand.FoodMenuCommand;
import com.fos.item.*;
import com.fos.main.CommandFactory;
import com.fos.main.CommandInvoker;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.*;

public class UpdateSettingsCommand extends Command {

    private static String commandName = "Update Settings";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        List<Command> commandList = new ArrayList<>();
        commandList.add(new ChefMenuCommand());
        commandList.add(new BartenderMenuCommand());
        commandList.add(new FoodMenuCommand());
        commandList.add(new DrinkMenuCommand());
        commandList.add(new SaveConfigCommand());
        
        CommandFactory commandFactory = new CommandFactory(commandList);
        CommandInvoker invoker = new CommandInvoker();
        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());

        while (true) {
            Utils.clearConsole();
            System.out.println("\n--- Update Chefs Menu ---");
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), commands.get(i).getCommandName());
            }
            System.out.println((commands.size() + 1) + ". Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command, scanner, kitchen, config);
            } else if (choice == commands.size() + 1) {
                return;
            } else {
                System.out.println("Invalid choice. Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void listChefs(ArrayList<Chef> chefs) {
        for (int i = 0; i < chefs.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), chefs.get(i).getName());
        }
    }

    private void listBartenders(ArrayList<Bartender> bartenders) {
        for (int i = 0; i < bartenders.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), bartenders.get(i).getName());
        }
    }


    private void listFoodItems(ArrayList<Food> foodItems) {
        for (int i = 0; i < foodItems.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), foodItems.get(i).getName());
        }
    }


    private void listDrinkItems(ArrayList<Drink> drinkItems) {
        for (int i = 0; i < drinkItems.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), drinkItems.get(i).getName());
        }
    }


    // private boolean isNameUnique(String name, ArrayList<Chef> chefs) {
    //     for (Chef chef : chefs) {
    //         if (chef.getName().equalsIgnoreCase(name)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // private boolean isNameUnique(String name, ArrayList<Bartender> bartenders) {
    //     for (Bartender bartender : bartenders) {
    //         if (bartender.getName().equalsIgnoreCase(name)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // private boolean isNameUnique(String name, ArrayList<Food> foods) {
    //     for (Food food : foods) {
    //         if (food.getName().equalsIgnoreCase(name)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // private boolean isNameUnique(String name, ArrayList<Drink> drinks) {
    //     for (Drink drink : drinks) {
    //         if (drink.getName().equalsIgnoreCase(name)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
