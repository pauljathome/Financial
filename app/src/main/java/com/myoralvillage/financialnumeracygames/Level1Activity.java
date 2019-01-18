package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Level1Activity extends GenericActivityGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        Intent intent = getIntent();
        getExtras(intent);
    }

    public void goToLevel1QA(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level1ActivityGameQA.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel1Tracing(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level1ActivityGameTracing.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel1DualCoding(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level1ActivityGameDualCoding.class);
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
