package com.fos.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;

public class Utils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM dd HH:mm:ss");

    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static String formatDate(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    public static String formatTime(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String formatTimeLeft(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", Math.max(minutes, 0), Math.max(seconds, 0));
    }

    public static void clearConsole() {
        // Clear console screen for Windows and Unix-based systems
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Could not clear the console.");
        }
    }

    public static String addColor(String text, String color) {
        return color + text + RESET;
    }

    public static int printMenu(Scanner scanner, List<Command> commands, List<String> additionalCommands) {
        String headerText = """
            ▗▄▄▖ ▗▄▄▄▖ ▗▄▄▖▗▄▄▄▖▗▄▖ ▗▖ ▗▖▗▄▄▖  ▗▄▖ ▗▖  ▗▖▗▄▄▄▖     ▗▄▄▖▗▄▄▄▖▗▖  ▗▖▗▖ ▗▖▗▖    ▗▄▖▗▄▄▄▖▗▄▖ ▗▄▄▖ 
            ▐▌ ▐▌▐▌   ▐▌     █ ▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌▐▌ ▐▌▐▛▚▖▐▌  █      ▐▌     █  ▐▛▚▞▜▌▐▌ ▐▌▐▌   ▐▌ ▐▌ █ ▐▌ ▐▌▐▌ ▐▌
            ▐▛▀▚▖▐▛▀▀▘ ▝▀▚▖  █ ▐▛▀▜▌▐▌ ▐▌▐▛▀▚▖▐▛▀▜▌▐▌ ▝▜▌  █       ▝▀▚▖  █  ▐▌  ▐▌▐▌ ▐▌▐▌   ▐▛▀▜▌ █ ▐▌ ▐▌▐▛▀▚▖
            ▐▌ ▐▌▐▙▄▄▖▗▄▄▞▘  █ ▐▌ ▐▌▝▚▄▞▘▐▌ ▐▌▐▌ ▐▌▐▌  ▐▌  █      ▗▄▄▞▘▗▄█▄▖▐▌  ▐▌▝▚▄▞▘▐▙▄▄▖▐▌ ▐▌ █ ▝▚▄▞▘▐▌ ▐▌""";

        System.out.println(headerText);
        
        // Create selection form
        return createSelectionForm(scanner, "Command Name", "Enter your choice", commands.stream().map(command -> command.getCommandName()).collect(Collectors.toList()), additionalCommands);
    }

    public static int createSelectionForm(Scanner scanner, String header, String prompt, List<String> options, List<String> additionalOptions) {
        int optionWidth = 4; // Width for numbering options
        int nameWidth = 40; // Width for option names
        int totalOptions = options.size() + additionalOptions.size();

        // Print the header of the table
        if (totalOptions != 0) {
            System.out.printf(addColor("%n%s | %-" + nameWidth + "s%n", MAGENTA), "No", header);
            System.out.println("-".repeat(optionWidth + nameWidth)); // Line separator

            // Print the options
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%-" + optionWidth + "s. | %s%n", addColor(String.valueOf(i + 1), BLUE), options.get(i));
            }

            // Print additional options if any
            for (int i = 0; i < additionalOptions.size(); i++) {
                System.out.printf("%-" + optionWidth + "s. | %s%n", addColor(String.valueOf(i + options.size() + 1), GREEN), additionalOptions.get(i));
            }

            System.out.println("-".repeat(optionWidth + nameWidth)); // Line separator

            int choice = -1;  // Default to an invalid choice
            boolean validInput = false;

            while (!validInput) {
                System.out.printf(addColor("\n" + prompt + " %s: ", YELLOW), addColor(String.format("[%d-%d]", 1, totalOptions), BLUE));
                String userInput = scanner.nextLine(); // Read input as a string

                try {
                    choice = Integer.parseInt(userInput); // Parse the string to an integer

                    if (choice < 1 || choice > totalOptions) {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + totalOptions + ".");
                    } else {
                        validInput = true;  // Input is valid, exit the loop
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            return choice;

        } else {
            System.out.print(addColor("\nThere are no options to choose!", RED));
            scanner.next();
            return -1; // Indicate no valid choice
        }
    }

    public static Map<String, Object> createInputField(Scanner scanner, String fieldName, String question, String inputType, boolean isRequired) {
        Map<String, Object> result = new HashMap<>();
        Object inputValue = null;

        while (inputValue == null) {
            System.out.printf("%-45s%s: ", question, isRequired ? addColor("(required)", MAGENTA) : addColor("(optional)", GREEN));
            String userInput = scanner.nextLine().trim();

            if (!isRequired && userInput.isEmpty()) {
                return new HashMap<>(); // Return empty map for optional input
            }

            try {
                switch (inputType.toLowerCase()) {
                    case "string":
                        inputValue = userInput;
                        break;
                    case "float":
                        inputValue = Float.parseFloat(userInput);
                        if ((Float) inputValue < 0) {
                            throw new NumberFormatException("Price must be a positive number.");
                        }
                        break;
                    case "integer":
                        inputValue = Integer.parseInt(userInput);
                        if ((Integer) inputValue < 0) {
                            throw new NumberFormatException("Value must be a non-negative integer.");
                        }
                        break;
                    default:
                        System.out.println(addColor("Unsupported input type. Please use 'String', 'Float', or 'Integer'.", RED));
                        inputValue = null;
                        break;
                }
            } catch (NumberFormatException e) {
                String message;
                if (inputType.equalsIgnoreCase("float")) {
                    message = "Invalid input: Please enter a valid floating-point number (e.g., 5.99).";
                } else if (inputType.equalsIgnoreCase("integer")) {
                    message = "Invalid input: Please enter a valid integer (e.g., 10).";
                } else {
                    message = "Invalid input. Please try again.";
                }
                System.out.println(addColor(message, RED));
                inputValue = null; // Reset inputValue for retry
            }
        }

        result.put(fieldName, inputValue);
        return result;
    }
}
