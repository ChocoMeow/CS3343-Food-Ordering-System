package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.List;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.worker.Bartender;

public class AddBartenderCommand extends Command {

    private static String commandName = "Add Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        System.out.print("Enter name for new bartender: ");
        String newName = scanner.nextLine();

        config.getBartenders().add(new Bartender(newName));
        System.out.println("Bartender added successfully.");

        boolean bartenderExists = kitchen.getBartenders().stream()
            .anyMatch(bartender -> bartender.getName().equalsIgnoreCase(newName));

        if (!bartenderExists) {
            config.getBartenders().add(new Bartender(newName));
            System.out.println("Bartender added successfully.");
        } else {
            System.out.println("Bartender name must be unique. Please try again.");
        }
        
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
