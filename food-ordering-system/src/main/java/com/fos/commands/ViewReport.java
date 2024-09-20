package com.fos.commands;

public class ViewReport extends Command {

    public ViewReport() {
        super("View Report", "View Report");
    }

    @Override
    public void execute() {
        System.out.println("Executing Command A");
    }
}