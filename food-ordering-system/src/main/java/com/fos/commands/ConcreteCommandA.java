package com.fos.commands;

public class ConcreteCommandA implements Command {
    @Override
    public void execute() {
        System.out.println("Executing Command A");
    }

    @Override
    public void undo() {
        System.out.println("Undoing Command A");
    }
}