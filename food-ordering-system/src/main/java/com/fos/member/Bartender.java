package com.fos.member;

public class Bartender extends Member {

    public Bartender(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Bartender";
    }

}
