package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Chef;

public class UpdateChefCommand extends Command {

    private static String commandName = "Update Chef";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("--- %s ---%n", commandName);
        List<Chef> chefs = kitchen.getChefs();
        for (int i = 0; i < chefs.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), chefs.get(i).getName());
        }

        System.out.print("\nEnter the chef number to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= config.getChefs().size()) {
            System.out.print("Enter new name for chef: ");
            String newName = scanner.nextLine();

            config.getChefs().get(choice - 1).setName(newName);
            System.out.println("Chef name updated successfully.");

            // if (isNameUnique(newName, config.getChefs())) {
            //     config.getChefs().get(choice - 1).setName(newName);
            //     System.out.println("Chef name updated successfully.");
            // } else {
            //     System.out.println("Chef name must be unique. Please try again.");
            // }
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    @Override
    public String getCommandName() {
        return commandName;
    }

}
