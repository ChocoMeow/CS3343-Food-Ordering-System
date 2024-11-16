import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fos.commands.ViewKitchenProcesses;
import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Kitchen;
import com.fos.main.Order;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TestViewKitchenProcesses {

    @InjectMocks
    private ViewKitchenProcesses viewKitchenProcesses;

    @Mock
    private Kitchen kitchen;

    @Mock
    private Order order;

    @Mock
    private Chef chef;

    @Mock
    private Bartender bartender;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Mock the kitchen behavior
        Queue<Order> ordersQueue = new LinkedList<>(Arrays.asList(order));
        when(kitchen.getOrders()).thenReturn(ordersQueue); // Add this line to provide the order queue
        when(kitchen.getChefs()).thenReturn(Arrays.asList(chef));
        when(kitchen.getBartenders()).thenReturn(Arrays.asList(bartender));

        // Mock order details
        when(order.getOrderTime()).thenReturn(System.currentTimeMillis());
        when(order.getWaitingTime()).thenReturn(5000L);
        when(order.getExpectedFinishTime()).thenReturn(System.currentTimeMillis() + 15000);
        when(order.getFoods()).thenReturn(Arrays.asList(new Food("Pasta", 25, 20, 0)));
        when(order.getDrinks()).thenReturn(Arrays.asList(new Drink("Cocktail", 20, 40, 0)));
        
        // Mock chef activity
        when(chef.getName()).thenReturn("Gordon");
        when(chef.getCurrentFood()).thenReturn(new Food("Pasta", 25, 20, 0));
        when(chef.getRemainingCookingTime()).thenReturn(10000L);

        // Mock bartender activity
        when(bartender.getName()).thenReturn("Tom");
        when(bartender.getCurrentDrink()).thenReturn(new Drink("Cocktail", 20, 40, 0));
        when(bartender.getRemainingMixingTime()).thenReturn(5000L);
    }

    @Test
    public void testExecute() {
        // Create a scanner that simulates user input
        Scanner scanner = new Scanner("e\n"); // The user will input 'e' to exit

        // Execute the command
        viewKitchenProcesses.execute(scanner, kitchen);
        
        // Wait a bit to allow the thread to run
        try {
            Thread.sleep(1500); // Sleep for a short time to allow output
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check the output
        String output = outputStream.toString();
        assertTrue(output.contains("Current Kitchen Processes"), "Output should contain 'Current Kitchen Processes'");
        assertTrue(output.contains("Gordon"), "Output should contain chef name 'Gordon'"); // Corrected to true
        assertTrue(output.contains("Tom"), "Output should contain bartender name 'Tom'");
        assertTrue(output.contains("Pasta"), "Output should contain food item 'Pasta'");
        assertTrue(output.contains("Cocktail"), "Output should contain drink item 'Cocktail'");
        
        // Clean up
        scanner.close();
    }

    @Test
    public void testDisplayCurrentOrderDetails() {
        viewKitchenProcesses.displayCurrentOrderDetails(order);
        String output = outputStream.toString();
        assertTrue(output.contains("Current Order"), "Output should contain 'Current Order'");
        assertTrue(output.contains("Pasta"), "Output should contain food item 'Pasta'");
        assertTrue(output.contains("Cocktail"), "Output should contain drink item 'Cocktail'");
    }
}