package com.fos.member;

public class Customer extends Member {

    public Customer(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Customer";
    }
    
}
