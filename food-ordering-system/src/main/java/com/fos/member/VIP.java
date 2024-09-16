package com.fos.member;

public class VIP extends Member {

    public VIP(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "VIP";
    }
    
}
