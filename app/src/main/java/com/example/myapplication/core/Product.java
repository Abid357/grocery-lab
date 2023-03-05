package com.example.myapplication.core;

public class Product {
    private String name;
    private String uom;

    public Product(String name, String uom) {
        this.name = name;
        this.uom = uom;
    }

    public String getName() {
        return name;
    }

    public String getUom() {
        return uom;
    }
}
