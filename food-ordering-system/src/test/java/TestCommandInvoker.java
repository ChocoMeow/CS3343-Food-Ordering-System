import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.CommandInvoker;
import com.fos.main.Kitchen;

public class TestCommandInvoker {

    private CommandInvoker commandInvoker;
    private Command command;
    private Scanner scanner;
    private Kitchen kitchen;

    @BeforeEach
    public void setUp() {
        commandInvoker = new CommandInvoker();
        command = mock(Command.class);
        scanner = mock(Scanner.class);
        kitchen = mock(Kitchen.class);
    }

    @Test
    public void testExecuteCommandWithValidCommand() {
        commandInvoker.executeCommand(command, scanner, kitchen);
        verify(command).execute(scanner, kitchen);
    }
}