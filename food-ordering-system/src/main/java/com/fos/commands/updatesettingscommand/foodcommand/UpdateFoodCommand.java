package com.fos.commands.updatesettingscommand.foodcommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class UpdateFoodCommand extends Command {
    private String commandName = "Update Foods";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("--- %s ---%n", commandName);
        List<Food> foods = kitchen.getAvailableFoods();
        for (int i = 0; i < foods.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), foods.get(i).getName());
        }

        System.out.print("\nEnter the food item number to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= config.getItems().getFoods().size()) {
            System.out.print("Enter new name for food item: ");
            String newName = scanner.nextLine();

            boolean foodExists = kitchen.getAvailableDrinks().stream()
                .anyMatch(food -> food.getName().equalsIgnoreCase(newName));

            if (!foodExists) {
                config.getItems().getFoods().get(choice - 1).setName(newName);
                System.out.println("Food item name updated successfully.");
            } else {
                System.out.println("Food item name must be unique. Please try again.");
            }
            
        } else {
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
