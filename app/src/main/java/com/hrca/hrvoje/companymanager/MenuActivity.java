package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void continu(View view) {

    }

    public void instructions(View view) {
        startActivity(new Intent(this, InstructionsActivity.class));
    }

    public void newGame(View view) {

    }
}
