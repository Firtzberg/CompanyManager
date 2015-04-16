package com.hrca.hrvoje.companymanager;


/**
 * Quantity of a resource.
 */
public class Amount {

    /**
     * The number of resources.
     */
    protected double quantity;

    /**
     * The resource of the Amount.
     */
    protected Resource resource;

    /**
     * Create new Amount instance.
     *
     * @param quantity The quantity of the Amount.
     * @param resource The resource of the Amount.
     */
    public Amount(double quantity, Resource resource) {
        this.quantity = quantity;
        this.resource = resource;
    }

    /**
     * Get the quantity.
     *
     * @return The quantity of the Amount.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Get the resource.
     *
     * @return The resource of the Amount.
     */
    public Resource getResource() {
        return resource;
    }
}