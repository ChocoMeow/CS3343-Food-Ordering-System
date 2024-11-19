package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.CommandFactory;
import com.fos.main.CommandInvoker;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class BartenderMenuCommand extends Command {

    private static String commandName = "Bartenders Menu";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        List<Command> commandList = new ArrayList<>();
        commandList.add(new AddBartenderCommand());
        commandList.add(new RemoveBartenderCommand());
        commandList.add(new UpdateBartenderCommand());

        CommandFactory commandFactory = new CommandFactory(commandList);
        CommandInvoker invoker = new CommandInvoker();
        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());
        List<String> additionalCommands = List.of("Go Back");

        while (true) {
            int choice = Utils.printMenu(scanner, commands, additionalCommands);
            Utils.clearConsole();
            
            if (choice == commands.size() + 1) {
                return;
            }

            Command command = commands.get(choice - 1);
            invoker.executeCommand(command, scanner, kitchen, config);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
