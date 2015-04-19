package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Class for management of instructions
 * Created by hrvoje on 11.04.15..
 */
public class InstructionAdapter {

    // Application context
    private Context context;

    // Possible instruction layouts
    private TextInstructionView textInstructionView;
    private ProductionInstructionView productionInstructionView;
    private EmployeeInstructionView employeeInstructionView;

    // Drawables for each subject
    private Drawable moneyDrawable;
    private Drawable materialDrawable;
    private Drawable productDrawable;
    private Drawable purchaserDrawable;
    private Drawable producerDrawable;
    private Drawable salespersonDrawable;
    private Drawable managerDrawable;

    /**
     * Index of currently viewed instruction.
     */
    public int position;

    /**
     * Instruction texts.
     */
    private static final String[] INSTRUCTIONS = new String[]{
            "The game is turn based.",
            "Each turn you can perform one action - buy, produce, sell or hire.",
            "The game is over when your money is below 0.",
            "You earn money by selling products.",
            "You loose money by buying, salary and hiring.",
            "Raw material costs $1.",
            "You need 1 raw material to produce 1 product.",
            "You sell 1 product for $5.",
            "You have to pay money when hiring a new employee.",
            "You pay every employee $1 each turn.",
            "You can't fire anybody.",
            "A purchaser buys 1 raw material each turn.",
            "A producer produces 1 product each turn.",
            "A salesperson sells 1 product each turn.",
            "A manager hires a new employee each turn. Always of the same profession.",
            "Managers can hire other managers.",
            "Have as many employees as possible after " + GameActivity.maxTurns + " turns."
    };

    /**
     * Create a new instance of Instruction adapter with hardcoded instructions
     *
     * @param context Application context
     */
    public InstructionAdapter(Context context) {
        this.context = context;
        this.position = 0;

        // Loading all drawables
        this.moneyDrawable = context.getResources().getDrawable(R.drawable.money);
        this.materialDrawable = context.getResources().getDrawable(R.drawable.material);
        this.productDrawable = context.getResources().getDrawable(R.drawable.product);
        this.purchaserDrawable = context.getResources().getDrawable(R.drawable.purchaser);
        this.producerDrawable = context.getResources().getDrawable(R.drawable.producer);
        this.salespersonDrawable = context.getResources().getDrawable(R.drawable.salesperson);
        this.managerDrawable = context.getResources().getDrawable(R.drawable.manager);
    }

    /**
     * Reset the position at the first instruction and return it.
     *
     * @return The first instruction view.
     */
    public InstructionView first() {
        this.position = 0;
        return this.getView();
    }

    /**
     * Get previous instruction view if possible.
     *
     * @return Previous instruction view or null if position is outside of the array.
     */
    public InstructionView previous() {
        this.position--;
        if (this.position < 0)
            return null;
        return this.getView();
    }

    /**
     * Get next instruction view if possible.
     *
     * @return Next instruction view or null if the position is out of the array.
     */
    public InstructionView next() {
        this.position++;
        if (position >= INSTRUCTIONS.length)
            return null;
        return this.getView();
    }

    /**
     * Get view for current instruction
     *
     * @return View showing the instruction
     */
    private InstructionView getView() {
        InstructionView result = null;

        // cases when productionInstructionView is required
        if (this.position >= 5 && this.position <= 7) {
            // Lazy productionInstructionView initialization
            if (this.productionInstructionView == null)
                this.productionInstructionView = new ProductionInstructionView(this.context);
            // Configure productionInstructionView depending on position
            switch (this.position) {
                case 5:
                    this.productionInstructionView.setPrimaryImage(this.moneyDrawable);
                    this.productionInstructionView.setFinalImage(this.materialDrawable);
                    break;
                case 6:
                    this.productionInstructionView.setPrimaryImage(this.materialDrawable);
                    this.productionInstructionView.setFinalImage(this.productDrawable);
                    break;
                case 7:
                    this.productionInstructionView.setPrimaryImage(this.productDrawable);
                    this.productionInstructionView.setFinalImage(this.moneyDrawable);
                    break;
            }
            result = this.productionInstructionView;
        }
        //cases when employeeInstructionView is required
        else if (this.position >= 11 && this.position <= 15) {
            // Lazy employeeInstructionView initialization
            if (this.employeeInstructionView == null)
                this.employeeInstructionView = new EmployeeInstructionView(this.context);
            // Configure employeeInstructionView depending on position
            switch (this.position) {
                case 11:
                    this.employeeInstructionView.setPrimaryImage(this.moneyDrawable);
                    this.employeeInstructionView.setFinalImage(this.materialDrawable);
                    this.employeeInstructionView.setEmployeeImage(this.purchaserDrawable);
                    break;
                case 12:
                    this.employeeInstructionView.setPrimaryImage(this.materialDrawable);
                    this.employeeInstructionView.setFinalImage(this.productDrawable);
                    this.employeeInstructionView.setEmployeeImage(this.producerDrawable);
                    break;
                case 13:
                    this.employeeInstructionView.setPrimaryImage(this.productDrawable);
                    this.employeeInstructionView.setFinalImage(this.moneyDrawable);
                    this.employeeInstructionView.setEmployeeImage(this.salespersonDrawable);
                    break;
                case 14:
                    this.employeeInstructionView.setPrimaryImage(this.moneyDrawable);
                    this.employeeInstructionView.setFinalImage(this.producerDrawable);
                    this.employeeInstructionView.setEmployeeImage(this.managerDrawable);
                    break;
                case 15:
                    this.employeeInstructionView.setPrimaryImage(this.moneyDrawable);
                    this.employeeInstructionView.setFinalImage(this.managerDrawable);
                    this.employeeInstructionView.setEmployeeImage(this.managerDrawable);
                    break;
            }
            result = this.employeeInstructionView;
        }
        //cases when textInstructionView is required
        else if (this.position >= 0 && this.position < INSTRUCTIONS.length) {
            // Lazy textInstructionView initialization
            if (this.textInstructionView == null)
                this.textInstructionView = new TextInstructionView(this.context);
            result = this.textInstructionView;
        }

        // Setting the instruction text
        if (result != null)
            result.setText(INSTRUCTIONS[this.position]);

        return result;
    }

    /**
     * Get current instruction number.
     *
     * @return Zero-based instruction number.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Get total number of instructions.
     *
     * @return Total number of instruction.
     */
    public int getCount() {
        return INSTRUCTIONS.length;
    }
}
