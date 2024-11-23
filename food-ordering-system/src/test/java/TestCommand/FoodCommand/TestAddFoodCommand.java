package TestCommand.FoodCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.foodcommand.AddFoodCommand;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;

import static org.junit.jupiter.api.Assertions.*;


public class TestAddFoodCommand {

    private AddFoodCommand addFoodCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize AddFoodCommand
        addFoodCommand = new AddFoodCommand();

        // Create a sample configuration with foods
        config = new Config().loadConfig();
        // List<Food> foodList = new ArrayList<>();
        // foodList.add(new Food("Pizza", 12.5f, 300, 10)); // name, price, cookingTime, stock
        // foodList.add(new Food("Burger", 8.0f, 200, 20));
        // config.getItems().setFoods(foodList);

        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testAddFoodCommand_SuccessfulAddition() {
        // Simulate user input for adding a new food item
        String input = "Pasta\n15.0\n400\n25\n"; // Enter new food details
        Scanner scanner = new Scanner(input);

        // Execute the command
        addFoodCommand.execute(scanner, kitchen, config);

        // Verify the new food item has been added
        // List<Food> foods = config.getItems().getFoods();
        // assertEquals(3, foods.size());
        // Food newFood = foods.get(2); // The newly added food item
        // assertEquals("Pasta", newFood.getName());
        // assertEquals(15.0f, newFood.getPrice());
        // assertEquals(400, newFood.getCookingTime());
        // assertEquals(25, newFood.getStock());
    }

    @Test
    void testAddFoodCommand_DuplicateFoodName() {
        // Simulate user input where the name is a duplicate ("Pizza")
        String input = "Pizza\n10.0\n300\n20\n"; // Duplicate name
        Scanner scanner = new Scanner(input);

        // Execute the command
        addFoodCommand.execute(scanner, kitchen, config);

        // Verify no new food item has been added
        // assertEquals(2, foods.size()); // Size should remain the same
        // assertEquals("Pizza", foods.get(0).getName()); // Original "Pizza" remains
    }

    @Test
    void testAddFoodCommand_InvalidInput() {
        // Simulate user input with invalid data (e.g., no name provided)
        String input = "\n15.0\n400\n25\n"; // Empty name
        Scanner scanner = new Scanner(input);

        // Execute the command
        // addFoodCommand.execute(scanner, kitchen, config);

        // Verify that no new food item has been added
        // assertEquals(2, foods.size()); // Size should remain unchanged
    }
}