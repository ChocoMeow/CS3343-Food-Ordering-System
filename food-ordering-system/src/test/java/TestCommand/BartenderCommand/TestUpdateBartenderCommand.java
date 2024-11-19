package TestCommand.BartenderCommand;

import com.fos.commands.updatesettingscommand.bartendercommand.UpdateBartenderCommand;
import com.fos.main.Config;
import com.fos.main.Utils;
import com.fos.worker.Bartender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UpdateBartenderCommandTest {

    private UpdateBartenderCommand command;
    private Config config;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new UpdateBartenderCommand();
        config = Config.loadConfig(); // Load the configuration from the file
        scanner = new Scanner(System.in); // Use a real scanner
    }

    @Test
    void testExecute_WhenBartenderExists_UpdatesBartenderName() {
        // Arrange
        ArrayList<Bartender> bartenders = new ArrayList<>();
        bartenders.add(new Bartender("John Doe"));
        bartenders.add(new Bartender("Jane Doe"));
        config.bartenders = bartenders; // Set the bartenders directly

        // Simulate user input to select the bartender and enter a new name
        System.setIn(new java.io.ByteArrayInputStream("1\nJohn Smith\n".getBytes())); // Simulate input
        scanner = new Scanner(System.in);

        // Act
        command.execute(scanner, null, config);

        // Assert
        assertEquals("Update Bartender",command.getCommandName());
        assertEquals("John Smith", bartenders.get(0).getName());
    }

    @Test
    void testExecute_WhenNewNameIsNotUnique_DoesNotUpdateBartenderName() {
        // Arrange
        ArrayList<Bartender> bartenders = new ArrayList<>();
        bartenders.add(new Bartender("John Doe"));
        bartenders.add(new Bartender("Jane Doe"));
        config.bartenders = bartenders; // Set the bartenders directly

        // Simulate user input to select the bartender and enter a duplicate name
        System.setIn(new java.io.ByteArrayInputStream("1\nJane Doe\n".getBytes())); // Simulate input
        scanner = new Scanner(System.in);

        // Act
        command.execute(scanner, null, config);

        // Assert
        assertEquals("John Doe", bartenders.get(0).getName()); // Name should not change
    }

    @Test
    void testExecute_WhenUserChoosesGoBack_DoesNotUpdateBartender() {
        // Arrange
        ArrayList<Bartender> bartenders = new ArrayList<>();
        bartenders.add(new Bartender("John Doe"));
        bartenders.add(new Bartender("Jane Doe"));
        config.bartenders = bartenders; // Set the bartenders directly

        // Simulate user input to choose "Go Back"
        System.setIn(new java.io.ByteArrayInputStream("3\n".getBytes())); // Simulate input
        scanner = new Scanner(System.in);

        // Act
        command.execute(scanner, null, config);

        // Assert
        assertEquals(2, bartenders.size()); // No change in the list
        assertEquals("John Doe", bartenders.get(0).getName());
        assertEquals("Jane Doe", bartenders.get(1).getName());
    }
}