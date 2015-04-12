package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Instruction view showing the production effect of an employee.
 * Created by hrvoje on 11.04.15..
 */
public class EmployeeInstructionView extends InstructionView {

    /**
     * Image of the consumed subject.
     */
    public final ImageView primaryImage;

    /**
     * Image of the produced subject.
     */
    public final ImageView finalImage;

    /**
     * Image of the employee.
     */
    public final ImageView employeeImage;

    public EmployeeInstructionView(Context context) {
        this(context, null);
    }

    public EmployeeInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int tenth = (metrics.widthPixels - 64) / 10;
        int imageSize = 3 * tenth;
        int margin = tenth / 2;

        // Preparing image matrix
        Matrix matrix = new Matrix();
        float scaleFactor = imageSize * 3 / (4 * metrics.scaledDensity * 256);
        matrix.postScale(scaleFactor, scaleFactor);
        float offsets = imageSize / 8F;
        matrix.postTranslate(offsets, offsets);

        // Initialize and position the primary image
        this.primaryImage = new ImageView(context);
        LayoutParams rlp = new LayoutParams(imageSize, imageSize);
        rlp.addRule(ALIGN_PARENT_TOP);
        rlp.addRule(ALIGN_PARENT_LEFT);
        rlp.topMargin = margin;
        rlp.leftMargin = margin;
        this.primaryImage.setLayoutParams(rlp);
        //set border background
        this.primaryImage.setBackgroundResource(R.drawable.border);
        this.primaryImage.setImageMatrix(matrix);
        this.primaryImage.setScaleType(ImageView.ScaleType.MATRIX);
        this.addView(this.primaryImage);

        // Initialize and position the final image
        this.finalImage = new ImageView(context);
        rlp = new LayoutParams(imageSize, imageSize);
        rlp.addRule(ALIGN_PARENT_TOP);
        rlp.addRule(ALIGN_PARENT_RIGHT);
        rlp.topMargin = margin;
        rlp.rightMargin = margin;
        this.finalImage.setLayoutParams(rlp);
        this.finalImage.setBackgroundResource(R.drawable.border);
        this.finalImage.setImageMatrix(matrix);
        this.finalImage.setScaleType(ImageView.ScaleType.MATRIX);
        this.addView(this.finalImage);

        // Initialize and position the arrow image
        int arrowSize = 2 * tenth;
        ImageView arrowImage = new ImageView(context);
        rlp = new LayoutParams(arrowSize, arrowSize);
        rlp.addRule(ALIGN_PARENT_TOP);
        rlp.addRule(CENTER_HORIZONTAL);
        rlp.topMargin = imageSize / 2 + margin - arrowSize / 2;
        rlp.bottomMargin = imageSize / 2 + margin - arrowSize / 2;
        arrowImage.setLayoutParams(rlp);
        arrowImage.setId(R.id.previous);
        //set arrow image
        arrowImage.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
        this.addView(arrowImage);

        // Initialize and position the employee image
        this.employeeImage = new ImageView(context);
        rlp = new LayoutParams(imageSize, imageSize);
        rlp.addRule(BELOW, R.id.previous);
        rlp.addRule(CENTER_HORIZONTAL);
        this.employeeImage.setLayoutParams(rlp);
        this.employeeImage.setId(R.id.next);
        this.employeeImage.setBackgroundResource(R.drawable.border);
        this.employeeImage.setImageMatrix(matrix);
        this.employeeImage.setScaleType(ImageView.ScaleType.MATRIX);
        this.addView(this.employeeImage);

        // Position the instruction text
        rlp = (LayoutParams) this.textView.getLayoutParams();
        rlp.addRule(BELOW, R.id.next);
    }

    /**
     * Set image of the consumed subject.
     *
     * @param drawable The new image of the consumed subject.
     */
    public void setPrimaryImage(Drawable drawable) {
        this.primaryImage.setImageDrawable(drawable);
    }

    /**
     * Set image of the produced subject.
     *
     * @param drawable The new image of the produced subject.
     */
    public void setFinalImage(Drawable drawable) {
        this.finalImage.setImageDrawable(drawable);
    }

    /**
     * Set image of the employee.
     *
     * @param drawable The new image of the employee.
     */
    public void setEmployeeImage(Drawable drawable) {
        this.employeeImage.setImageDrawable(drawable);
    }
}