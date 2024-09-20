package com.fos.commands;

public class UpdateConfig extends Command {

    public UpdateConfig() {
        super("Update Config", "Update Config");
    }

    @Override
    public void execute() {
        System.out.println("Executing Command A");
    }
}