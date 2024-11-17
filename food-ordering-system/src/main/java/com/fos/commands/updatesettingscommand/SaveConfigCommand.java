package com.fos.commands.updatesettingscommand;

import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Config;
import com.fos.main.Kitchen;

public class SaveConfigCommand extends Command {

    private String commandName = "Save Changes";
    @Override
    public void execute(Scanner scanner, Kitchen kitchen, Config config) {
        config.saveConfig();
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
    
}
