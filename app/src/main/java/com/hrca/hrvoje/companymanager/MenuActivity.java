package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
     *
     * @param view The pressed view.
     */
    public void instructions(View view) {
        startActivity(new Intent(this, InstructionsActivity.class));
    }

    /**
     * Start ScoresActivity.
     *
     * @param view The pressed view.
     */
    public void scores(View view) {
        startActivity(new Intent(this, ScoresActivity.class));
    }

    /**
     * Erase game stats and start GameActivity.
     *
     * @param view The pressed view.
     */
    public void newGame(View view) {
        File save = getBaseContext().getFileStreamPath(GameActivity.saveFileName);
        if (save.exists()) {
            new AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.new_game_title))
                    .setMessage(this.getString(R.string.new_game_warning))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MenuActivity.this.deleteFile(GameActivity.saveFileName);
                            MenuActivity.this.continu(null);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        } else {
            this.continu(view);
        }
    }
}
