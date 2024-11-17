package com.fos.commands.updatesettingscommand.foodcommand;

import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;

public class AddFoodCommand extends Command {
    private String commandName = "Add Food";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        System.out.print("Enter name for new food item: ");
        String newName = scanner.nextLine();

        boolean foodExists = kitchen.getAvailableDrinks().stream()
            .anyMatch(food -> food.getName().equalsIgnoreCase(newName));

        if (!foodExists) {
            config.getItems().getFoods().add(new Food(newName, 0, 0, 0));
            System.out.println("Food item added successfully.");
        } else {
            System.out.println("Food item name must be unique. Please try again.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
