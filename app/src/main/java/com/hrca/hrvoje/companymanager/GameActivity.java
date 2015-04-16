package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class GameActivity extends Activity implements AdapterView.OnItemClickListener {

    /**
     * Number of the turn. When game starts it is 0.
     */
    protected int turn;

    /**
     * Primary resources.
     */
    protected ArrayList<Resource> resources;

    /**
     * List of human resources.
     */
    protected ArrayList<HumanResource> employees;

    /**
     * Adapter for primary resources.
     *
     * @see com.hrca.hrvoje.companymanager.GameActivity#resources
     */
    protected ResourceGridAdapter resourceAdapter;

    /**
     * Adapter for employees.
     *
     * @see com.hrca.hrvoje.companymanager.GameActivity#employees
     */
    protected ResourceGridAdapter employeeAdapter;

    /**
     * Money.
     */
    protected Resource money;

    /**
     * Drawable used for managers.
     */
    protected Drawable managerDrawable;

    /**
     * Filename for saving game stats.
     */
    public static final String saveFileName = "save.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.managerDrawable = getResources().getDrawable(R.drawable.manager);

        this.turn = 0;
        this.initResourcesAndAdapters();
        this.getEmployee(16);

        GridView resourceGrid = (GridView) findViewById(R.id.resourceGrid);
        resourceGrid.setAdapter(this.resourceAdapter);
        resourceGrid.setOnItemClickListener(this);
        GridView employeesGrid = (GridView) findViewById(R.id.employeesGrid);
        employeesGrid.setAdapter(this.employeeAdapter);
        employeesGrid.setOnItemClickListener(this);
    }

    /**
     */
    private void initResourcesAndAdapters() {
        // Initialize resources
        Resource money = new Resource("Money");
        money.setImage(getResources().getDrawable(R.drawable.money));
        Resource material = new Resource("Raw material");
        material.setImage(getResources().getDrawable(R.drawable.material));
        Resource product = new Resource("Product");
        product.setImage(getResources().getDrawable(R.drawable.product));
        // Initialize primary employees
        HumanResource purchaser = new HumanResource("Purchaser");
        purchaser.setImage(getResources().getDrawable(R.drawable.purchaser));
        HumanResource producer = new HumanResource("Producer");
        producer.setImage(getResources().getDrawable(R.drawable.producer));
        HumanResource salesperson = new HumanResource("Salesperson");
        salesperson.setImage(getResources().getDrawable(R.drawable.salesperson));

        // Initialize lit and assign resources and basic employees
        this.resources = new ArrayList<>();
        this.resources.add(material);
        this.resources.add(product);
        this.resources.add(money);
        this.employees = new ArrayList<>();
        this.employees.add(purchaser);
        this.employees.add(producer);
        this.employees.add(salesperson);

        // Try open saveFile
        BufferedReader reader = null;
        File save = getBaseContext().getFileStreamPath(saveFileName);
        if (save.exists()) {
            try {
                reader = new BufferedReader(
                        new InputStreamReader(
                                this.openFileInput(saveFileName)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (reader == null) {
            // Set money to $1
            money.produce();
        } else {
            try {
                // Parse turn
                this.turn = Integer.parseInt(reader.readLine());
                // Parse and assign quantities to created resources
                for (int i = 0; i < this.resources.size(); i++) {
                    this.resources.get(i).produce(Double.parseDouble(reader.readLine()));
                }
                // Parse and assign quantities to created primary employees
                for (int i = 0; i < this.employees.size(); i++) {
                    this.employees.get(i).produce(Double.parseDouble(reader.readLine()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Configure costs of resources
        money.setCost(0.2, product);
        product.setCost(1, material);
        material.setCost(1, money);
        //Configure employees
        purchaser.setCost(1, money);
        purchaser.setProduction(1, material);
        purchaser.setSalary(1, money);
        producer.setCost(2, money);
        producer.setProduction(1, product);
        producer.setSalary(1, money);
        salesperson.setCost(5, money);
        salesperson.setProduction(5, money);
        salesperson.setSalary(1, money);

        // If save exists
        if (reader != null) {
            String line;
            HumanResource resource;
            HumanResource above;
            try {
                // Fill all other saved employees
                while ((line = reader.readLine()) != null) {
                    above = this.employees.get(this.employees.size() - this.resources.size());
                    resource = new HumanResource("Manager");
                    resource.setImage(getResources().getDrawable(R.drawable.manager));
                    resource.setSalary(above.getSalary().getQuantity() * 2, money);
                    resource.setProduction(1, above);
                    resource.produce(Double.parseDouble(line));
                    resource.setCost(above.getCost().getQuantity() * 10, money);
                    this.employees.add(resource);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.money = money;
        this.resourceAdapter = new ResourceGridAdapter(this, this.resources);
        this.employeeAdapter = new ResourceGridAdapter(this, this.employees);
    }

    /**
     * Get the employee at specified position. If it doesn't exist, create it.
     *
     * @param position Position of wanted employee.
     * @return Employee at specified position.
     */
    protected HumanResource getEmployee(int position) {
        if (position < 0)
            throw new IndexOutOfBoundsException("Position must not be negative.");
        if (position < this.employees.size())
            return this.employees.get(position);
        for (int i = this.employees.size(); i < position; i++)
            this.getEmployee(i);
        HumanResource above = this.getEmployee(position - this.resources.size());
        HumanResource manager = new HumanResource("Manager");
        manager.setImage(this.managerDrawable);
        manager.setSalary(above.getSalary().getQuantity() * 2, this.money);
        manager.setProduction(1, above);
        manager.setCost(above.getCost().getQuantity() * 10, this.money);
        this.employees.add(manager);
        this.employeeAdapter.notifyDataSetChanged();
        return manager;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int gridId = parent.getId();
        Resource resource = ((ResourceView) view).getResource();
        // In case of resources add them before next turn
        if (gridId == R.id.resourceGrid) {
            // In case of money
            if (position == 2)
                resource.produce(5);
            else resource.produce();
        }
        // Everyone does its job
        this.nextTurn();
        // In case of persons hire them after the already hired people did their jobs
        if (gridId == R.id.employeesGrid) {
            resource.produce();
        }
    }

    /**
     * Every employee does its job and gets paid.
     * The order ensures that the required resources are available.
     */
    public void nextTurn() {
        for (int i = 2; i >= 0; i--) {
            this.employees.get(i).work();
            this.employees.get(i).pay();
        }
        for (int i = 3; i < this.employees.size(); i++) {
            this.employees.get(i).work();
            this.employees.get(i).pay();
        }
        this.turn++;
        // Auto save
        this.save();
        this.resourceAdapter.notifyDataSetChanged();
        this.employeeAdapter.notifyDataSetChanged();
    }

    /**
     * Save current game stats to file.
     *
     * @see com.hrca.hrvoje.companymanager.GameActivity#saveFileName
     */
    public void save() {
        FileOutputStream out;
        try {
            out = this.openFileOutput(saveFileName, MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        OutputStreamWriter writer = new OutputStreamWriter(out);
        try {
            writer.write(Integer.toString(this.turn));
            for (int i = 0; i < this.resources.size(); i++) {
                writer.write('\n');
                writer.write(Double.toString(this.resources.get(i).getNumber()));
            }
            for (int i = 0; i < this.employees.size(); i++) {
                writer.write('\n');
                writer.write(Double.toString(this.employees.get(i).getNumber()));
            }
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
