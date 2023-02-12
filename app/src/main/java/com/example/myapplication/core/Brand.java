package com.example.myapplication.core;

public class Brand {
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

    public String getProduct() {
        return product;
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
