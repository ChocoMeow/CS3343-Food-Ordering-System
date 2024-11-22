package TestCommand.ChefCommand;

import com.fos.commands.updatesettingscommand.chefcommand.ChefMenuCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestChefMenuCommand {

    private ChefMenuCommand command;
    private Config config;
    private Kitchen kitchen;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new ChefMenuCommand();
        config = new Config().loadConfig();
        config.chefs = new ArrayList<>(); // Initialize chefs list
        kitchen = new Kitchen(config);
        scanner = new Scanner(System.in);
        assertEquals("Chefs Menu", command.getCommandName());
    }

    @Test
    void testExecute_AddChef() {
        // Simulate user input to add a chef: select Add Chef and enter name
        String input = "1\nJohn Doe\n4\n"; // 1: Add Chef, enter name, 4: Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config); 

        // Assert
        assertEquals(1, config.getChefs().size()); // One chef should be added
        assertEquals("John Doe", config.getChefs().get(0).getName()); // Check the name
    }

    @Test
    void testExecute_RemoveChef() {
        // Arrange
        config.getChefs().add(new Chef("John Doe")); // Prepopulate with a chef
        String input = "2\n1\n4\n"; // 2: Remove Chef, choose John Doe, 4: Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(0, config.getChefs().size()); // No chefs should remain
    }

    @Test
    void testExecute_UpdateChef() {
        // Arrange
        config.getChefs().add(new Chef("John Doe")); // Prepopulate with a chef
        String input = "3\n1\nJane Doe\n4\n"; // 3: Update Chef, choose John Doe, enter new name, 4: Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.getChefs().size()); // Still one chef
        assertEquals("Jane Doe", config.getChefs().get(0).getName()); // Check if the name was updated
    }

    @Test
    void testExecute_GoBack() {
        // Simulate user input for Go Back without making changes
        String input = "4\n"; // Select Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Set input stream
        scanner = new Scanner(System.in); // Reinitialize scanner

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(0, config.getChefs().size()); // No chefs should be present
    }
}