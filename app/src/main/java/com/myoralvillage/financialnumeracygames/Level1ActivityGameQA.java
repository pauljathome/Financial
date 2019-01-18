
package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Level1ActivityGameQA extends GenericActivityGame {
    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    public int correctAnswer;
    public boolean correctOnFirstTry = true;
    public int numCorrect = 0;
    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    int scoringNumAttempts = 0;
    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    String[] scoringAnswers = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gameqa);
        Intent intent = getIntent();
        getExtras(intent);
        userHasViewedDemo = thisUser.demosViewed[0];

        if (!userHasViewedDemo) {
            startDemo();
            thisUser.demosViewed[0] = true;
        }
        startGame();
    }


    public void startDemo() {
        //function call to go to this activity's demo (a separate activity)
        Intent intent = new Intent(this, Level1ActivityDemoQA.class);
        startActivity(intent);
    }

    public void startGame() {
        startNewRound();
    }

    public void startNewRound() {
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "error";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        correctOnFirstTry = true;
        generateFinger();
    }

    public void generateFinger() {
        Random r = new Random();
        correctAnswer = r.nextInt(10);

        String filename = "game1_qa_fingers" + correctAnswer;
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());
        scoringQuestion = String.valueOf(correctAnswer);
        displayFinger(img_id);
    }

    public void displayFinger(int img_id) {
        ImageView iv = (ImageView) findViewById(R.id.img_hands);

        iv.requestLayout();

        iv.setImageResource(img_id);
        iv.setVisibility(View.VISIBLE);

        generateAnswers();
    }

    public void generateAnswers() {
        Random r = new Random();
        int wrongAnswer1 = -1;
        int wrongAnswer2 = -1;
        do {
            wrongAnswer1 = r.nextInt(10);
        } while (wrongAnswer1 == correctAnswer);
        do {
            wrongAnswer2 = r.nextInt(10);
        } while (wrongAnswer2 == correctAnswer || wrongAnswer2 == wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game1_qa_answer" + wrongAnswer1;
        filenames[1] = "game1_qa_answer" + wrongAnswer2;
        filenames[2] = "game1_qa_answer" + correctAnswer;

        scoringAnswers[0] = String.valueOf(wrongAnswer1);
        scoringAnswers[1] = String.valueOf(wrongAnswer2);
        scoringAnswers[2] = String.valueOf(correctAnswer);

        int[] takenPositions = {-1, -1, -1};
        displayAnswers(filenames, takenPositions);

    }

    public void displayAnswers(String[] filenames, int[] takenPositions) {
        for (int i = 0; i < filenames.length; i++) {

            Random answerR = new Random();
            int answerPosition = -1;
            if (i == 0) {
                answerPosition = answerR.nextInt(3);
            } else {
                do {
                    answerPosition = answerR.nextInt(3);
                }
                while (answerPosition == takenPositions[0] || answerPosition == takenPositions[1]);
            }
            takenPositions[i] = answerPosition;

            int img_id = getResources().getIdentifier(filenames[i], "drawable", getPackageName());
            String imgView_name = "img_answer" + answerPosition;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            iv.requestLayout();
            iv.setImageResource(img_id);
            iv.setTag(filenames[i]);
            iv.setVisibility(View.VISIBLE);
            iv.setClickable(true);
            iv.setAlpha((float) 1.0);
        }
    }

    public void checkAnswer(View v) {
        ImageView iv = (ImageView) findViewById(v.getId());
        String thisImage = (iv.getTag()).toString();
        int imgFileNum = Integer.parseInt((thisImage.toString()).substring(15));

        scoringNumAttempts++;
        scoringSelectedAnswer = String.valueOf(imgFileNum);

        if (imgFileNum == correctAnswer) {
            scoringCorrect = "correct";
            writeToScore("level1qa.txt");
            if (correctOnFirstTry == true) {
                numCorrect++;

                String score_name = "star" + numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            v.setClickable(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (numCorrect == 10) {
                        thisUser.activityProgress[0] = true;
                        onBackPressed();
                    } else {
                        startNewRound();
                    }
                }
            }, 3050);
        } else {
            scoringCorrect = "incorrect";
            writeToScore("level1qa.txt");
            v.setAlpha((float) 0.5);
            v.setClickable(false);
            correctOnFirstTry = false;
        }
    }


    @Override
    public void onBackPressed() {

        if (!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        backButtonPressed = true;

        Intent intent = createIntent(Level1Activity.class);
        startActivity(intent);
        finish();

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
}