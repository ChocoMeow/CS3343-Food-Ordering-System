package src.factories;

import src.commands.Command;
import src.commands.ConcreteCommandA;
import src.commands.ConcreteCommandB;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<Integer, Command> commandMap = new HashMap<>();

    public CommandFactory() {
        commandMap.put(1, new ConcreteCommandA());
        commandMap.put(2, new ConcreteCommandB());
    }

    public Command getCommand(int commandNumber) {
        return commandMap.get(commandNumber);
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }
}