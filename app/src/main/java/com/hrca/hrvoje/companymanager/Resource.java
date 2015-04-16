package com.hrca.hrvoje.companymanager;

import android.graphics.drawable.Drawable;

/**
 * Collection of resources.
 */
public class Resource {

    /**
     * An Amount that can be consumed.
     *
     * @see Resource.ConsumableAmount#consume(double)
     */
    protected class ConsumableAmount extends Amount {

        /**
         * Create a new instance of the ConsumableAmount.
         *
         * @param quantity The quantity of consumption.
         * @param resource The resource of consumption.
         */
        public ConsumableAmount(double quantity, Resource resource) {
            super(quantity, resource);
        }

        /**
         * Reduce the resource by the quantity several times.
         *
         * @param multiplier The number of wanted reductions.
         * @return The number of realized reductions.
         * @see Resource#consume(double)
         */
        public double consume(double multiplier) {
            if (this.quantity == 0.0)
                return multiplier;
            return this.resource.consume(multiplier * this.quantity) / this.quantity;
        }

        /**
         * Check if specified number of consumptions is affordable.
         *
         * @param multiplier The number of wanted consumptions.
         * @return True is specified number is affordable.
         * @see com.hrca.hrvoje.companymanager.Resource#cost
         */
        public boolean isAffordable(double multiplier) {
            return this.resource.number >= multiplier * this.quantity;
        }
    }

    /**
     * An Amount that can be produced.
     *
     * @see Resource.ProductionAmount#produce(double)
     */
    protected class ProductionAmount extends Amount {

        /**
         * Create a new instance of the ProductionAmount.
         *
         * @param quantity The number of new resources in each production.
         * @param resource The produced resource.
         */
        public ProductionAmount(double quantity, Resource resource) {
            super(quantity, resource);
        }

        /**
         * Produce the Amount several times.
         *
         * @param multiplier The number of wanted productions.
         * @return The number of realized productions.
         * @see Resource#produce(double)
         */
        public double produce(double multiplier) {
            if (this.quantity == 0.0)
                return multiplier;
            return this.resource.produce(multiplier * this.quantity) / this.quantity;
        }
    }

    /**
     * Name of the resource.
     */
    protected String name;

    /**
     * Total number of resources.
     */
    protected double number;

    /**
     * The production cost of a new resource.
     */
    protected ConsumableAmount cost;

    /**
     * The image fo the resource.
     */
    protected Drawable image;

    /**
     * Create a new resource instance.
     *
     * @param Name The name of the resource.
     */
    public Resource(String Name) {
        this.name = Name;
        this.number = 0;
    }

    /**
     * Get total number of resources.
     *
     * @return Total number.
     * @see Resource#number
     */
    public double getNumber() {
        return this.number;
    }

    /**
     * Get resource's name.
     *
     * @return resource's name.
     * @see Resource#name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get resource's image.
     *
     * @return Image of the resource.
     * @see Resource#image
     */
    public Drawable getImage() {
        return this.image;
    }

    /**
     * Get production costs of the resource.
     *
     * @return Amount of costs.
     * @see Resource#cost
     */
    public Amount getCost() {
        return this.cost;
    }

    /**
     * Set the image.
     *
     * @param image The new image.
     * @see Resource#image
     */
    public void setImage(Drawable image) {
        this.image = image;
    }

    /**
     * Set production costs of a resource.
     *
     * @param quantity Number of consumed resources.
     * @param resource The resource consumed during production.
     * @see Resource#cost
     */
    public void setCost(double quantity, Resource resource) {
        this.cost = new ConsumableAmount(quantity, resource);
    }

    /**
     * Reduce total number of resources.
     * If there are not enough resources, their number will be set to zero.
     *
     * @param quantity The wanted quantity by which the number is reduced.
     * @return The quantity by which the number is reduced.
     */
    public double consume(double quantity) {
        if (this.number >= quantity) {
            this.number -= quantity;
            return quantity;
        }
        quantity = this.number;
        this.number = 0;
        return quantity;
    }

    /**
     * Check if specified quantity is affordable.
     *
     * @param quantity The number of wanted new resources.
     * @return True is specified quantity is affordable.
     * @see com.hrca.hrvoje.companymanager.Resource#cost
     */
    public boolean isAffordable(double quantity) {
        return this.cost.isAffordable(quantity);
    }

    /**
     * Produce several new resources. For each new resource the production costs have to be paid.
     * If there are not enough resources available, less resources will be produced.
     *
     * @param quantity The number of wanted new resources.
     * @return The number of produced new resources.
     * @see Resource#cost
     */
    public double produce(double quantity) {
        if (this.cost != null) {
            quantity = this.cost.consume(quantity);
        }
        this.number += quantity;
        return quantity;
    }

    /**
     * Produce one new resource. The production costs are paid.
     *
     * @return The number of produced new resources.
     * @see Resource#produce(double)
     */
    public double produce() {
        return this.produce(1);
    }
}
