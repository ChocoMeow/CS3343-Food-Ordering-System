package TestCommand.BartenderCommand;

import com.fos.commands.updatesettingscommand.bartendercommand.AddBartenderCommand;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Bartender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAddBartenderCommand  {

    private AddBartenderCommand command;
    private Kitchen kitchen;
    private Config config;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        command = new AddBartenderCommand();
        kitchen = mock(Kitchen.class);
        config = mock(Config.class);
        scanner = mock(Scanner.class);
    }

    @Test
    void testExecute_WhenBartenderDoesNotExist_AddsBartender() {
        // Arrange
        String bartenderName = "John Doe";
        when(scanner.nextLine()).thenReturn(bartenderName);

        ArrayList<Bartender> bartenders = new ArrayList<>();
        when(kitchen.getBartenders()).thenReturn(bartenders);
        
        ArrayList<Bartender> configBartenders = new ArrayList<>();
        when(config.getBartenders()).thenReturn(configBartenders);

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(1, configBartenders.size());
        assertEquals(bartenderName, configBartenders.get(0).getName());
        System.out.println("Bartender added successfully.");
    }

    @Test
    void testExecute_WhenBartenderExists_DoesNotAddBartender() {
        // Arrange
        String bartenderName = "Jane Doe";
        when(scanner.nextLine()).thenReturn(bartenderName);
        
        ArrayList<Bartender> bartenders = new ArrayList<>();
        bartenders.add(new Bartender(bartenderName));
        when(kitchen.getBartenders()).thenReturn(bartenders);
        
        ArrayList<Bartender> configBartenders = new ArrayList<>();
        when(config.getBartenders()).thenReturn(configBartenders);

        // Act
        command.execute(scanner, kitchen, config);

        // Assert
        assertEquals(0, configBartenders.size());
        assertEquals("Add Bartender", command.getCommandName());
        System.out.println("Bartender name must be unique. Please try again.");
    }
}