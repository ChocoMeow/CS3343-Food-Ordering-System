package com.fos.commands;

public class CreateOrder extends Command {

    public CreateOrder() {
        super("Create Order", "Create Order");
    }

    @Override
    public void execute() {
        System.out.println("Executing Command A");
    }
}