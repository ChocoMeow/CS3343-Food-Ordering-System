package TestCommand.ChefCommand;

import com.fos.commands.updatesettingscommand.chefcommand.AddChefCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestAddChefCommand {

    private AddChefCommand command;
    private Config config;
    private Kitchen kitchen;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new AddChefCommand();
        config = new Config().loadConfig(); // Initialize a new Config instance
        config.chefs = new ArrayList<>(); // Initialize chefs list
        kitchen = new Kitchen(config); // Assuming Kitchen has a constructor that accepts Config
        scanner = new Scanner(System.in); // Create a new Scanner instance
        assertEquals("Add Chefs", command.getCommandName());
    }

    @Test
    void testExecute_AddUniqueChef() {
        // Simulate user input to add a unique chef
        String input = "John Doe\n"; // Enter chef name
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.getChefs().size()); // One chef should be added
        assertEquals("John Doe", config.getChefs().get(0).getName()); // Check the name
    }

    @Test
    void testExecute_AddDuplicateChef() {
        // Arrange
        config.getChefs().add(new Chef("John Doe")); // Add existing chef
        String input = "John Doe\n"; // Attempt to add the same chef name
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.getChefs().size()); // Still only one chef
        assertEquals("John Doe", config.getChefs().get(0).getName()); // Check the name
    }

    @Test
    void testExecute_AddDifferentChef() {
        // Arrange
        config.getChefs().add(new Chef("John Doe")); // Add existing chef
        String input = "Jane Doe\n"; // Add a different chef name
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(2, config.getChefs().size()); // Now two chefs should exist
        assertEquals("Jane Doe", config.getChefs().get(1).getName()); // Check the new name
    }
}