package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Chef;

public class RemoveChefCommand extends Command {

    private static String commandName = "Remove Chefs";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();

        int choice = Utils.createSelectionForm(
            scanner,
            "Chef Name",
            "Enter the chef number to remove",
            config.getChefs().stream().map(chef -> chef.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getChefs().size() + 1) {
            return;
        }
        config.getChefs().remove(choice - 1);
        System.out.println("Chef removed successfully.");        
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

}
