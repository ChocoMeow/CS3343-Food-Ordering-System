package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.CommandFactory;
import com.fos.main.CommandInvoker;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Chef;

public class ChefMenuCommand extends Command {

    private static String commandName = "Update Chefs";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        List<Command> commandList = new ArrayList<>();
        commandList.add(new AddChefCommand());
        commandList.add(new RemoveChefCommand());
        commandList.add(new UpdateChefCommand());

        CommandFactory commandFactory = new CommandFactory(commandList);
        CommandInvoker invoker = new CommandInvoker();
        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());

        while (true) {
            Utils.clearConsole();
            System.out.println("\n--- Update Chefs Menu ---");
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), commands.get(i).getCommandName());
            }
            System.out.println((commands.size() + 1) + ". Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command, scanner, kitchen, config);
            } else if (choice == commands.size() + 1) {
                return;
            } else {
                System.out.println("Invalid choice. Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
