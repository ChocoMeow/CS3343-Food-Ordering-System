import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fos.commands.ViewKitchenProcesses;
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

class TestViewKitchenProcesses  {

    private ViewKitchenProcesses viewKitchenProcesses;
    private Kitchen kitchen;

    @BeforeEach
    void setUp() {
        viewKitchenProcesses = new ViewKitchenProcesses();
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

        // Create sample orders
        Order order1 = new Order();
        order1.addFood(food1);
        order1.addDrink(drink1);
        order1.addFood(food2); // Additional item for variety

        Order order2 = new Order();
        order2.addFood(food2);
        order2.addDrink(drink2);

        // Add orders to the kitchen
        kitchen.placeOrder(order1);
        kitchen.placeOrder(order2);
    }

    @Test
    void testExecuteWithOrders() throws InterruptedException {
        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Simulate user input for the scanner (pressing ENTER)
        String simulatedInput = "\n";  // Simulates an Enter key press
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        
        // Create a scanner for input
        Scanner scanner = new Scanner(System.in);

        // Execute the command
        viewKitchenProcesses.execute(scanner, kitchen, new Config());

        // Allow some time for the refresh thread to run
        Thread.sleep(1500); // Sleep for a bit to allow the thread to display output
        
        // Restore the original System.out
        System.setOut(originalOut);
        System.setIn(System.in); // Restore original System.in

        // Get the output as a String
        String output = outputStream.toString();

        // Assertions to verify the output
        assertTrue(output.contains("View Kitchen Process List"));
        assertTrue(output.contains("Pizza"));
        assertTrue(output.contains("Burger"));
        assertTrue(output.contains("Coke"));
        assertTrue(output.contains("Sprite"));
        assertTrue(output.contains("Press 'ENTER' to exit view..."));
    }

    @Test
    void testExecuteWithNoOrders() throws InterruptedException {
        // Clear orders from kitchen
        kitchen.getOrders().clear();

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Simulate user input for the scanner (pressing ENTER)
        String simulatedInput = "\n";  // Simulates an Enter key press
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        
        // Create a scanner for input
        Scanner scanner = new Scanner(System.in);

        // Execute the command
        viewKitchenProcesses.execute(scanner, kitchen, new Config());

        // Allow some time for the refresh thread to run
        Thread.sleep(1500); // Sleep for a bit to allow the thread to display output
        
        // Restore the original System.out
        System.setOut(originalOut);
        System.setIn(System.in); // Restore original System.in

        // Get the output as a String
        String output = outputStream.toString();

        // Assertions to verify the output
        assertTrue(output.contains("View Kitchen Process List"));
        assertTrue(output.contains("There is currently no order queued!"));
        assertTrue(output.contains("Press 'ENTER' to exit view..."));
    }
}