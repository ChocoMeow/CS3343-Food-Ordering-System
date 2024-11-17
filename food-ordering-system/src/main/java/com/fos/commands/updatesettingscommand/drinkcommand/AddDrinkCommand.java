package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;

public class AddDrinkCommand extends Command {
    private String commandName = "Add Drink";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        System.out.print("Enter name for new drink item: ");
        String newName = scanner.nextLine();

        boolean drinkExists = kitchen.getAvailableDrinks().stream()
                .anyMatch(drink -> drink.getName().equalsIgnoreCase(newName));

        if (!drinkExists) {
            config.getItems().getDrinks().add(new Drink(newName, 0, 0, 0));
            System.out.println("Drink item added successfully.");
        } else {
            System.out.println("Drink item name must be unique. Please try again.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
