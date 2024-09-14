package com.fos.main;

import com.fos.factories.CommandFactory;
import com.fos.invokers.CommandInvoker;
import com.fos.commands.Command;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvoker();
        Scanner scanner = new Scanner(System.in);

        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());

        while (true) {
            System.out.println("Menu:");
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("%d. Execute %s%n", (i + 1), commands.get(i).getClass().getSimpleName());
            }
            System.out.println((commands.size() + 1) + ". Undo Last Command");
            System.out.println((commands.size() + 2) + ". Redo Last Command");
            System.out.println((commands.size() + 3) + ". Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command);
            } else if (choice == commands.size() + 1) {
                invoker.undo();
            } else if (choice == commands.size() + 2) {
                invoker.redo();
            } else if (choice == commands.size() + 3) {
                System.out.println("Exiting...");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}