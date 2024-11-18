package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class RemoveDrinkCommand extends Command {
    private String commandName = "Remove Drinks";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();

        int choice = Utils.createSelectionForm(
            scanner,
            "Drink Name",
            "Enter the drink item number to remove",
            config.getItems().getDrinks().stream().map(drink -> drink.getName()).collect(Collectors.toList()),
            List.of()
        );

        config.getItems().getDrinks().remove(choice - 1);
        System.out.println("Drink item removed successfully.");
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
