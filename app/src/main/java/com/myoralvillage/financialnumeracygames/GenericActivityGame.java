/*
 * This code is Copyright MyOralVillage. All rights are reserved
 */

package com.myoralvillage.financialnumeracygames;


import android.content.ClipData;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by paulj on 2016-10-17.
 *
 * This class is designed to implement the code that is common to all games.
 *
 * Reading and writing status, writing performance data (it is  not yet read) etc
 *
 * Basically, just trying to reduce code duplication and make my life easier down the road
 */

public abstract class GenericActivityGame extends AppCompatActivity {

    /*
     * List of games.
     *
     * TODO : We have the user settings set of arrays and the score file
     * names. Refactor into a single array. But right now I just want to use
     * enum constants instead of magic numbers.
     */

    public enum ActivityGame {LEVEL1QA, LEVEL1UNKNOWN, LEVEL1DUALCODING,
        LEVEL2FILLINTHEBLANKS, LEVEL2LEVEL2ORDERING, LEVEL2PV,
        LEVEL3UNKNOWN, LEVEL3PURCHASE, LEVEL3EXACTCHANGE}

    public class ActivityGames {
        String scoreName;
        ActivityGame which;
    }

    public boolean userHasViewedDemo = false;
    public int numCorrect=0;
    public boolean correctOnFirstTry=true;

    int scoringNumAttempts = 0;

    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    final String[] scoringAnswers = new String[3];
    final Locale locale = Locale.US;

    public final UserSettings thisUser = new UserSettings();
    final File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    /*
     * TODO - This code needs to be radically changed
     */

    public final void writeToScore(String score_name) {
        try
        {
            if (!root.exists()) {
                root.mkdirs(); // TODO do something if this fails. What I'm not sure
            }
            File userSettingsFile = new File(root, score_name);


            FileWriter writer = new FileWriter(userSettingsFile, true);
            writer.append(thisUser.userName);
            writer.append(",");
            writer.append(String.valueOf(thisUser.userId));
            writer.append(",");
            writer.append(String.valueOf(scoringNumAttempts));
            writer.append(",");
            writer.append(scoringCorrect);
            writer.append(",");
            writer.append(scoringSelectedAnswer);
            writer.append(",");
            writer.append(scoringQuestion);

            writer.append("\n");
            writer.flush();
            writer.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setHomeButton(View v) {
        if (!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        homeButtonPressed = true;

        final Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
        finish();
    }

    public final Intent createIntent(Class newActivity) {
        Intent intent = new Intent(this, newActivity);
        intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
        intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
        intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
        intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
        intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
        intent.putExtra ( "USERSETTINGS_ADMIN", thisUser.admin);
        return intent;
    }

    public final String stringifyUserSetting() {
        String thisString = thisUser.userName + "," + String.valueOf(thisUser.userId);
        for(int i = 0; i < thisUser.demosViewed.length; i++) {
            thisString += "," + String.valueOf(thisUser.demosViewed[i]);
        }
        for(int i = 0; i < thisUser.availableLevels.length; i++) {
            thisString += "," + String.valueOf(thisUser.availableLevels[i]);
        }
        for(int i = 0; i < thisUser.activityProgress.length; i++) {
            thisString += "," + String.valueOf(thisUser.activityProgress[i]);
        }

        return thisString;
    }

    /*
     * TODO - This file really isn't of much use. Needs to be nearly completely changed
     *        especially since we are now planning on creating much more comprehensive
     *        statistics
     */
    public final void updateUserSettings() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            // input the file content to the String "input"
            BufferedReader file = new BufferedReader(new FileReader(userSettingsFile));
            String line;
            String input = "";
            String newLine ="";
            String oldLine ="";

            while ((line = file.readLine()) != null) {
                String[] thisLine = line.split(",");
                if(thisLine[0].equals(thisUser.userName)) {
                    newLine = stringifyUserSetting();
                    oldLine = line;
                }
                input += line + '\n';
            }

            file.close();

            if(!oldLine.equals(newLine)) {
                input = input.replace(oldLine, newLine);
            }
            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(userSettingsFile);
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public final void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
        thisUser.admin = intent.getBooleanExtra("USERSETTINGS_ADMIN", false);

    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    public final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

}
