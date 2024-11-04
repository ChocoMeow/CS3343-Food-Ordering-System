package com.fos.factories;

import com.fos.commands.Command;
import com.fos.commands.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<Integer, Command> commandMap = new HashMap<>();

    public CommandFactory() {
        commandMap.put(1, new CreateOrderCommand());
        commandMap.put(2, new ViewKitchenProcesses());
    }

    public Command getCommand(int commandNumber) {
        return commandMap.get(commandNumber);
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }
}