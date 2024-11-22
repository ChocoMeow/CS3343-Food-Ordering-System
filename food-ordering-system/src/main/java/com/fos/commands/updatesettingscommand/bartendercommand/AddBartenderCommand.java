package com.fos.commands.updatesettingscommand.bartendercommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;
import com.fos.main.Utils;
import com.fos.worker.Bartender;

public class AddBartenderCommand extends Command {

    private static String commandName = "Add Bartender";

    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        Map<String, Object> formReults = new HashMap<>();
        formReults.putAll(Utils.createInputField(scanner, "name", "Enter name for new bartender:", "String", true));

        String name = (String) formReults.get("name");

        boolean bartenderExists = kitchen.getBartenders().stream()
            .anyMatch(bartender -> bartender.getName().equalsIgnoreCase(name));

        if (!bartenderExists) {
            config.getBartenders().add(new Bartender(name));
            System.out.println(Utils.addColor("Bartender added successfully.", Utils.GREEN));
        } else {
            System.out.println(Utils.addColor("Bartender name must be unique. Please try again.", Utils.RED));
        }
        
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
