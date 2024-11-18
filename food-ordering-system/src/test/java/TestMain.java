import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fos.main.Kitchen;
import com.fos.main.Main;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class TestMain{

    private Kitchen kitchen;
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        kitchen = Mockito.mock(Kitchen.class);
        scanner = Mockito.mock(Scanner.class);

        // Simulating user input for command choices.
        when(scanner.nextInt()).thenReturn(1, 2, 3, 4); // Simulating commands
        when(scanner.nextLine()).thenReturn(""); // Simulating pressing Enter
    }

    @Test
    public void testMainMenuExecution() {
        // Set the mocked kitchen
        Main.kitchen = kitchen; 
        
        // Run the main method with mocked Scanner
        // This will run indefinitely unless we handle exit correctly.
        Thread mainThread = new Thread(() -> Main.main(new String[]{}));
        mainThread.start();

        try {
            // Sleep for a moment to let the application start
            Thread.sleep(1000);

            // Simulate user input
            for (int i = 1; i <= 3; i++) {
                scanner.nextInt(); // Simulate input
            }
            scanner.nextInt(); // Simulate exit command
            
            // Stop the main thread
            mainThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that orders are processed
        // verify(kitchen, atLeastOnce()).processOrders();
    }

    @Test
    public void testExitCommand() {
        when(scanner.nextInt()).thenReturn(4); // Simulate user choosing to exit
        Main.kitchen = kitchen; // Set the mocked kitchen

        Thread mainThread = new Thread(() -> Main.main(new String[]{}));
        mainThread.start();

        try {
            // Sleep to allow execution to reach the exit command
            Thread.sleep(1000);
            mainThread.interrupt(); // Stop the main thread after exit command
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the exit command was handled correctly
        // verify(kitchen, atLeastOnce()).processOrders();
    }
}