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
}
