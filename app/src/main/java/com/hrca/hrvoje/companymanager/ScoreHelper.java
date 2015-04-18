package com.hrca.hrvoje.companymanager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Helper class for adding and getting high scores.
 */
public class ScoreHelper {

    /**
     * Name of file for saving high scores.
     */
    public static final String scoreFileName = "scores.txt";

    /**
     * Number of saved high scores.
     */
    public static final int scoreCount = 10;

    /**
     * Add high score to scores.
     * The score will be saved if it greater than another score or number of scores didn't reach scoreCount.
     *
     * @param context Application context.
     * @param score   Score to add.
     * @return True if the score is added to the high scores.
     */
    public static boolean add(Context context, Double score) {
        // Load high scores.
        ArrayList<Double> scores = getScores(context);
        boolean added = false;

        // Add the score to the scores list if it is higher than another one.
        for (int i = 0; i < scores.size(); i++) {
            if (score > scores.get(i)) {
                scores.add(i, score);
                added = true;
                break;
            }
        }

        // Add the score to the scores list if is not higher than another one, but there is still space.
        if (!added && scores.size() <= scoreCount) {
            scores.add(score);
            added = true;
        }

        // Save scores list to file if changed
        if (added) {
            try {
                FileOutputStream writer = context.openFileOutput(scoreFileName, Context.MODE_PRIVATE);
                // Don't save more than scoreCount scores
                for (int i = 0; i < scores.size() && i < scoreCount; i++) {
                    writer.write(Double.toString(scores.get(i)).getBytes());
                    writer.write('\n');
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return added;
    }

    /**
     * Get list of saved high scores.
     *
     * @param context Application context.
     * @return High scores.
     */
    public static ArrayList<Double> getScores(Context context) {
        ArrayList<Double> scores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.openFileInput(scoreFileName)));
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(Double.parseDouble(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
