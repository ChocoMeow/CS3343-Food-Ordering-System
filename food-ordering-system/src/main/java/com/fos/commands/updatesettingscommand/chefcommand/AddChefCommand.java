package com.fos.commands.updatesettingscommand.chefcommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Chef;

public class AddChefCommand extends Command {

    private static String commandName = "Add Chefs";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter name for new chef:", "String", true));

        String name = (String) formReults.get("name");

        boolean chefExists = config.getChefs().stream()
            .anyMatch(chef -> chef.getName().equalsIgnoreCase(name));

        if (!chefExists) {
            config.getChefs().add(new Chef(name));
            System.out.println(Utils.addColor("Chef added successfully.", Utils.GREEN));
        } else {
            System.out.println(Utils.addColor("Chef name must be unique. Please try again.", Utils.RED));
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
