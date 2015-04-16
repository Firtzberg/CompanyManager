package com.hrca.hrvoje.companymanager;

/**
 * Collection of human resources.
 * Created by hrvoje on 15.04.15..
 */
public class HumanResource extends Resource {

    /**
     * The Amount each employee produces in every turn.
     */
    protected ProductionAmount production;

    /**
     * The Amount each employee consumes in every turn.
     */
    protected ConsumableAmount salary;

    /**
     * Create a new human resource instance.
     *
     * @param Name The name of the human resource.
     */
    public HumanResource(String Name) {
        super(Name);
    }

    /**
     * Get the Amount each employee produces in every turn.
     *
     * @return Amount of production.
     * @see com.hrca.hrvoje.companymanager.HumanResource#production
     */
    public Amount getProduction() {
        return this.production;
    }

    /**
     * Get the Amount each employee consumes in every turn.
     *
     * @return Maintenance amount.
     * @see com.hrca.hrvoje.companymanager.HumanResource#salary
     */
    public Amount getSalary() {
        return this.salary;
    }

    /**
     * Set the Amount each employee produces in every turn.
     *
     * @param quantity Number of produced resources.
     * @param resource The produced resource.
     * @see com.hrca.hrvoje.companymanager.HumanResource#production
     */
    public void setProduction(double quantity, Resource resource) {
        this.production = new ProductionAmount(quantity, resource);
    }

    /**
     * Set the salary.
     *
     * @param quantity Number of consumed resources.
     * @param resource The currency (consumed resource).
     * @see com.hrca.hrvoje.companymanager.HumanResource#salary
     */
    public void setSalary(double quantity, Resource resource) {
        this.salary = new ConsumableAmount(quantity, resource);
    }

    /**
     * Pay as many workers as possible.
     *
     * @return Number of unpaid employees
     * @see com.hrca.hrvoje.companymanager.HumanResource#salary
     * @see com.hrca.hrvoje.companymanager.Resource#consume(double)
     */
    public double pay() {
        if (this.salary != null) {
            return this.number - this.salary.consume(this.number);
        }
        return 0.0;
    }

    /**
     * Each employee tries to produce its production.
     * If there are not enough resources for all employees to work, less work will be done.
     *
     * @return The number of employees which did their job.
     * @see com.hrca.hrvoje.companymanager.HumanResource#production
     * @see com.hrca.hrvoje.companymanager.Resource#produce(double)
     */
    public double work() {
        if (this.production != null) {
            return this.production.produce(this.number);
        }
        return this.number;
    }
}
