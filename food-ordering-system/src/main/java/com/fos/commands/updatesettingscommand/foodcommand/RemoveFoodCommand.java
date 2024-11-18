package com.fos.commands.updatesettingscommand.foodcommand;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class RemoveFoodCommand extends Command {
    private String commandName = "Remove Foods";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        
        int choice = Utils.createSelectionForm(
            scanner,
            "Drink Name",
            "Enter the food item number to remove",
            config.getItems().getFoods().stream().map(food -> food.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getItems().getFoods().size() + 1) {
            return;
        }

        config.getItems().getFoods().remove(choice - 1);
        System.out.println("Food item removed successfully.");
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
