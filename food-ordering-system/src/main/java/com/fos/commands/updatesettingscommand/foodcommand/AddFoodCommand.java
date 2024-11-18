package com.fos.commands.updatesettingscommand.foodcommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class AddFoodCommand extends Command {
    private String commandName = "Add Food";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter name for new food item:", "String", true));
        formReults.putAll(Utils.createInputField(scanner, "price", "Enter price for new food item:", "Float", true));
        formReults.putAll(Utils.createInputField(scanner, "cookingTime", "Enter cooking time for new food item (in seconds):", "Integer", true));
        formReults.putAll(Utils.createInputField(scanner, "stock", "Enter stock for new food item:", "Integer", true));

        boolean foodExists = config.getItems().getFoods().stream()
            .anyMatch(food -> food.getName().equalsIgnoreCase((String) formReults.get("name")));

        if (!foodExists) {
            config.getItems().getFoods().add(new Food(
                (String) formReults.get("name"),
                (Float) formReults.get("price"),
                (Integer) formReults.get("cookingTime"),
                (Integer) formReults.get("stock")
            ));
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
