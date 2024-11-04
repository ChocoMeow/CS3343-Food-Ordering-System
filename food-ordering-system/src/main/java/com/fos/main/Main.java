package com.fos.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fos.Task.Task;
import com.fos.commands.Command;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

public class Main {
    public static Kitchen kitchen;

    public static void main(String[] args) {
        // Create chefs and bartenders
        Chef chef1 = new Chef("Gordon");
        Chef chef2 = new Chef("Jamie");
        Bartender bartender1 = new Bartender("Mix Master");

        // Initialize kitchen
        kitchen = new Kitchen(Arrays.asList(chef1, chef2), Arrays.asList(bartender1));

        // Start the background task to process orders
        Task orderProcessingTask = new Task(0, 1) {
            @Override
            public void execute() {
                kitchen.processOrders();
            }
        };
        orderProcessingTask.start();

        // Command menu
        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvoker();
        Scanner scanner = new Scanner(System.in);

        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());

        while (true) {
            Utils.clearConsole();
            System.out.println("\n--- Restaurant Simulator Menu ---");
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), commands.get(i).getCommandName());
            }
            System.out.println((commands.size() + 1) + ". Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command, scanner, kitchen);

            } else if (choice == commands.size() + 1) {
                orderProcessingTask.stop();
                System.out.println("Simulator stopped.");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
