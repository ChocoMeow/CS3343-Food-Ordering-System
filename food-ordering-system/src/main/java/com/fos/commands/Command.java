package com.fos.commands;

public interface Command {
    void execute();
    void undo();
}