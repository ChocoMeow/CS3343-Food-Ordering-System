package com.fos.commands;

public class ViewQueue extends Command {

    public ViewQueue() {
        super("View Queue", "View Queue");
    }

    @Override
    public void execute() {
        System.out.println("Executing Command A");
    }
}