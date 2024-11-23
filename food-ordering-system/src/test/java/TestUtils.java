import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import com.fos.main.Utils;

public class TestUtils {

    @Test
    public void testFormatDate() {
        long timestamp = 1633036800000L; // Example timestamp (Oct 1, 2021)
        String expectedDate = "10 01 05:20:00"; // Expected format based on timestamp
        String formattedDate = Utils.formatDate(timestamp);
        assertEquals(expectedDate, formattedDate, "Formatted date should match the expected format");
    }

    @Test
    public void testFormatTime() {
        long totalSeconds = 125; // 2 minutes and 5 seconds
        String expectedTime = "02:05";
        String formattedTime = Utils.formatTime(totalSeconds);
        assertEquals(expectedTime, formattedTime, "Formatted time should match the expected format");
    }

    @Test
    public void testFormatTimeLeft() {
        long millis = 125000; // 125 seconds
        String expectedTimeLeft = "02:05";
        String formattedTimeLeft = Utils.formatTimeLeft(millis);
        assertEquals(expectedTimeLeft, formattedTimeLeft, "Formatted time left should match the expected format");
    }

    @Test
    public void testFormatTimeLeftWithNegativeMillis() {
        long millis = -5000; // Negative milliseconds
        String expectedTimeLeft = "00:00"; // Should return 00:00 for negative values
        String formattedTimeLeft = Utils.formatTimeLeft(millis);
        assertEquals(expectedTimeLeft, formattedTimeLeft, "Formatted time left for negative millis should be 00:00");
    }

    @Test
    public void testClearConsole() {
        assertDoesNotThrow(() -> Utils.clearConsole(), "clearConsole should not throw any exceptions");
    }

    @Test
    public void testAddColor() {
        String text = "Hello";
        String coloredText = Utils.addColor(text, Utils.GREEN);
        assertTrue(coloredText.startsWith(Utils.GREEN) && coloredText.endsWith(Utils.RESET), "Colored text should be wrapped with ANSI codes");
    }

    // @Test
    // public void testPrintMenu() {
    //     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //     PrintStream originalOut = System.out;
    //     System.setOut(new PrintStream(outputStream));
        
    //     // Simulate input for the menu
    //     ByteArrayInputStream inputStream = new ByteArrayInputStream("0\n".getBytes());
    //     System.setIn(inputStream);

    //     Scanner scanner = new Scanner(System.in);
    //     Utils.printMenu(scanner, List.of(), List.of("Option 1", "Option 2"));

    //     System.setOut(originalOut);
    //     String output = outputStream.toString();
    //     assertTrue(output.contains("There are no options to choose!"), "Should inform about no options available");
    // }

    @Test
    public void testCreateSelectionFormValidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // Simulate user input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("First Command", "Second Command");
        int choice = Utils.createSelectionForm(scanner, "Test Header", "Enter your choice", options, List.of("Go Back"));

        System.setOut(originalOut);
        assertEquals(1, choice, "Should return the valid choice of 1");
        String output = outputStream.toString();
        assertTrue(output.contains("Enter your choice"), "Should prompt for input");
    }

    @Test
    public void testCreateSelectionFormInvalidInput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Simulate invalid input followed by valid input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("999\n1\n".getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);

        List<String> options = List.of("First Command", "Second Command");
        int choice = Utils.createSelectionForm(scanner, "Test Header", "Enter your choice", options, List.of("Go Back"));

        System.setOut(originalOut);
        assertEquals(1, choice, "Should return the valid choice of 1 after invalid input");
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice. Please enter a number"), "Should inform about invalid choice");
    }

    @Test
    public void testCreateInputFieldValidString() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Test Input\n".getBytes());
        Scanner scanner = new Scanner(inputStream);
        Map<String, Object> result = Utils.createInputField(scanner, "inputField", "Enter a string", "string", true);
        assertEquals("Test Input", result.get("inputField"), "The input should match the provided string value");
    }

    @Test
    public void testCreateInputFieldEmptyRequired() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("\nTest Input\n".getBytes()); // Empty, then valid input
        Scanner scanner = new Scanner(inputStream);
        Map<String, Object> result = Utils.createInputField(scanner, "inputField", "Enter a string", "string", true);
        assertEquals("Test Input", result.get("inputField"), "The input should match the provided string value");
    }

    @Test
    public void testCreateInputFieldInvalidInteger() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("abc\n123\n".getBytes());
        Scanner scanner = new Scanner(inputStream);
        Map<String, Object> result = Utils.createInputField(scanner, "intField", "Enter an integer", "integer", true);
        assertEquals(123, result.get("intField"), "The input should convert valid integer input correctly");
    }

    // @Test
    // public void testCreateInputFieldNegativeInteger() {
    //     ByteArrayInputStream inputStream = new ByteArrayInputStream("-5\n".getBytes()); // Negative integer input
    //     Scanner scanner = new Scanner(inputStream);
    //     Map<String, Object> result = Utils.createInputField(scanner, "intField", "Enter an integer", "integer", true);
    //     assertNull(result.get("intField"), "Should not accept negative integers");
    // }

    @Test
    public void testCreateInputFieldValidFloat() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("12.34\n".getBytes());
        Scanner scanner = new Scanner(inputStream);
        Map<String, Object> result = Utils.createInputField(scanner, "floatField", "Enter a float", "float", true);
        assertEquals(12.34f, result.get("floatField"), "The input should match the provided float value");
    }

    // @Test
    // public void testCreateInputFieldNegativeFloat() {
    //     ByteArrayInputStream inputStream = new ByteArrayInputStream("-5.67\n".getBytes());
    //     Scanner scanner = new Scanner(inputStream);
    //     Map<String, Object> result = Utils.createInputField(scanner, "floatField", "Enter a float", "float", true);
    //     assertNull(result.get("floatField"), "Should not accept negative float values");
    // }
}