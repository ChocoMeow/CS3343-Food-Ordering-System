package com.fos.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Command {
    String name;
    String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public void execute() {}
}