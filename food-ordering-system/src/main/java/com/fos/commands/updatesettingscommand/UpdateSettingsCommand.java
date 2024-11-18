package com.fos.commands.updatesettingscommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.commands.updatesettingscommand.bartendercommand.BartenderMenuCommand;
import com.fos.commands.updatesettingscommand.chefcommand.ChefMenuCommand;
import com.fos.commands.updatesettingscommand.drinkcommand.DrinkMenuCommand;
import com.fos.commands.updatesettingscommand.foodcommand.FoodMenuCommand;
import com.fos.main.CommandFactory;
import com.fos.main.CommandInvoker;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class UpdateSettingsCommand extends Command {

    private static String commandName = "Update Settings";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config ogConfig) {
        List<Command> commandList = new ArrayList<>();
        commandList.add(new ChefMenuCommand());
        commandList.add(new BartenderMenuCommand());
        commandList.add(new FoodMenuCommand());
        commandList.add(new DrinkMenuCommand());
        
        CommandFactory commandFactory = new CommandFactory(commandList);
        CommandInvoker invoker = new CommandInvoker();
        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());
        List<String> additionalCommands = List.of("Save Changes and Go Back", "Go Back");

        Config config = Config.loadConfig();

        while (true) {
            Utils.clearConsole();
            int choice = Utils.printMenu(scanner, commands, additionalCommands);
            Utils.clearConsole();

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command, scanner, kitchen, config);
            } else if (choice == commands.size() + 1) {
                config.saveConfig();
                return;
            }
            else if (choice == commands.size() + 2) {
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
