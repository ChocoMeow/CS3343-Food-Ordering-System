package com.fos.main;

import com.fos.factories.CommandFactory;
import com.fos.invokers.CommandInvoker;
import com.fos.commands.Command;

import com.fos.Tasks.TaskPool;
import com.fos.Tasks.Task;

import com.fos.foods.Food;
import com.fos.drinks.Drink;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static Task foodQueueTask = TaskPool.getTask("main");
    public static ArrayList<Food> foodQueue = new ArrayList<Food>();
    public static ArrayList<Drink> drinkQueue = new ArrayList<Drink>();

    public static void main(String[] args) {
        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvoker();
        Scanner scanner = new Scanner(System.in);

        List<Command> commands = new ArrayList<>(commandFactory.getAllCommands());
        
        Config config = Config.loadConfig();

        while (true) {
            System.out.println("Menu:");
            for (int i = 0; i < commands.size(); i++) {
                System.out.printf("%d. %s%n", (i + 1), commands.get(i).getName());
            }
            System.out.println((commands.size() + 1) + ". Undo Last Command");
            System.out.println((commands.size() + 2) + ". Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= commands.size()) {
                Command command = commands.get(choice - 1);
                invoker.executeCommand(command);
            } else if (choice == commands.size() + 1) {
                invoker.redo();
            } else if (choice == commands.size() + 2) {
                System.out.println("Exiting...");
                scanner.close();
                TaskPool.stopAllTasks();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}