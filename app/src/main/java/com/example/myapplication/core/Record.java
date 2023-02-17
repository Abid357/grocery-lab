package com.example.myapplication.core;

public class Record {

    /**
     * Product name.
     * For example: Noodles, Washing Powder
     */
    private String productName;

    /**
     * Brand name of the product.
     * For example: Maggie, OMO
     */
    private String brandName;

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
    private float price;

    /**
     * Quantity in units or the number of packages for the product.
     */
    private float quantity;

    /**
     * Applicable only if the product uses a unit of measure (UoM).
     * For example: a 200ml Joocy mango juice would have measure=200
     */
    private float measure;

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
    private float pricePerUom;

    public Record(String productName, String brandName, float price) {
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        quantity = 1;
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

    public float getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getMeasure() {
        return measure;
    }

    public boolean isPackaged() {
        return isPackaged;
    }

    public int getPackageQuantity() {
        return packageQuantity;
    }

    public float getPricePerUom() {
        return pricePerUom;
    }

    public void setPurchase(boolean purchase) {
        isPurchase = purchase;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(float measure) {
        this.measure = measure;
    }

    public void setPackaged(boolean packaged) {
        isPackaged = packaged;
    }

    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public void setPricePerUom(float pricePerUom) {
        this.pricePerUom = pricePerUom;
    }
}
