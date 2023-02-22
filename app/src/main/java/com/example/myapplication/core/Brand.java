package com.example.myapplication.core;

import java.util.Date;

public class Brand {
    private String name;
    private String productName;
    private String imagePath;
    private double lastPurchasePrice;
    private String lastPurchaseLocation;

    private int recordCount;

    private Date lastPurchaseDate;

    public Brand(String name, String productName) {
        this.name = name;
        this.productName = productName;
    }

    public String getName() {
        return name;
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

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
