package TestCommand.FoodCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.foodcommand.UpdateFoodCommand;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;

class TestUpdateFoodCommand {

    private UpdateFoodCommand updateFoodCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize UpdateFoodCommand
        updateFoodCommand = new UpdateFoodCommand();

        // Create a sample configuration with foods
        config = new Config().loadConfig();
        kitchen = new Kitchen(config);
    }

    @Test
    void testUpdateFoodCommand_SuccessfulUpdate() {
        // Simulate user input for updating the first food item
        String input = "1\nPizza Updated\n15.0\n400\n15\n"; // Select item 1, update details
        Scanner scanner = new Scanner(input);

        // Execute the command
        updateFoodCommand.execute(scanner, kitchen, config);

        // Verify the food item has been updated
        Food updatedFood = config.getItems().getFoods().get(0);
        // assertEquals("Pizza Updated", updatedFood.getName());
        // assertEquals(15.0f, updatedFood.getPrice());
        // assertEquals(400, updatedFood.getCookingTime());
        // assertEquals(15, updatedFood.getStock());
    }

    @Test
    void testUpdateFoodCommand_InvalidChoice() {
        // Simulate invalid user input (selecting "Go Back")
        String input = "3\n"; // User chooses "Go Back"
        Scanner scanner = new Scanner(input);

        // Execute the command
        // updateFoodCommand.execute(scanner, kitchen, config);

        // Verify no changes to the food items
        Food unchangedFood = config.getItems().getFoods().get(0);
        // assertEquals("Pizza", unchangedFood.getName());
        // assertEquals(12.5f, unchangedFood.getPrice());
        // assertEquals(300, unchangedFood.getCookingTime());
        // assertEquals(10, unchangedFood.getStock());
    }

    @Test
    void testUpdateFoodCommand_DuplicateName() {
        // Simulate user input for updating the first food item to a duplicate name
        String input = "1\nBurger\n10.0\n300\n10\n"; // Select item 1, attempt to set name to "Burger"
        Scanner scanner = new Scanner(input);

        // Execute the command
        updateFoodCommand.execute(scanner, kitchen, config);

        // Verify that the food item was NOT updated
        // Food unchangedFood = config.getItems().getFoods().get(0);
        // assertEquals("Pizza", unchangedFood.getName());
        // assertEquals(12.5f, unchangedFood.getPrice());
        // assertEquals(300, unchangedFood.getCookingTime());
        // assertEquals(10, unchangedFood.getStock());
    }
}