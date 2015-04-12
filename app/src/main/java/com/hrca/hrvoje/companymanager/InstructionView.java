package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Base class for different Instruction layouts.
 * Created by hrvoje on 11.04.15..
 */
public abstract class InstructionView extends RelativeLayout {

    /**
     * TextView in which the instruction is displayed.
     */
    protected final TextView textView;

    public InstructionView(Context context) {
        this(context, null);
    }

    public InstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize and position the instruction text
        this.textView = new TextView(context);
        LayoutParams rlp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rlp.addRule(ALIGN_PARENT_LEFT);
        this.textView.setLayoutParams(rlp);
        this.textView.setGravity(Gravity.CENTER);
        this.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        this.addView(this.textView);
    }

    /**
     * Set the text of the instruction.
     *
     * @param text Text of the instruction.
     */
    public void setText(String text) {
        this.textView.setText(text);
    }
}
