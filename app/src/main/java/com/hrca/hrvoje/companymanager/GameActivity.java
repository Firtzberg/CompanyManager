package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class GameActivity extends Activity implements AdapterView.OnItemClickListener {

    /**
     * Number of the turn. When game starts it is 0.
     */
    protected int turn;

    /**
     * Currently handled subjects.
     */
    protected ArrayList<Subject> subjects;

    /**
     * Adapter for GridView
     */
    protected SubjectGridAdapter adapter;

    /**
     * Money.
     */
    protected Subject money;

    /**
     * Drawable used for managers.
     */
    protected Drawable managerDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.managerDrawable = getResources().getDrawable(R.drawable.manager);

        this.subjects = this.getInitialSubjects();
        this.money = this.subjects.get(2);
        this.adapter = new SubjectGridAdapter(this, this.subjects);
        this.getSubject(10);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(this.adapter);

        gridView.setOnItemClickListener(this);
        this.turn = 0;
    }

    /**
     * Get initial subjects array with resources and primary employees as described in the instructions.
     *
     * @return Configured initial array of subjects.
     */
    private ArrayList<Subject> getInitialSubjects() {
        // Initialize resources
        Subject money = new Subject("Money")
                .setImage(getResources().getDrawable(R.drawable.money));
        Subject material = new Subject("Raw material")
                .setImage(getResources().getDrawable(R.drawable.material));
        Subject product = new Subject("Product")
                .setImage(getResources().getDrawable(R.drawable.product));
        // Initialize primary employees
        Subject purchaser = new Subject("Purchaser")
                .setImage(getResources().getDrawable(R.drawable.purchaser));
        Subject producer = new Subject("Producer")
                .setImage(getResources().getDrawable(R.drawable.producer));
        Subject salesperson = new Subject("Salesperson")
                .setImage(getResources().getDrawable(R.drawable.salesperson));

        // Set money to $1
        money.produce();

        // Configure costs of resources
        money.setCost(0.2, product);
        product.setCost(1, material);
        material.setCost(1, money);
        //Configure employees
        purchaser.setCost(1, money)
                .setProduction(1, material)
                .setMaintenance(1, money);
        producer.setCost(2, money)
                .setProduction(1, product)
                .setMaintenance(1, money);
        salesperson.setCost(5, money)
                .setProduction(5, money)
                .setMaintenance(1, money);

        // Initialize and assign rows
        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(material);
        subjects.add(product);
        subjects.add(money);
        subjects.add(purchaser);
        subjects.add(producer);
        subjects.add(salesperson);

        return subjects;
    }

    /**
     * Get the subject at specified position. If it doesn't exist, create it.
     *
     * @param position Position of wanted subject.
     * @return Subject at specified position.
     */
    protected Subject getSubject(int position) {
        if (position < 0)
            throw new IndexOutOfBoundsException("Position must not be negative.");
        if (position < this.subjects.size())
            return this.subjects.get(position);
        for (int i = this.subjects.size(); i < position; i++)
            this.getSubject(i);
        Subject above = this.getSubject(position - 3);
        Subject newSubject = new Subject("Manager")
                .setImage(this.managerDrawable)
                .setMaintenance(1, this.money)
                .setProduction(1, above)
                .setCost(above.getCost().getQuantity() * 10, this.money);
        this.subjects.add(newSubject);
        this.adapter.notifyDataSetChanged();
        return newSubject;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Subject subject = ((SubjectView) view).getSubject();
        // In case of resources add them before next turn
        if (position < 3) {
            // In case of money
            if (position == 2)
                subject.produce(5);
            else subject.produce();
        }
        // Everyone does its job
        this.nextTurn();
        // In case of persons hire them after the already hired people did their jobs
        if (position >= 3)
            subject.produce();
    }

    /**
     * Every employee does its job and gets paid.
     * The order ensures that the required resources are available.
     */
    public void nextTurn() {
        int columns = 3;
        int i, rows = (this.subjects.size() + columns - 1) / columns;
        for (int row = 1; row < rows; row++) {
            i = this.subjects.size() - row * columns;
            if (i > columns)
                i = columns;
            for (i--; i >= 0; i--)
                this.subjects.get(row * columns + i).nextTurn();
        }
        this.turn++;
        this.adapter.notifyDataSetChanged();
    }
}
