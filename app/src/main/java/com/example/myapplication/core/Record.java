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
    private int quantity;

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

    /**
     * URL of the product from the product store, if any.
     */
    private String link;

    /**
     * Remarks and notes on a record.
     */
    private String note;

    /**
     * Rating of a product out of 5.
     */
    private float rating;

    public Record(String productName,
                  String brandName,
                  double measure,
                  int packageQuantity,
                  int quantity,
                  double price,
                  double pricePerUom,
                  boolean isPurchase,
                  Date purchaseDate,
                  String location,
                  String link,
                  float rating,
                  String note) {
        this.productName = productName;
        this.brandName = brandName;
        this.measure = measure;
        this.packageQuantity = packageQuantity;
        if (packageQuantity != 0)
            isPackaged = true;
        this.quantity = quantity;
        this.price = price;
        this.pricePerUom = pricePerUom;
        this.isPurchase = isPurchase;
        this.purchaseDate = purchaseDate;
        this.location = location;
        this.link = link;
        this.rating = rating;
        this.note = note;
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

    public int getQuantity() {
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

    public void setQuantity(int quantity) {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Record{" +
                "productName='" + productName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", isPurchase=" + isPurchase +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", measure=" + measure +
                ", isPackaged=" + isPackaged +
                ", packageQuantity=" + packageQuantity +
                ", pricePerUom=" + pricePerUom +
                ", purchaseDate=" + purchaseDate +
                ", creationDate=" + creationDate +
                ", link='" + link + '\'' +
                ", note='" + note + '\'' +
                ", rating=" + rating +
                '}';
    }
}
