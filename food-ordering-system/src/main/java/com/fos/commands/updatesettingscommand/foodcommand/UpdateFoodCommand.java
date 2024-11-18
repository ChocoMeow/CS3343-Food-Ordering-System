package com.fos.commands.updatesettingscommand.foodcommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        int choice = Utils.createSelectionForm(
            scanner,
            "Drink Name",
            "Enter the food item number to update",
            config.getItems().getFoods().stream().map(food -> food.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getItems().getFoods().size() + 1) {
            return;
        }

        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter new name for food item:", "String", true));
        formReults.putAll(Utils.createInputField(scanner, "price", "Enter new price for food item:", "Float", true));
        formReults.putAll(Utils.createInputField(scanner, "cookingTime", "Enter new cooking time for food item (in seconds):", "Integer", true));
        formReults.putAll(Utils.createInputField(scanner, "stock", "Enter new stock for food item:", "Integer", true));

        String name = (String) formReults.get("name");
        Float price = (Float) formReults.get("price");
        Integer cookingTime = (Integer) formReults.get("cookingTime");
        Integer stock = (Integer) formReults.get("stock");

        boolean foodExists = kitchen.getAvailableDrinks().stream()
            .anyMatch(food -> food.getName().equalsIgnoreCase(name));

        if (!foodExists) {
            Food food = config.getItems().getFoods().get(choice - 1);
            food.setName(name);
            food.setPrice(price);
            food.setCookingTime(cookingTime);
            food.setStock(stock);
            System.out.println("Food item name updated successfully.");
        } else {
            System.out.println("Food item name must be unique. Please try again.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
