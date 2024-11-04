package com.fos.invokers;

import java.util.Scanner;

import com.fos.commands.Command;
import com.fos.main.Kitchen;

public class CommandInvoker {
    public void executeCommand(Command command, Scanner scanner, Kitchen kitchen) {
        if (command != null) {
            command.execute(scanner, kitchen);
        }
    }
}