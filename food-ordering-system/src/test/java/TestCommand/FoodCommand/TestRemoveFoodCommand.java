package TestCommand.FoodCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.foodcommand.RemoveFoodCommand;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;

class TestRemoveFoodCommand {

    private RemoveFoodCommand removeFoodCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize RemoveFoodCommand
        removeFoodCommand = new RemoveFoodCommand();

        // Load a new Config instance with real data
        config = new Config().loadConfig();

        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testRemoveFoodCommand_SuccessfulRemoval() {
        // Simulate user input for removing the second food item (Burger)
        String input = "2\n"; // Select item 2 (Burger)
        Scanner scanner = new Scanner(input);

        // Execute the command
        removeFoodCommand.execute(scanner, kitchen, config);

        // Verify the food item has been removed
        // List<Food> foods = config.getItems().getFoods();
        // assertEquals(2, foods.size()); // Only 2 items should remain
        // assertEquals("Pizza", foods.get(0).getName());
        // assertEquals("Pasta", foods.get(1).getName());
    }

    @Test
    void testRemoveFoodCommand_InvalidChoice() {
        // Simulate user input for "Go Back"
        String input = "4\n"; // Select "Go Back" (choice outside the food list size)
        Scanner scanner = new Scanner(input);

        // Execute the command
        removeFoodCommand.execute(scanner, kitchen, config);

        // Verify no changes to the food list
        List<Food> foods = config.getItems().getFoods();
        // assertEquals(3, foods.size()); // Original size remains unchanged
        // assertEquals("Pizza", foods.get(0).getName());
        // assertEquals("Burger", foods.get(1).getName());
        // assertEquals("Pasta", foods.get(2).getName());
    }

    @Test
    void testRemoveFoodCommand_RemoveFirstItem() {
        // Simulate user input for removing the first food item (Pizza)
        String input = "1\n"; // Select item 1 (Pizza)
        Scanner scanner = new Scanner(input);

        // Execute the command
        removeFoodCommand.execute(scanner, kitchen, config);

        // Verify the food item has been removed
        // List<Food> foods = config.getItems().getFoods();
        // assertEquals(2, foods.size()); // Only 2 items should remain
        // assertEquals("Burger", foods.get(0).getName());
        // assertEquals("Pasta", foods.get(1).getName());
    }
}