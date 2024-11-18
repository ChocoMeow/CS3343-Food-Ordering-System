package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        int choice = Utils.createSelectionForm(
            scanner,
            "Drink Name",
            "Enter the drink item number to update",
            config.getItems().getDrinks().stream().map(drink -> drink.getName()).collect(Collectors.toList()),
            List.of()
        );

        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter new name for drink item:", "String", true));
        formReults.putAll(Utils.createInputField(scanner, "price", "Enter new price for drink item:", "Float", true));
        formReults.putAll(Utils.createInputField(scanner, "mixingTime", "Enter new mixing time for drink item (in seconds):", "Integer", true));
        formReults.putAll(Utils.createInputField(scanner, "stock", "Enter new stock for drink item:", "Integer", true));

        String name = (String) formReults.get("name");
        Float price = (Float) formReults.get("price");
        Integer mixingTime = (Integer) formReults.get("mixingTime");
        Integer stock = (Integer) formReults.get("stock");
        
        boolean drinkExists = kitchen.getAvailableDrinks().stream()
            .anyMatch(drink -> drink.getName().equalsIgnoreCase(name));

        if (!drinkExists) {
            Drink drink = config.getItems().getDrinks().get(choice - 1);
            drink.setName(name);
            drink.setPrice(price);
            drink.setMixingTime(mixingTime);
            drink.setStock(stock);

            System.out.println("Drink item name updated successfully.");
        } else {
            System.out.println("Drink item name must be unique. Please try again.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
