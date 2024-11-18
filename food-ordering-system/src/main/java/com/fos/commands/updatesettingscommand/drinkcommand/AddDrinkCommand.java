package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class AddDrinkCommand extends Command {
    private String commandName = "Add Drink";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter name for new drink item:", "String", true));
        formReults.putAll(Utils.createInputField(scanner, "price", "Enter price for new drink item:", "Float", true));
        formReults.putAll(Utils.createInputField(scanner, "mixingTime", "Enter cooking time for new drink item (in seconds):", "Integer", true));
        formReults.putAll(Utils.createInputField(scanner, "stock", "Enter stock for new drink item:", "Integer", true));

        String name = (String) formReults.get("name");
        Float price = (Float) formReults.get("price");
        Integer mixingTime = (Integer) formReults.get("mixingTime");
        Integer stock = (Integer) formReults.get("stock");

        boolean drinkExists = kitchen.getAvailableDrinks().stream()
            .anyMatch(drink -> drink.getName().equalsIgnoreCase(name));

        if (!drinkExists) {
            config.getItems().getDrinks().add(new Drink(name, price, mixingTime, stock));
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
