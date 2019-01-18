package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Level2Activity extends GenericActivityGame {
    public UserSettings thisUser = new UserSettings();
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        Intent intent = getIntent();
        getExtras(intent);
    }

    public void goToLevel2PlaceValue(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level2ActivityGamePV.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel2Ordering(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level2ActivityGameOrdering.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel2FillInTheBlanks(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level2ActivityGameFillInTheBlanks.class);
        startActivity(intent);
        finish();
    }

    public void setHomeButton(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        homeButtonPressed = true;
        Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        backButtonPressed = true;
        Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
