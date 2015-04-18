package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        ListView scoresView = (ListView) findViewById(R.id.listView);
        ArrayList<Double> scores = ScoreHelper.getScores(this);
        String[] scoreTexts = new String[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            scoreTexts[i] = Integer.toString(i + 1) + ".\t" + ResourceView.toShortNumberFormat(scores.get(i));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, scoreTexts);
        scoresView.setAdapter(adapter);
    }
}
