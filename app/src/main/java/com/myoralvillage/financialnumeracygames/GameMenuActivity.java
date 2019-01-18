package com.myoralvillage.financialnumeracygames;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class GameMenuActivity extends GenericActivityGame {
    //Used to store user profile data
    public UserSettings thisUser = new UserSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        //Receive user profile data from LoginActivity
        Intent intent = getIntent();
        getExtras(intent);
        setLevelAvailability();
    }

    //Grant/deny level access to users based off of game completion status
    public void setLevelAvailability() {
        for (int i = 0; i < thisUser.availableLevels.length; i++) {
            String thisId = "btn_lvl" + String.valueOf(i+1);
            int resId = getResources().getIdentifier(thisId, "id", getPackageName());
            ImageButton thisButton = (ImageButton) findViewById(resId);
            thisButton.setClickable(true);
            thisButton.setAlpha(1.0f);
            if(!thisUser.admin && !thisUser.availableLevels[i]) {
                thisButton.setClickable(false);
                thisButton.setAlpha(0.5f);
            }
        }
    }

    //onClick: go to L1 Menu
    public void goToLevel1(View v) {
        Intent intent = createIntent(Level1Activity.class);
        startActivity(intent);
        finish();
    }

    //onClick: go to L2 Menu
    public void goToLevel2(View v) {
        Intent intent = createIntent(Level2Activity.class);
        startActivity(intent);
        finish();
    }

    //onClick: go to L3 Menu
    public void goToLevel3(View v) {
        Intent intent = createIntent(Level3Activity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        backButtonPressed = true;
        Intent intent = createIntent(LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
