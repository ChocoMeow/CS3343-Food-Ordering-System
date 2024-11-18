package com.fos.commands.updatesettingscommand.drinkcommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.CommandFactory;
import com.fos.main.CommandInvoker;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class DrinkMenuCommand extends Command {
    private String commandName = "Update Drink Items";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        List<Command> commandList = new ArrayList<>();
        commandList.add(new AddDrinkCommand());
        commandList.add(new RemoveDrinkCommand());
        commandList.add(new UpdateDrinkCommand());

        CommandFactory commandFactory = new CommandFactory(commandList);
        CommandInvoker invoker = new CommandInvoker();
        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());
        List<String> additionalCommands = List.of("Go Back");

        while (true) {
            Utils.clearConsole();
            int choice = Utils.printMenu(scanner, commands, additionalCommands);
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
