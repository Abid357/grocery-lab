package com.example.myapplication.core;

public class Brand {
    private String brandName;
    private String productName;
    private String imagePath;
    private double lastPurchasePrice;
    private String lastPurchaseLocation;

    public Brand(String brandName, String productName) {
        this.brandName = brandName;
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
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
