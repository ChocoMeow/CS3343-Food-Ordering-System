package TestCommand.BartenderCommand;

import com.fos.commands.updatesettingscommand.bartendercommand.RemoveBartenderCommand;
import com.fos.main.Config;
import com.fos.worker.Bartender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestRemoveBartenderCommand {

    private RemoveBartenderCommand command;
    private Config config;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new RemoveBartenderCommand();
        config = new Config(); // Create a new Config instance
        config.bartenders = new ArrayList<>(); // Initialize the bartenders list
        scanner = new Scanner(System.in); // Use a real scanner
    }

    @Test
    void testExecute_WhenBartenderExists_RemovesBartender() {
        // Arrange
        Bartender bartender1 = new Bartender("John Doe");
        Bartender bartender2 = new Bartender("Jane Doe");
        config.bartenders.add(bartender1);
        config.bartenders.add(bartender2);

        // Simulate user input to select the bartender to remove
        System.setIn(new ByteArrayInputStream("1\n".getBytes())); // User selects "John Doe"
        scanner = new Scanner(System.in); // Reinitialize the scanner after setting System.in

        // Act
        command.execute(scanner, null, config);

        // Assert
        assertEquals(1, config.bartenders.size()); // Only one bartender should remain
        assertEquals("Jane Doe", config.bartenders.get(0).getName()); // Remaining bartender should be Jane Doe
        assertEquals("Remove Bartender",command.getCommandName());
    }

    @Test
    void testExecute_WhenUserChoosesGoBack_DoesNotRemoveBartender() {
        // Arrange
        Bartender bartender1 = new Bartender("John Doe");
        Bartender bartender2 = new Bartender("Jane Doe");
        config.bartenders.add(bartender1);
        config.bartenders.add(bartender2);

        // Simulate user input to choose "Go Back"
        System.setIn(new ByteArrayInputStream("3\n".getBytes())); // User chooses "Go Back"
        scanner = new Scanner(System.in); // Reinitialize the scanner after setting System.in

        // Act
        command.execute(scanner, null, config);

        // Assert
        assertEquals(2, config.bartenders.size()); // No change in the list
        assertEquals("John Doe", config.bartenders.get(0).getName());
        assertEquals("Jane Doe", config.bartenders.get(1).getName());
    }
}