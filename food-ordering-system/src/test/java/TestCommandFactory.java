import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fos.commands.Command;
import com.fos.commands.CreateOrderCommand;
import com.fos.commands.ViewKitchenProcesses;
import com.fos.main.CommandFactory;
import com.fos.commands.CreateReport;

import java.util.Collection;

public class TestCommandFactory {

    private CommandFactory commandFactory;

    @BeforeEach
    public void setUp() {
        commandFactory = new CommandFactory();
    }

    @Test
    public void testGetCommandWithValidNumber() {
        Command command = commandFactory.getCommand(1);
        assertNotNull(command, "Command should not be null for valid command number");
        assertTrue(command instanceof CreateOrderCommand, "Command should be an instance of CreateOrderCommand");
        
        command = commandFactory.getCommand(2);
        assertNotNull(command, "Command should not be null for valid command number");
        assertTrue(command instanceof ViewKitchenProcesses, "Command should be an instance of ViewKitchenProcesses");
        
        command = commandFactory.getCommand(3);
        assertNotNull(command, "Command should not be null for valid command number");
        assertTrue(command instanceof CreateReport, "Command should be an instance of CreateReport");
    }

    @Test
    public void testGetCommandWithInvalidNumber() {
        Command command = commandFactory.getCommand(99);
        assertNull(command, "Command should be null for invalid command number");
    }

    @Test
    public void testGetAllCommands() {
        Collection<Command> commands = commandFactory.getAllCommands();
        assertNotNull(commands, "Command collection should not be null");
        assertEquals(3, commands.size(), "There should be three commands in the collection");
    }
}