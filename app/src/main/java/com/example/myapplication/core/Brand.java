package com.example.myapplication.core;

public class Brand {
    public static final String BRAND_TAG = "brands";
    public static final int INSERT = 1;
    public static final int EDIT = 2;
    public static final int DELETE = 3;
    private String name;
    private String product;
    private String imagePath;
    private double lastPurchasePrice;
    private String lastPurchaseLocation;

    public Brand(String name, String product) {
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public String getLastPurchaseLocation() {
        return lastPurchaseLocation;
    }

    public void setLastPurchaseLocation(String lastPurchaseLocation) {
        this.lastPurchaseLocation = lastPurchaseLocation;
    }
}
