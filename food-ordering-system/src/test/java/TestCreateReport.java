import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.item.Food;
import com.fos.commands.CreateReport;
import com.fos.item.Drink;
import com.fos.worker.Chef;
import com.fos.worker.Bartender;

import static org.junit.jupiter.api.Assertions.*;

class TestCreateReport {

    private CreateReport createReport;
    private Kitchen kitchen;
    private Config config;

    @BeforeEach
    void setUp() {
        // Initialize the CreateReport command
        createReport = new CreateReport();

        // Load the configuration and create a kitchen
        config = new Config().loadConfig();
        kitchen = new Kitchen(config);

        // Populate kitchen with sample data
        setupSampleData();
    }

    private void setupSampleData() {
        // Create sample chefs and bartenders
        Chef chef1 = new Chef("Chef Gordon");
        Chef chef2 = new Chef("Chef Jamie");
        Bartender bartender1 = new Bartender("Bartender Alice");
        Bartender bartender2 = new Bartender("Bartender Bob");

        // Add chefs and bartenders to the kitchen
        kitchen.getChefs().add(chef1);
        kitchen.getChefs().add(chef2);
        kitchen.getBartenders().add(bartender1);
        kitchen.getBartenders().add(bartender2);

        // Add sample food items
        kitchen.getAvailableFoods().add(new Food("Pizza", 12.0f, 30, 10));
        kitchen.getAvailableFoods().add(new Food("Burger", 8.0f, 20, 15));

        // Add sample drink items
        kitchen.getAvailableDrinks().add(new Drink("Coke", 2.5f, 500, 30));
        kitchen.getAvailableDrinks().add(new Drink("Sprite", 2.0f, 500, 25));

        // Simulate order history
        kitchen.getHistoryItems().put("Pizza", 3);
        kitchen.getHistoryItems().put("Burger", 2);
        kitchen.getHistoryItems().put("Coke", 4);
        kitchen.getHistoryItems().put("Sprite", 1);
        kitchen.setTotalHandledOrder(10); // Simulate total handled orders
        kitchen.setTotalWaitingTime(120); // Simulate total waiting time
    }

    @Test
    void testCreateReport() {
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

        // Execute the CreateReport command
        createReport.execute(scanner, kitchen, config);

        // Restore the original System.out
        System.setOut(originalOut);
        System.setIn(System.in); // Restore original System.in

        // Get the output as a String
        String output = outputStream.toString();

        // Check if the output contains expected values
        assertTrue(output.contains("Total Handled Orders"));
        assertTrue(output.contains("Total Profit"));
        assertTrue(output.contains("Average Waiting Time"));
        assertTrue(output.contains("Chef Name"));
        assertTrue(output.contains("Bartender Name"));
        assertTrue(output.contains("Food Name"));
        assertTrue(output.contains("Drink Name"));

        // Additionally, you can validate specific outputs based on the sample data
        assertTrue(output.contains("Pizza")); // Check if Pizza appears in the report
        assertTrue(output.contains("Burger")); // Check if Burger appears in the report
        assertTrue(output.contains("Coke"));   // Check if Coke appears in the report
        assertTrue(output.contains("Sprite"));  // Check if Sprite appears in the report
    }
}