package src.commands;

public class ConcreteCommandB implements Command {
    @Override
    public void execute() {
        System.out.println("Executing Command B");
    }

    @Override
    public void undo() {
        System.out.println("Undoing Command B");
    }
}