package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class RemoveBartenderCommand extends Command {

    private static String commandName = "Remove Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();

        int choice = Utils.createSelectionForm(
            scanner,
            "Bartender Name",
            "Enter the bartender number to remove",
            config.getBartenders().stream().map(bar -> bar.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getBartenders().size() + 1) {
            return;
        }

        config.getBartenders().remove(choice - 1);
        System.out.println("Bartender removed successfully.");
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
