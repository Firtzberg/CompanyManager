package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * This view is a graphical representation of a resource.
 */
public class ResourceView extends RelativeLayout {

    /**
     * The resource this view represents.
     */
    protected Resource resource;

    /**
     * View for displaying the total amount of the resources.
     *
     * @see Resource#number
     */
    protected final TextView quantity;

    /**
     * View for displaying the resource's image.
     *
     * @see Resource#image
     */
    protected final ImageView image;

    /**
     * View for displaying the quantity of the resource's costs.
     *
     * @see Resource#cost
     * @see Resource.ConsumableAmount#quantity
     */
    protected final TextView costQuantity;

    /**
     * View for displaying the image of the resource's costs.
     *
     * @see Resource#cost
     * @see Resource.ConsumableAmount#resource
     * @see Resource#image
     */
    protected final ImageView costImage;

    public ResourceView(Context context) {
        this(context, null);
    }

    public ResourceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResourceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        inflate(context, R.layout.view_resource, this);
        this.image = (ImageView) this.findViewById(R.id.image);
        this.quantity = (TextView) this.findViewById(R.id.quantity);
        this.costImage = (ImageView) this.findViewById(R.id.cost_image);
        this.costQuantity = (TextView) this.findViewById(R.id.cost_quantity);

        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if (attrs == null)
            return;

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ResourceView, defStyle, 0);

        // Initialize resource
        this.resource = new Resource("Unnamed");
        // Set number
        this.resource.produce(a.getFloat(R.styleable.ResourceView_number, 1000));
        // Set image if available
        if (a.hasValue(R.styleable.ResourceView_image)) {
            this.resource.setImage(a.getDrawable(
                    R.styleable.ResourceView_image));
        }

        // Initialize costs
        Resource cost = new Resource("Unnamed");
        // Set costs image if available
        if (a.hasValue(R.styleable.ResourceView_cost_image)) {
            cost.setImage(a.getDrawable(
                    R.styleable.ResourceView_cost_image));
        }
        // Set costs
        this.resource.setCost(a.getFloat(R.styleable.ResourceView_cost_quantity, 1F), cost);

        a.recycle();
        this.adjust();
    }

    /**
     * Update view using data from resource
     */
    public void adjust() {
        if (this.resource == null)
            return;
        this.image.setImageDrawable(this.resource.getImage());
        this.quantity.setText(this.toShortNumberFormat(this.resource.getNumber()));

        Amount cost = this.resource.getCost();
        if (cost == null)
            return;
        this.costImage.setImageDrawable(cost.getResource().getImage());
        this.costQuantity.setText(this.toShortNumberFormat(cost.getQuantity()));
    }

    /**
     * Gets displayed resource.
     *
     * @return Resource which this view displays.
     */
    public Resource getResource() {
        return this.resource;
    }

    /**
     * Set displayed resource.
     *
     * @param resource Resource which this view displays.
     */
    public void setResource(Resource resource) {
        this.resource = resource;
        this.adjust();
    }

    /**
     * Formats the given number with kilo, mega, giga... and one decimal point if required.
     *
     * @param number The formatted number.
     * @return String representation.
     */
    public static String toShortNumberFormat(double number) {
        String[] Letters = new String[]{
                "", "K", "M", "G", "T", "P", "E", "Z", "Y"
        };
        int grade = 0;
        while (number >= 1000) {
            grade++;
            number /= 1000;
        }
        String base;
        if (grade == 0) {
            base = Integer.toString((int) number);
        } else {
            if (number >= 10.0) {
                number = Math.round(number * 10.0) / 10.0;
            } else {
                number = Math.round(number * 100.0) / 100.0;
            }
            base = Double.toString(number);
        }
        if (grade < Letters.length)
            return base + Letters[grade];
        return base + "E" + Integer.toString(grade * 3);
    }
}
