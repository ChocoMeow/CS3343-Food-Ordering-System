package com.fos.main;

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
        commandMap.put(3, new CreateReport());
    }

    public Command getCommand(int commandNumber) {
        return commandMap.get(commandNumber);
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }
}