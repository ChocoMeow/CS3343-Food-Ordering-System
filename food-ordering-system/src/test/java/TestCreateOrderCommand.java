import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fos.commands.CreateOrderCommand;
import com.fos.item.Drink;
import com.fos.item.Food;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Order;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TestCreateOrderCommand  {

    private CreateOrderCommand createOrderCommand;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        createOrderCommand = new CreateOrderCommand();
        kitchen = new Kitchen(new Config().loadConfig()); // Assuming Config initializes necessary components
        setupSampleData();
    }

    private void setupSampleData() {
        // Create sample food and drink items
        Food food1 = new Food("Pizza", 12.0f, 30, 10);
        Food food2 = new Food("Burger", 8.0f, 20, 15);
        Drink drink1 = new Drink("Coke", 2.5f, 500, 30);
        Drink drink2 = new Drink("Sprite", 2.0f, 500, 25);

        // Add food and drinks to the kitchen
        kitchen.getAvailableFoods().add(food1);
        kitchen.getAvailableFoods().add(food2);
        kitchen.getAvailableDrinks().add(drink1);
        kitchen.getAvailableDrinks().add(drink2);
    }

    @Test
    void testCreateOrderWithItems() throws InterruptedException {
        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Simulate user input for the scanner
        String simulatedInput = "1\n1\n0\n"; // Add Pizza, then Coke, then finish
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a scanner for input
        Scanner scanner = new Scanner(System.in);

        // Execute the command
        createOrderCommand.execute(scanner, kitchen, new Config());

        // Restore the original System.out
        System.setOut(originalOut);
        
        // Get the output as a String
        String output = outputStream.toString();

        // Verify the order was placed
        assertEquals(1, kitchen.getOrders().size());

        // Check the details of the order
        // Order order = kitchen.getOrders().get(0);
        // assertEquals(1, order.getFoods().size());
        // assertEquals(1, order.getDrinks().size());
        // assertEquals("Pizza", order.getFoods().get(0).getName());
        // assertEquals("Coke", order.getDrinks().get(0).getName());

        // Check output contains confirmation messages
        // assertTrue(output.contains("Pizza added to the order."));
        // assertTrue(output.contains("Coke added to the order."));
    }

    @Test
    void testCreateOrderWithNoItems() throws InterruptedException {
        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Simulate user input for the scanner
        String simulatedInput = "0\n0\n"; // Finish without adding anything
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Create a scanner for input
        Scanner scanner = new Scanner(System.in);

        // Execute the command
        createOrderCommand.execute(scanner, kitchen, new Config());

        // Restore the original System.out
        System.setOut(originalOut);

        // Get the output as a String
        String output = outputStream.toString();

        // Verify that no order was placed
        assertTrue(kitchen.getOrders().isEmpty());

        // Check output contains cancellation message
        assertTrue(output.contains("No items in the order. Order cancelled."));
    }
}