package TestCommand.BartenderCommand;

import com.fos.commands.updatesettingscommand.bartendercommand.BartenderMenuCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Bartender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestBartenderMenuCommand {

    private BartenderMenuCommand command;
    private Config config;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        command = new BartenderMenuCommand();
        config = new Config().loadConfig();// Initialize a new Config instance
        config.bartenders = new ArrayList<>(); // Initialize the bartenders list
        kitchen = new Kitchen(config); // Assuming Kitchen has a constructor that accepts Config
        assertEquals("Bartenders Menu", command.getCommandName());
    }

    @Test
    void testExecute_AddBartender() {
        // Simulate user input to add a bartender
        String input = "1\nJohn Doe\n4\n"; // Select Add Bartender, enter name, and go back
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in); // Initialize scanner after setting System.in

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.bartenders.size()); // One bartender should be added
        assertEquals("John Doe", config.bartenders.get(0).getName()); // Check the name
    }

    @Test
    void testExecute_RemoveBartender() {
        // Arrange
        config.bartenders.add(new Bartender("John Doe")); // Prepopulate with a bartender
        String input = "2\n1\n4\n"; // Select Remove Bartender, choose the existing bartender, and go back
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in); // Initialize scanner after setting System.in

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(0, config.bartenders.size()); // No bartenders should remain
    }

    @Test
    void testExecute_UpdateBartender() {
        // Arrange
        config.bartenders.add(new Bartender("John Doe")); // Prepopulate with a bartender
        String input = "3\n1\nJane Doe\n4\n"; // Select Update Bartender, choose the existing one, enter new name, and go back
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in); // Initialize scanner after setting System.in

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, config.bartenders.size()); // Still one bartender
        assertEquals("Jane Doe", config.bartenders.get(0).getName()); // Check if the name was updated
    }

    @Test
    void testExecute_GoBack() {
        // Simulate user input to go back
        String input = "4\n"; // Choose Go Back
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in); // Initialize scanner after setting System.in

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(0, config.bartenders.size()); // No change in bartenders

    }
}