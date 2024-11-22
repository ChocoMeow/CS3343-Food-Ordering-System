package TestCommand.ChefCommand;

import com.fos.commands.updatesettingscommand.chefcommand.RemoveChefCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestRemoveChefCommand {

    private RemoveChefCommand command;
    private Config config;
    private Kitchen kitchen;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new RemoveChefCommand();
        config = new Config().loadConfig(); // Initialize a new Config instance
        config.chefs = new ArrayList<>(); // Initialize chefs list
        kitchen = new Kitchen(config); // Assuming Kitchen has a constructor that accepts Config
        scanner = new Scanner(System.in); // Create a new Scanner instance
        assertEquals("Remove Chefs", command.getCommandName());
    }

    @Test
    void testExecute_RemoveChefSuccessfully() {
        // Arrange
        Chef chef1 = new Chef("John Doe");
        Chef chef2 = new Chef("Jane Doe");
        config.getChefs().add(chef1); // Add chefs to the config
        config.getChefs().add(chef2); // Add another chef
        String input = "1\n"; // Simulate user input: select chef 1 to remove
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.getChefs().size()); // One chef should be removed
        assertEquals("Jane Doe", config.getChefs().get(0).getName()); // Ensure remaining chef is "Jane Doe"
    }

    @Test
    void testExecute_GoBackWithoutRemoving() {
        // Arrange
        Chef chef1 = new Chef("John Doe");
        config.getChefs().add(chef1); // Add a chef to the config
        String input = "2\n"; // Simulate user input: choose Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.getChefs().size()); // No change in chefs
        assertEquals("John Doe", config.getChefs().get(0).getName()); // Chef name should remain unchanged
    }
}