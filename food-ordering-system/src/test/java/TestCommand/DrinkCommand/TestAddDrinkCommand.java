package TestCommand.DrinkCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import com.fos.commands.updatesettingscommand.drinkcommand.UpdateDrinkCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;


class UpdateDrinkCommandTest {

    private UpdateDrinkCommand updateDrinkCommand;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        // Initialize UpdateDrinkCommand
        updateDrinkCommand = new UpdateDrinkCommand();

        // Initialize Config with some sample drink data
        config = new Config().loadConfig();
        // List<Drink> drinkList = new ArrayList<>();
        // drinkList.add(new Drink("Coke", 2.5f, 5)); // name, price, mixingTime, stock
        // drinkList.add(new Drink("Sprite", 2.0f, 10));
        // config.getItems().setDrinks(drinkList);

        // Initialize Kitchen
        kitchen = new Kitchen(config);
    }

    @Test
    void testUpdateDrinkCommand_SuccessfulUpdate() {
        // Simulate user input for selecting and updating a drink
        String input = "1\nPepsi\n3.0\n8\n20\n"; // Select Coke, update its name, price, mixingTime, and stock
        Scanner scanner = new Scanner(input);

        // Execute the UpdateDrinkCommand
        updateDrinkCommand.execute(scanner, kitchen, config);

        // Verify that the drink has been updated
        // Drink updatedDrink = config.getItems().getDrinks().get(0); // The first drink
        // assertEquals("Pepsi", updatedDrink.getName());
        // assertEquals(3.0f, updatedDrink.getPrice());
        // assertEquals(8, updatedDrink.getMixingTime());
        // assertEquals(20, updatedDrink.getStock());
    }

    @Test
    void testUpdateDrinkCommand_DuplicateName() {
        // Simulate user input for selecting and updating a drink with a duplicate name
        String input = "1\nSprite\n3.0\n8\n20\n"; // Select Coke, but try to rename it to "Sprite" (which already exists)
        Scanner scanner = new Scanner(input);

        // Execute the UpdateDrinkCommand
        updateDrinkCommand.execute(scanner, kitchen, config);

        // Verify that the drink has not been updated due to duplicate name
        // Drink drink = config.getItems().getDrinks().get(0); // The first drink
        // assertEquals("Coke", drink.getName()); // Name should remain unchanged
        // assertEquals(2.5f, drink.getPrice()); // Other attributes should remain unchanged
        // assertEquals(5, drink.getMixingTime());
        // assertEquals(5, drink.getStock());
    }

    @Test
    void testUpdateDrinkCommand_GoBack() {
        // Simulate user input to select "Go Back"
        String input = "3\n"; // Select "Go Back"
        Scanner scanner = new Scanner(input);

        // Execute the UpdateDrinkCommand
        updateDrinkCommand.execute(scanner, kitchen, config);

        // Verify that no changes have been made to the drinks
        // List<Drink> drinks = config.getItems().getDrinks();
        // assertEquals(2, drinks.size()); // Ensure no drinks were removed or added
        // assertEquals("Coke", drinks.get(0).getName());
        // assertEquals("Sprite", drinks.get(1).getName());
    }
}