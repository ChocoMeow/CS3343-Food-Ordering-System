package com.fos.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.Task.Task;
import com.fos.commands.Command;
import com.fos.commands.CreateOrderCommand;
import com.fos.commands.CreateReport;
import com.fos.commands.ViewKitchenProcesses;
import com.fos.commands.updatesettingscommand.UpdateSettingsCommand;

public class Main {
    public static Kitchen kitchen;

    public static void main(String[] args) {
        Config config = Config.loadConfig();

        // Initialize kitchen
        kitchen = new Kitchen(config);

        // Start the background task to process orders
        Task orderProcessingTask = new Task(0, 1) {
            @Override
            public void execute() {
                kitchen.processOrders();
            }
        };
        orderProcessingTask.start();

        // Command menu
        List<Command> commandList = new ArrayList<>();
        commandList.add(new CreateOrderCommand());
        commandList.add(new ViewKitchenProcesses());
        commandList.add(new CreateReport());
        commandList.add(new UpdateSettingsCommand());
        
        CommandFactory commandFactory = new CommandFactory(commandList);

        CommandInvoker invoker = new CommandInvoker();
        Scanner scanner = new Scanner(System.in);

        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());
        List<String> additionalCommands = List.of("Exit");

        while (true) {
            Utils.clearConsole();
            int choice = Utils.printMenu(scanner, commands, additionalCommands);

            if (choice == commands.size() + 1) {
                orderProcessingTask.stop();
                System.out.println("Simulator stopped.");
                scanner.close();
                return;
            }

            Command command = commands.get(choice - 1);
            invoker.executeCommand(command, scanner, kitchen, config);
        }
    }
}
