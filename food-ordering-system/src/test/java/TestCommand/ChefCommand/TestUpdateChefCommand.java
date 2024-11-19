package TestCommand.ChefCommand;

import com.fos.commands.updatesettingscommand.chefcommand.UpdateChefCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestUpdateChefCommand {

    private UpdateChefCommand command;
    private Config config;
    private Kitchen kitchen;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new UpdateChefCommand();
        config = new Config().loadConfig(); // Initialize a new Config instance
        config.chefs = new ArrayList<>(); // Initialize chefs list
        kitchen = new Kitchen(config); // Assuming Kitchen has a constructor that accepts Config
        scanner = new Scanner(System.in); // Create a new Scanner instance
        assertEquals("Update Chef", command.getCommandName());
    }

    @Test
    void testExecute_UpdateChefNameSuccessfully() {
        // Arrange
        Chef chef = new Chef("John Doe");
        config.getChefs().add(chef); // Add existing chef
        String input = "1\nJane Doe\n"; // Simulate user input: select chef and new name
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals("Jane Doe", config.getChefs().get(0).getName()); // Check if the name was updated
    }

    @Test
    void testExecute_UpdateChefNameToDuplicate() {
        // Arrange
        Chef chef1 = new Chef("John Doe");
        Chef chef2 = new Chef("Jane Doe");
        config.getChefs().add(chef1); // Add existing chef
        config.getChefs().add(chef2); // Add another chef
        String input = "1\nJane Doe\n"; // Attempt to change chef1's name to chef2's name
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals("John Doe", config.getChefs().get(0).getName()); // Name should remain unchanged
        assertEquals("Jane Doe", config.getChefs().get(1).getName()); // Second chef's name should remain unchanged
    }

    @Test
    void testExecute_GoBack() {
        // Arrange
        Chef chef = new Chef("John Doe");
        config.getChefs().add(chef); // Add existing chef
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