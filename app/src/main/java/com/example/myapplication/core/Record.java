package com.example.myapplication.core;

import java.util.Date;

public class Record {

    /**
     * Product name.
     * For example: Noodles, Washing Powder
     */
    private final String productName;

    /**
     * Brand name of the product.
     * For example: Maggie, OMO
     */
    private final String brandName;

    /**
     * A record may be a purchase or just for the sake of record-keeping.
     */
    private boolean isPurchase;

    /**
     * Location at which this record was seen or purchased.
     * For example: Safeer Market, Lulu Hypermarket
     */
    private String location;

    /**
     * Price of a single unit or a single packaged unit for the product.
     */
    private final double price;

    /**
     * Quantity in units or the number of packages for the product.
     */
    private double quantity;

    /**
     * Applicable only if the product uses a unit of measure (UoM).
     * For example: a 200ml Joocy mango juice would have measure=200
     */
    private double measure;

    /**
     * Indicates if the product comes in packages.
     * For example: a pack of 5 Maggie noodles
     */
    private boolean isPackaged;

    /**
     * Quantity of the product inside a single package.
     * For example: a pack of 5 Maggie noodles would have packageQuantity=5
     */
    private int packageQuantity;

    /**
     * Price per unit of measure is the goal of the computation for each record.
     * This is what the user would be comparing between every record for a product.
     * pricePerUom = price / measure;
     * pricePerUom = (price / packageQuantity) / measure; if packaged
     */
    private double pricePerUom;

    /**
     * Date of purchase or entry of record.
     */
    private Date purchaseDate;

    /**
     * Date of creation of the record.
     */
    private final Date creationDate;

    public Record(String productName, String brandName, int quantity, double price, double pricePerUom) {
        this.productName = productName;
        this.brandName = brandName;
        this.quantity = quantity;
        this.price = price;
        this.pricePerUom = pricePerUom;
        creationDate = new Date();
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public boolean isPurchase() {
        return isPurchase;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getMeasure() {
        return measure;
    }

    public boolean isPackaged() {
        return isPackaged;
    }

    public int getPackageQuantity() {
        return packageQuantity;
    }

    public double getPricePerUom() {
        return pricePerUom;
    }

    public void setPurchase(boolean purchase) {
        isPurchase = purchase;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public void setPackaged(boolean packaged) {
        isPackaged = packaged;
    }

    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public void setPricePerUom(double pricePerUom) {
        this.pricePerUom = pricePerUom;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
