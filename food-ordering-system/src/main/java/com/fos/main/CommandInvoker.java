package com.fos.main;

import java.util.Scanner;

import com.fos.commands.Command;

public class CommandInvoker {
    public void executeCommand(Command command, Scanner scanner, Kitchen kitchen, Config config) {
        if (command != null) {
            command.execute(scanner, kitchen, config);
        }
    }
}