package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * This view is a graphical representation of a subject.
 */
public class SubjectView extends RelativeLayout {

    /**
     * The subject this view represents.
     */
    protected Subject subject;

    /**
     * View for displaying the total amount of the subjects.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#number
     */
    protected final TextView quantity;

    /**
     * View for displaying the subject's image.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#image
     */
    protected final ImageView image;

    /**
     * View for displaying the quantity of the subject's costs.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#cost
     * @see com.hrca.hrvoje.companymanager.Subject.ConsumableAmount#quantity
     */
    protected final TextView costQuantity;

    /**
     * View for displaying the image of the subject's costs.
     *
     * @see com.hrca.hrvoje.companymanager.Subject#cost
     * @see com.hrca.hrvoje.companymanager.Subject.ConsumableAmount#subject
     * @see com.hrca.hrvoje.companymanager.Subject#image
     */
    protected final ImageView costImage;

    public SubjectView(Context context) {
        this(context, null);
    }

    public SubjectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubjectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        inflate(context, R.layout.view_subject, this);
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
                attrs, R.styleable.SubjectView, defStyle, 0);

        // Initialize subject
        this.subject = new Subject("Unnamed");
        // Set number
        this.subject.produce(a.getFloat(R.styleable.SubjectView_number, 1000));
        // Set image if available
        if (a.hasValue(R.styleable.SubjectView_image)) {
            this.subject.setImage(a.getDrawable(
                    R.styleable.SubjectView_image));
        }

        // Initialize costs
        Subject cost = new Subject("Unnamed");
        // Set costs image if available
        if (a.hasValue(R.styleable.SubjectView_cost_image)) {
            cost.setImage(a.getDrawable(
                    R.styleable.SubjectView_cost_image));
        }
        // Set costs
        this.subject.setCost(a.getFloat(R.styleable.SubjectView_cost_quantity, 1F), cost);

        a.recycle();
        this.adjust();
    }

    /**
     * Update view using data from subject
     */
    public void adjust() {
        if (this.subject == null)
            return;
        this.image.setImageDrawable(this.subject.getImage());
        this.quantity.setText(this.toShortNumberFormat(this.subject.getNumber()));

        Subject.Amount cost = this.subject.getCost();
        if (cost == null)
            return;
        this.costImage.setImageDrawable(cost.getSubject().getImage());
        this.costQuantity.setText(this.toShortNumberFormat(cost.getQuantity()));
    }

    /**
     * Gets displayed subject.
     *
     * @return Subject which this view displays.
     */
    public Subject getSubject() {
        return this.subject;
    }

    /**
     * Set displayed subject.
     *
     * @param subject Subject which this view displays.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.adjust();
    }

    /**
     * Formats the given number with kilo, mega, giga... and one decimal point if required.
     *
     * @param number The formatted number.
     * @return String representation.
     */
    private String toShortNumberFormat(double number) {
        String[] Letters = new String[]{
                "", "K", "M", "G", "T", "P", "E", "Z", "Y"
        };
        int grade = 0;
        while (number >= 1000) {
            grade++;
            number /= 1000;
        }
        String base;
        if (number == (int) number) {
            base = Integer.toString((int) number);
        } else {
            number = Math.round(number * 10.0) / 10.0;
            base = Double.toString(number);
        }
        if (grade < Letters.length)
            return base + Letters[grade];
        return base + "E" + Integer.toString(grade * 3);
    }
}
