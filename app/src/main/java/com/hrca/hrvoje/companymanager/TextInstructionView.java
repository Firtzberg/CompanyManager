package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Instruction view with text only.
 * Created by hrvoje on 11.04.15..
 */
public class TextInstructionView extends InstructionView {

    public TextInstructionView(Context context) {
        this(context, null);
    }

    public TextInstructionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Position the instruction text
        LayoutParams rlp = (LayoutParams) this.textView.getLayoutParams();
        rlp.addRule(ALIGN_PARENT_TOP);
    }
}
