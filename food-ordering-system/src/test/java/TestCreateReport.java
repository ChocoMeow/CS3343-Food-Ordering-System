import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fos.commands.CreateReport;
import com.fos.main.Kitchen;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class TestCreateReport {

    @InjectMocks
    private CreateReport createReport;

    @Mock
    private Kitchen kitchen;

    @Mock
    private Chef chef1;

    @Mock
    private Chef chef2;

    @Mock
    private Bartender bartender1;

    @Mock
    private Bartender bartender2;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Mock kitchen behavior
        when(kitchen.getTotalHandledOrder()).thenReturn((int) 5L);
        when(kitchen.getTotalProfit()).thenReturn((float) 1000.00);
        when(kitchen.getTotalWaitingTime()).thenReturn(30000L); // 30 seconds total waiting time
        when(kitchen.getChefs()).thenReturn(Arrays.asList(chef1, chef2));
        when(kitchen.getBartenders()).thenReturn(Arrays.asList(bartender1, bartender2));

        // Mock chef behavior
        when(chef1.getName()).thenReturn("Gordon");
        when(chef1.getTotalHandledFoods()).thenReturn(20);
        when(chef2.getName()).thenReturn("Marco");
        when(chef2.getTotalHandledFoods()).thenReturn(15);

        // Mock bartender behavior
        when(bartender1.getName()).thenReturn("Tom");
        when(bartender1.getTotalHandledDrinks()).thenReturn(50);
        when(bartender2.getName()).thenReturn("Jerry");
        when(bartender2.getTotalHandledDrinks()).thenReturn(30);
    }

    @Test
    public void testExecute() {
        // Simulate user input to exit
        // Scanner scanner = new Scanner("e\n");

        // Execute the command
        // createReport.execute(scanner, kitchen);

        // Capture the output
        // String output = outputStream.toString();

        // Assertions for the report output
        // assertTrue(output.contains("Total Handled Orders: 5"), "Output should contain total handled orders.");
        // assertTrue(output.contains("Total Profit: $1000.00"), "Output should contain total profit.");
        // assertTrue(output.contains("Average Waiting Time: 00:06"), "Output should contain average waiting time."); // 30 seconds = 00:06

        // assertTrue(output.contains("Gordon"), "Output should contain chef's name 'Gordon'.");
        // assertTrue(output.contains("20"), "Output should contain total handled foods by Gordon.");
        // assertTrue(output.contains("Marco"), "Output should contain chef's name 'Marco'.");
        // assertTrue(output.contains("15"), "Output should contain total handled foods by Marco.");
        
        // assertTrue(output.contains("Tom"), "Output should contain bartender's name 'Tom'.");
        // assertTrue(output.contains("50"), "Output should contain total handled drinks by Tom.");
        // assertTrue(output.contains("Jerry"), "Output should contain bartender's name 'Jerry'.");
        // assertTrue(output.contains("30"), "Output should contain total handled drinks by Jerry.");
        
        // assertTrue(output.contains("\nPress Enter to exit..."), "Output should prompt to press Enter to exit.");

        // Clean up
        // scanner.close();
    }
}