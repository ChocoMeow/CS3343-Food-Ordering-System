package com.fos.commands;

import java.util.Scanner;

import com.fos.main.Config;
import com.fos.main.Kitchen;

public abstract class Command {
    public abstract void execute(Scanner scanner, Kitchen kitchen, Config config);
    public abstract String getCommandName();
}
