package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ScoresActivity extends Activity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        this.mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("1779D7EB87ED1FC4A2DCB69B5D963A1A")

                .build();
        this.mAdView.loadAd(adRequest);

        ListView scoresView = (ListView) findViewById(R.id.listView);
        ArrayList<Double> scores = ScoreHelper.getScores(this);
        String[] scoreTexts = new String[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            scoreTexts[i] = Integer.toString(i + 1) + ".\t" + ResourceView.toShortNumberFormat(scores.get(i));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, scoreTexts);
        scoresView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
