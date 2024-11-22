package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;
import com.fos.worker.Chef;

public class UpdateChefCommand extends Command {

    private static String commandName = "Update Chef";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();

        int choice = Utils.createSelectionForm(
            scanner,
            "Chef Name",
            "Enter the chef number to update",
            config.getChefs().stream().map(chef -> chef.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getChefs().size() + 1) {
            return;
        }
        
        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter new name for chef:", "String", true));

        String name = (String) formReults.get("name");

        boolean chefExists = config.getChefs().stream()
            .anyMatch(chef -> chef.getName().equalsIgnoreCase(name));

        if (!chefExists) {
            config.getChefs().get(choice - 1).setName(name);
            System.out.println(Utils.addColor("Chef name updated successfully.", Utils.GREEN));
        } else {
            System.out.println(Utils.addColor("Chef name must be unique. Please try again.", Utils.RED));
        }
    }
    
    @Override
    public String getCommandName() {
        return commandName;
    }

}
