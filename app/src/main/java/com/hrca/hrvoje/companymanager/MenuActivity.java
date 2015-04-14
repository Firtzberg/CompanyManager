package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button continu = (Button) findViewById(R.id.continu);
        File save = getBaseContext().getFileStreamPath(GameActivity.saveFileName);
        continu.setEnabled(save.exists());
    }

    /**
     * Start GameActivity.
     *
     * @param view The pressed view.
     */
    public void continu(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    /**
     * Start InstructionsActivity.
     * @param view The pressed view.
     */
    public void instructions(View view) {
        startActivity(new Intent(this, InstructionsActivity.class));
    }

    /**
     * Erase game stats and start GameActivity.
     * @param view The pressed view.
     */
    public void newGame(View view) {
        this.deleteFile(GameActivity.saveFileName);
        this.continu(view);
    }
}
