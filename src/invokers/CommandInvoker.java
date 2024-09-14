package src.invokers;

import src.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CommandInvoker {
    private List<Command> commandHistory = new ArrayList<>();
    private Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command) {
        if (command != null) {
            command.execute();
            commandHistory.add(command);
            redoStack.clear(); // Clear redo stack on new command
        }
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command command = commandHistory.remove(commandHistory.size() - 1);
            command.undo();
            redoStack.push(command);
        } else {
            System.out.println("No commands to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            commandHistory.add(command);
        } else {
            System.out.println("No commands to redo.");
        }
    }
}