package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;

public class UpdateBartenderCommand extends Command {

    private static String commandName = "Update Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Utils.clearConsole();

        int choice = Utils.createSelectionForm(
            scanner,
            "Bartender Name",
            "Enter the bartender number to update",
            config.getBartenders().stream().map(bar -> bar.getName()).collect(Collectors.toList()),
            List.of("Go Back")
        );

        if (choice == config.getBartenders().size() + 1) {
            return;
        }

        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter new name for bartender:", "String", true));

        String name = (String) formReults.get("name");

        boolean bartenderExists = config.getBartenders().stream()
            .anyMatch(bartender -> bartender.getName().equalsIgnoreCase(name));

        if (!bartenderExists) {
            config.getBartenders().get(choice - 1).setName(name);
            System.out.println(Utils.addColor("Bartender name updated successfully.", Utils.GREEN));
        } else {
            System.out.println(Utils.addColor("Bartender name must be unique. Please try again.", Utils.RED));
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
