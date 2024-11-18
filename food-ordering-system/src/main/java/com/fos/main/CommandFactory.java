package com.fos.main;

import com.fos.commands.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {
    private Map<Integer, Command> commandMap = new HashMap<>();

    public CommandFactory(List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            commandMap.put(i + 1, commands.get(i)); // Keys start from 1
        }
    }

    public Command getCommand(int commandNumber) {
        return commandMap.get(commandNumber);
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }
}