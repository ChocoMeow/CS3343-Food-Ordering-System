package com.fos.member;

public abstract class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract String getType();
}
