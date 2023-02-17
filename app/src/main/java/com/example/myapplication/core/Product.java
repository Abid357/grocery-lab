package com.example.myapplication.core;

public class Product {
    private String name;
    private String uom;

    private int brandCount;

    public Product(String name, String uom) {
        this.name = name;
        this.uom = uom;
        brandCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getUom() {
        return uom;
    }

    public int getBrandCount() {
        return brandCount;
    }

    public void setBrandCount(int brandCount) {
        this.brandCount = brandCount;
    }
}
