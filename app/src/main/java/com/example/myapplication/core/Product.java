package com.example.myapplication.core;

public class Product {
    public static final String PRODUCT_TAG = "products";
    public static final int INSERT = 1;
    public static final int EDIT = 2;
    public static final int DELETE = 3;
    private String brandName;
    private String productType;
    private String imagePath;
    private double lastPurchasePrice;
    private String lastPurchaseLocation;

    public Product(String brandName, String productType) {
        this.brandName = brandName;
        this.productType = productType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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
