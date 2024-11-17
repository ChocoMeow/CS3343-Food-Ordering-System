package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;

public class UpdateBartenderCommand extends Command {

    private static String commandName = "Update Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("--- %s ---%n", commandName);
        List<Bartender> bartenders = kitchen.getBartenders();
        for (int i = 0; i < bartenders.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), bartenders.get(i).getName());
        }

        System.out.print("\nEnter the bartender number to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= config.getBartenders().size()) {
            System.out.print("Enter new name for bartender: ");
            String newName = scanner.nextLine();

            config.getBartenders().get(choice - 1).setName(newName);
            System.out.println("Bartender name updated successfully.");
                
            // if (isNameUnique(newName, config.getBartenders())) {
            //     config.getBartenders().get(choice - 1).setName(newName);
            //     System.out.println("Bartender name updated successfully.");
            // } else {
            //     System.out.println("Bartender name must be unique. Please try again.");
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
