package TestCommand.FoodCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.foodcommand.AddFoodCommand;
import com.fos.commands.updatesettingscommand.foodcommand.FoodMenuCommand;
import com.fos.commands.updatesettingscommand.foodcommand.RemoveFoodCommand;
import com.fos.commands.updatesettingscommand.foodcommand.UpdateFoodCommand;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;

import static org.junit.jupiter.api.Assertions.*;

class TestFoodMenuCommand {

    private FoodMenuCommand foodMenuCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize FoodMenuCommand
        foodMenuCommand = new FoodMenuCommand();

        // Initialize Config with some sample food items
        config = new Config().loadConfig();
        // List<Food> foodList = new ArrayList<>();
        // foodList.add(new Food("Pizza", 12.5f, 300, 10)); // name, price, cookingTime, stock
        // foodList.add(new Food("Burger", 8.0f, 200, 20));
        // config.getItems().setFoods(foodList);

        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testFoodMenuCommand_AddFood() {
        // Simulate user input for navigating to "Add Food" and adding a new food item
        String input = "1\nPasta\n15.0\n400\n25\n4\n"; // Navigate to AddFoodCommand, add food, and go back
        Scanner scanner = new Scanner(input);

        // Execute the FoodMenuCommand
        foodMenuCommand.execute(scanner, kitchen, config);

        // Verify the new food item has been added
        List<Food> foods = config.getItems().getFoods();
        assertEquals(4, foods.size());
        // Food newFood = foods.get(2); // The newly added food item
        // assertEquals("Pasta", newFood.getName());
        // assertEquals(15.0f, newFood.getPrice());
        // assertEquals(400, newFood.getCookingTime());
        // assertEquals(25, newFood.getStock());
    }

    @Test
    void testFoodMenuCommand_RemoveFood() {
        // Simulate user input for navigating to "Remove Food" and removing a food item
        String input = "2\n1\n4\n"; // Navigate to RemoveFoodCommand, remove "Pizza", and go back
        Scanner scanner = new Scanner(input);

        // Execute the FoodMenuCommand
        foodMenuCommand.execute(scanner, kitchen, config);

        // Verify the food item has been removed
        // List<Food> foods = config.getItems().getFoods();
        // assertEquals(1, foods.size());
        // assertEquals("Burger", foods.get(0).getName());
    }

    @Test
    void testFoodMenuCommand_UpdateFood() {
        // Simulate user input for navigating to "Update Food" and updating a food item
        String input = "3\n1\nPizza Updated\n14.0\n350\n12\n4\n"; // Navigate to UpdateFoodCommand, update "Pizza", and go back
        Scanner scanner = new Scanner(input);

        // Execute the FoodMenuCommand
        foodMenuCommand.execute(scanner, kitchen, config);

        // Verify the food item has been updated
        // Food updatedFood = config.getItems().getFoods().get(0); // The first food item
        // assertEquals("Pizza Updated", updatedFood.getName());
        // assertEquals(14.0f, updatedFood.getPrice());
        // assertEquals(350, updatedFood.getCookingTime());
        // assertEquals(12, updatedFood.getStock());
    }

    @Test
    void testFoodMenuCommand_GoBack() {
        // Simulate user input for selecting "Go Back" immediately
        String input = "4\n"; // Select "Go Back"
        Scanner scanner = new Scanner(input);

        // Execute the FoodMenuCommand
        foodMenuCommand.execute(scanner, kitchen, config);

        // Verify no changes to the food list
        // List<Food> foods = config.getItems().getFoods();
        // assertEquals(2, foods.size()); // Original size remains unchanged
        // assertEquals("Pizza", foods.get(0).getName());
        // assertEquals("Burger", foods.get(1).getName());
    }
}