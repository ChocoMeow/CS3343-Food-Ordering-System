package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Chef;

public class AddChefCommand extends Command {

    private static String commandName = "Add Chefs";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        System.out.print("Enter name for new chef: ");
        String newName = scanner.nextLine();

        config.getChefs().add(new Chef(newName));
        System.out.println("Chef added successfully.");

        // if (isNameUnique(newName, config.getChefs())) {
        //     config.getChefs().add(new Chef(newName));
        //     System.out.println("Chef added successfully.");
        // } else {
        //     System.out.println("Chef name must be unique. Please try again.");
        // }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
