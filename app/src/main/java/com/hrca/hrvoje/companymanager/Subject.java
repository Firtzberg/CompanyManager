package com.hrca.hrvoje.companymanager;

import android.graphics.drawable.Drawable;

/**
 * Collection of resources or people.
 */
public class Subject {

    /**
     * Quantity of a subject.
     */
    public class Amount {

        /**
         * The number of subjects.
         */
        protected double quantity;

        /**
         * The subject of the Amount.
         */
        protected Subject subject;

        /**
         * Create new Amount instance.
         *
         * @param quantity The quantity of the Amount.
         * @param subject  The subject of the Amount.
         */
        public Amount(double quantity, Subject subject) {
            this.quantity = quantity;
            this.subject = subject;
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
         * Get the subject.
         *
         * @return The subject of the Amount.
         */
        public Subject getSubject() {
            return subject;
        }
    }

    /**
     * An Amount that can be consumed.
     *
     * @see com.hrca.hrvoje.companymanager.Subject.ConsumableAmount#consume(double)
     */
    private class ConsumableAmount extends Amount {

        /**
         * Create a new instance of the ConsumableAmount.
         *
         * @param quantity The quantity of consumption.
         * @param subject  The subject of consumption.
         */
        public ConsumableAmount(double quantity, Subject subject) {
            super(quantity, subject);
        }

        /**
         * Reduce the subject by the quantity several times.
         *
         * @param multiplier The number of reductions.
         * @see com.hrca.hrvoje.companymanager.Subject#consume(double)
         */
        public void consume(double multiplier) {
            this.subject.consume(multiplier * this.quantity);
        }
    }

    /**
     * An Amount that can be produced.
     *
     * @see com.hrca.hrvoje.companymanager.Subject.ProductionAmount#produce(double)
     */
    private class ProductionAmount extends Amount {

        /**
         * Create a new instance of the ProductionAmount.
         *
         * @param quantity The number of new subjects in each production.
         * @param subject  The produced Subject.
         */
        public ProductionAmount(double quantity, Subject subject) {
            super(quantity, subject);
        }

        /**
         * Produce the Amount several times.
         *
         * @param multiplier The number of productions.
         * @see com.hrca.hrvoje.companymanager.Subject#produce(double)
         */
        public void produce(double multiplier) {
            this.subject.produce(multiplier * this.quantity);
        }
    }

    /**
     * Name of the subject.
     */
    private String name;

    /**
     * Total number of subjects.
     */
    private double number;

    /**
     * The production cost of a new subject.
     */
    private ConsumableAmount cost;

    /**
     * The Amount each subject produces in every turn.
     */
    private ProductionAmount production;

    /**
     * The Amount each subject consumes in every turn.
     */
    private ConsumableAmount maintenance;

    /**
     * The image fo the subject.
     */
    private Drawable image;

    /**
     * Create a new subject instance.
     *
     * @param Name The name of the subject.
     */
    public Subject(String Name) {
        this.name = Name;
        this.number = 0;
    }

    /**
     * Get total number of subjects.
     *
     * @return Total number.
     * @see com.hrca.hrvoje.companymanager.Subject#number
     */
    public double getNumber() {
        return this.number;
    }

    /**
     * Get subject's name.
     *
     * @return Subject's name.
     * @see com.hrca.hrvoje.companymanager.Subject#name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get subject's image.
     *
     * @return Image of the subject.
     * @see com.hrca.hrvoje.companymanager.Subject#image
     */
    public Drawable getImage() {
        return this.image;
    }

    /**
     * Get production costs of the subject.
     *
     * @return Amount of costs.
     * @see com.hrca.hrvoje.companymanager.Subject#cost
     */
    public Amount getCost() {
        return this.cost;
    }

    /**
     * Get the Amount each subject produces in every turn.
     *
     * @return Amount of production.
     * @see com.hrca.hrvoje.companymanager.Subject#production
     */
    public Amount getProduction() {
        return this.production;
    }

    /**
     * Get the Amount each subject consumes in every turn.
     *
     * @return Maintenance amount.
     * @see com.hrca.hrvoje.companymanager.Subject#maintenance
     */
    public Amount getMaintenance() {
        return this.maintenance;
    }

    /**
     * Set the image.
     *
     * @param image The new image.
     * @return This.
     * @see com.hrca.hrvoje.companymanager.Subject#image
     */
    public Subject setImage(Drawable image) {
        this.image = image;
        return this;
    }

    /**
     * Set production costs for a new subject.
     *
     * @param quantity Number of consumed subjects.
     * @param subject  The Subject consumed during production.
     * @return This.
     * @see com.hrca.hrvoje.companymanager.Subject#cost
     */
    public Subject setCost(double quantity, Subject subject) {
        this.cost = new ConsumableAmount(quantity, subject);
        return this;
    }

    /**
     * Set the Amount each subject produces in every turn.
     *
     * @param quantity Number of produced subjects.
     * @param subject  The produced subject.
     * @return This.
     * @see com.hrca.hrvoje.companymanager.Subject#production
     */
    public Subject setProduction(double quantity, Subject subject) {
        this.production = new ProductionAmount(quantity, subject);
        return this;
    }

    /**
     * Set the Amount each subject consumes in every turn.
     *
     * @param quantity Number of consumed Subjects.
     * @param subject  The Subject consumed during production.
     * @return This.
     * @see com.hrca.hrvoje.companymanager.Subject#maintenance
     */
    public Subject setMaintenance(double quantity, Subject subject) {
        this.maintenance = new ConsumableAmount(quantity, subject);
        return this;
    }

    /**
     * Reduce total number of subjects.
     *
     * @param quantity Quantity by which the number is reduced.
     */
    private void consume(double quantity) {
        this.number -= quantity;
    }

    /**
     * Produce several new subjects. For each new subject the production costs have to be paid.
     *
     * @param quantity Number of new Subjects
     * @see com.hrca.hrvoje.companymanager.Subject#cost
     */
    protected void produce(double quantity) {
        if (this.cost != null) {
            this.cost.consume(quantity);
        }
        this.number += quantity;
    }

    /**
     * Produce one new Subject. The production costs are paid.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#produce(double)
     */
    public void produce() {
        this.produce(1);
    }

    /**
     * Consume the maintenance costs and produce the production for each subject.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#maintenance
     * @see com.hrca.hrvoje.companymanager.Subject#production
     */
    public void nextTurn() {
        if (this.maintenance != null) {
            this.maintenance.consume(this.number);
        }
        if (this.production != null) {
            this.production.produce(this.number);
        }
    }
}
