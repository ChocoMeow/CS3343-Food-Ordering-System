package TestCommand.DrinkCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.updatesettingscommand.drinkcommand.RemoveDrinkCommand;
import com.fos.item.Drink;
import com.fos.main.Config;
import com.fos.main.Kitchen;

import static org.junit.jupiter.api.Assertions.*;

class TestRemoveDrinkCommand {

    private RemoveDrinkCommand removeDrinkCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize RemoveDrinkCommand
        removeDrinkCommand = new RemoveDrinkCommand();

        // Initialize Config with some sample drink data
        config = new Config().loadConfig();

        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testRemoveDrinkCommand_SuccessfulRemoval() {
        // Simulate user input for selecting a drink to remove
        String input = "2\n"; // Select "Sprite" (2nd drink) for removal
        Scanner scanner = new Scanner(input);

        // Execute the RemoveDrinkCommand
        removeDrinkCommand.execute(scanner, kitchen, config);

    }

    // @Test
    // void testRemoveDrinkCommand_GoBack() {
    //     // Simulate user input to select "Go Back" instead of removing a drink
    //     String input = "4\n"; // 4th option corresponds to "Go Back" (3 drinks + 1 for "Go Back")
    //     Scanner scanner = new Scanner(input);

    //     // Execute the RemoveDrinkCommand
    //     removeDrinkCommand.execute(scanner, kitchen, config);

    // }

    @Test
    void testRemoveDrinkCommand_RemoveFirstDrink() {
        // Simulate user input for selecting the first drink to remove
        String input = "1\n"; // Select "Coke" (1st drink) for removal
        Scanner scanner = new Scanner(input);

        // Execute the RemoveDrinkCommand
        removeDrinkCommand.execute(scanner, kitchen, config);
    }

    @Test
    void testRemoveDrinkCommand_RemoveLastDrink() {
        // Simulate user input for selecting the last drink to remove
        String input = "3\n"; // Select "Fanta" (3rd drink) for removal
        Scanner scanner = new Scanner(input);

        // Execute the RemoveDrinkCommand
        removeDrinkCommand.execute(scanner, kitchen, config);

    }
}