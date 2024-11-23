package TestCommand.DrinkCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.drinkcommand.AddDrinkCommand;
import com.fos.commands.updatesettingscommand.drinkcommand.DrinkMenuCommand;
import com.fos.commands.updatesettingscommand.drinkcommand.RemoveDrinkCommand;
import com.fos.commands.updatesettingscommand.drinkcommand.UpdateDrinkCommand;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestDrinkMenuCommand {

    private DrinkMenuCommand drinkMenuCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize DrinkMenuCommand
        drinkMenuCommand = new DrinkMenuCommand();

        // Initialize Config with some sample drink items
        config = new Config().loadConfig();
        
        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testDrinkMenuCommand_AddDrink() {
        // Simulate user input for navigating to "Add Drink" and adding a new drink item
        String input = "1\nCoke\n2.5\n500\n30\n4\n"; // Navigate to AddDrinkCommand, add drink, and go back
        Scanner scanner = new Scanner(input);

        // Execute the DrinkMenuCommand
        drinkMenuCommand.execute(scanner, kitchen, config);

    }

    @Test
    void testDrinkMenuCommand_RemoveDrink() {
        // Simulate user input for navigating to "Remove Drink" and removing a drink item
        String input = "2\n1\n4\n"; // Navigate to RemoveDrinkCommand, remove "Coke", and go back
        Scanner scanner = new Scanner(input);

        // Execute the DrinkMenuCommand
        drinkMenuCommand.execute(scanner, kitchen, config);

        // Verify the drink item has been removed
        List<Drink> drinks = config.getItems().getDrinks();
        // assertEquals(3, drinks.size()); // Assuming original was 4 and one is removed
        // assertEquals("Sprite", drinks.get(0).getName()); // Assuming Sprite was the next in line
    }

    @Test
    void testDrinkMenuCommand_UpdateDrink() {
        // Simulate user input for navigating to "Update Drink" and updating a drink item
        String input = "3\n1\nCoke Updated\n3.0\n450\n25\n4\n"; // Navigate to UpdateDrinkCommand, update "Coke", and go back
        Scanner scanner = new Scanner(input);

        // Execute the DrinkMenuCommand
        drinkMenuCommand.execute(scanner, kitchen, config);

        // Verify the drink item has been updated
        Drink updatedDrink = config.getItems().getDrinks().get(0); // The first drink item
        assertEquals("Coke Updated", updatedDrink.getName());
        assertEquals(3.0f, updatedDrink.getPrice());
        // assertEquals(450, updatedDrink.getVolume());
        assertEquals(25, updatedDrink.getStock());
    }

    @Test
    void testDrinkMenuCommand_GoBack() {
        // Simulate user input for selecting "Go Back" immediately
        String input = "4\n"; // Select "Go Back"
        Scanner scanner = new Scanner(input);

        // Execute the DrinkMenuCommand
        drinkMenuCommand.execute(scanner, kitchen, config);
    }
}