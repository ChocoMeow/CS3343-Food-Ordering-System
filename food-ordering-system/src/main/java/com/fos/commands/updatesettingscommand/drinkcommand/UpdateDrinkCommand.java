package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class UpdateDrinkCommand extends Command {
    private String commandName = "Update Drinks";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("--- %s ---%n", commandName);
        List<Drink> drinks = kitchen.getAvailableDrinks();
        for (int i = 0; i < drinks.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), drinks.get(i).getName());
        }

        System.out.print("\nEnter the drink item number to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= config.getItems().getDrinks().size()) {
            System.out.print("Enter new name for drink item: ");
            String newName = scanner.nextLine();

            config.getItems().getDrinks().get(choice - 1).setName(newName);
            System.out.println("Drink item name updated successfully.");

            // if (isNameUnique(newName, config.getItems().getDrinks())) {
            //     config.getItems().getDrinks().get(choice - 1).setName(newName);
            //     System.out.println("Drink item name updated successfully.");
            // } else {
            //     System.out.println("Drink item name must be unique. Please try again.");
            // }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
