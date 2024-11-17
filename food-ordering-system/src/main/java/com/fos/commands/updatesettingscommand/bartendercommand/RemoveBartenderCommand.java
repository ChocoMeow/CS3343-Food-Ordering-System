package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;

public class RemoveBartenderCommand extends Command {

    private static String commandName = "Remove Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();
        System.out.printf("--- %s ---%n", commandName);
        List<Bartender> bartenders = kitchen.getBartenders();
        for (int i = 0; i < bartenders.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), bartenders.get(i).getName());
        }

        System.out.print("\nEnter the bartender number to remove: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice > 0 && choice <= config.getBartenders().size()) {
            config.getBartenders().remove(choice - 1);
            System.out.println("Bartender removed successfully.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
