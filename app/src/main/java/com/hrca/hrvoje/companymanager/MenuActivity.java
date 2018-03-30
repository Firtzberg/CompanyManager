package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;


public class MenuActivity extends Activity {
    private Tracker mTracker;
    //InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getResources().getString("ca-app-pub-8610807363905439/9246883308"));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startActivity(new Intent(MenuActivity.this, ScoresActivity.class));
            }
        });

        requestNewInterstitial();
        */

        // Google analytics tracking
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button continu = (Button) findViewById(R.id.continu);
        File save = getBaseContext().getFileStreamPath(GameActivity.saveFileName);
        continu.setEnabled(save.exists());

        mTracker.setScreenName(this.getLocalClassName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /*
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }*/

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
       /* if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {*/
            startActivity(new Intent(this, ScoresActivity.class));
        //}
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
