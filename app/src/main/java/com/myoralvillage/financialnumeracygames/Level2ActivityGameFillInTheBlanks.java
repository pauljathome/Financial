package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Level2ActivityGameFillInTheBlanks extends GenericActivityGame {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    public int[] sequence = new int[5]; //this is where the sequence of numbers for the question is stored
    public int[] options = new int[5]; // this is where the option answers is stored (one of which is the correct answer)
    public int patternNumber; // this is the actual pattern itself. for ex: if patternNumber = 2, the numbers in the sequence will increment by 2
    public int missingPosition; //this stores the missing position
    public int missingAnswer; //this stores the position of the answer (1,2, or 3)
    public Random randomFirstNumber = new Random(); //this randomizes the first number for the question
    public Random randomPattern = new Random(); //this randomizes the patternNumber
    public Random randomMissingPosition = new Random(); //this randomizes the position that is missing (from 1-4)
    public Random randomMissingAnswer = new Random(); //this generates the random position of the answer (1,2 or 3)
    public int difficultyLevel = 1;

    public int numCorrect = 1;
    public boolean correctOnFirstTry;
    public boolean firstAttempt = true;

    int scoringNumAttempts = 0;
    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    String[] scoringAnswers = new String[3];

    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_level2_gamefillintheblanks);
        Intent intent = getIntent();
        getExtras(intent);

        userHasViewedDemo = thisUser.demosViewed[3];


        Typeface myTypeFace = Typeface.createFromAsset(getAssets(),"fonts/TanzaFont.ttf");
        Button myButton = (Button)findViewById(R.id.optionView0);
        myButton.setTypeface(myTypeFace);
        myButton = (Button)findViewById(R.id.optionView1);
        myButton.setTypeface(myTypeFace);
        myButton = (Button)findViewById(R.id.optionView2);
        myButton.setTypeface(myTypeFace);
        TextView myTextView = (TextView)findViewById(R.id.sequenceView0);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView1);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView2);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView3);
        myTextView.setTypeface(myTypeFace);

       if(!userHasViewedDemo) {
           startDemo();
           thisUser.demosViewed[3] = true;
       }
       generateSequence();
   }

   public void startDemo() {
       Intent intent = new Intent(this, Level2ActivityDemoFillInTheBlanks.class);
       startActivity(intent);
    }


    public void generateSequence(){
        correctOnFirstTry = true;
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        firstAttempt = true;
        //generate a random first number, a random pattern and store the sequence in an array
        if(difficultyLevel < 2) {
            if (numCorrect < 10) {
                sequence[0] = randomFirstNumber.nextInt(89) + 10;
            }
            if (numCorrect >=  10) {
                difficultyLevel++;
                numCorrect = 0;
                thisUser.activityProgress[3] = true;
                //sequence[0] = randomFirstNumber.nextInt(89) + 10;
            }

        }

        else if (difficultyLevel == 2) {
            if (numCorrect < 10) {
                sequence[0] = randomFirstNumber.nextInt(899) + 100;
            }
            if (numCorrect >=  10) {
                difficultyLevel++;
                numCorrect = 0;
                //sequence[0] = randomFirstNumber.nextInt(89) + 10;
            }
        }

        else {
            sequence[0] = randomFirstNumber.nextInt(941) + 10;
        }


        patternNumber = randomPattern.nextInt(4) + 2;
        sequence[1] = sequence[0]+ patternNumber;
        sequence[2] = sequence[1] + patternNumber;
        sequence[3] = sequence[2] + patternNumber;
        //generate a number that indicates the missing position
        missingPosition = randomMissingPosition.nextInt(3);
        missingAnswer = randomMissingAnswer.nextInt(2);

        // generate options
        if (missingAnswer==0) {
            options[0] = sequence[missingPosition];
            options[1] = (sequence[0] - patternNumber);
            options[2] = (sequence[3] + patternNumber);

            playGame(sequence, options, missingPosition);
        }
        if (missingAnswer == 1) {
            options[0] = (sequence[0] - patternNumber);
            options[1] = sequence[missingPosition];
            options[2] = (sequence[3] + patternNumber);

            playGame(sequence, options, missingPosition);
        }

        if (missingAnswer == 2) {
            options[0] = (sequence[0] - patternNumber);
            options[1] = (sequence[3] + patternNumber);
            options[2] = sequence[missingPosition];
            playGame(sequence, options, missingPosition);
        }
    }

    public void playGame(int[]sequence, int[] options, int missingPosition) {
        // take the options array and display each number in a button at the bottom of the screen
        TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
        if (sequence[missingPosition] == sequence[0]){
            sequenceView0.setText("_");
            scoringQuestion += ","+"_";
        }
        else {
            sequenceView0.setText(String.valueOf(sequence[0]));
            scoringQuestion += ","+String.valueOf(sequence[0]);
        }

        TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
        if (sequence[missingPosition] == sequence[1]) {
            sequenceView1.setText("_");
            scoringQuestion += ","+"_";
        } else {
            sequenceView1.setText(String.valueOf(sequence[1]));
            scoringQuestion += ","+String.valueOf(sequence[1]);
        }

        TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
        if (sequence[missingPosition] == sequence[2]) {
            sequenceView2.setText("_");
            scoringQuestion += ","+"_";
        } else {
            sequenceView2.setText(String.valueOf(sequence[2]));
            scoringQuestion += ","+String.valueOf(sequence[2]);
        }

        TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
        if (sequence[missingPosition] == sequence[3]) {
            sequenceView3.setText("_");
            scoringQuestion += ","+"_";
        } else {
            sequenceView3.setText(String.valueOf(sequence[3]));
            scoringQuestion += ","+String.valueOf(sequence[3]);
        }
        Button optionView0 = (Button) findViewById(R.id.optionView0);
        optionView0.setText(String.valueOf(options[0]));
        Button optionView1 = (Button) findViewById(R.id.optionView1);
        optionView1.setText(String.valueOf(options[1]));
        Button optionView2 = (Button) findViewById(R.id.optionView2);
        optionView2.setText(String.valueOf(options[2]));
        optionView0.setClickable(true);
        optionView1.setClickable(true);
        optionView2.setClickable(true);
        optionView0.setAlpha(1f);
        optionView1.setAlpha(1f);
        optionView2.setAlpha(1f);

        scoringAnswers[0] = String.valueOf(options[0]);
        scoringAnswers[1] = String.valueOf(options[1]);
        scoringAnswers[2] = String.valueOf(options[2]);
     }

    public void checkThisAnswer (View v) {
        Button optionView0 = (Button) findViewById(R.id.optionView0);
        Button optionView1 = (Button) findViewById(R.id.optionView1);
        Button optionView2 = (Button) findViewById(R.id.optionView2);
        optionView0.setClickable(false);
        optionView1.setClickable(false);
        optionView2.setClickable(false);
        scoringNumAttempts++;
        Button mButton = (Button) findViewById(v.getId());
        int thisNumber = Integer.parseInt(mButton.getText().toString());
        scoringSelectedAnswer = String.valueOf(thisNumber);
        if (thisNumber==sequence[missingPosition]){
            optionView0.setClickable(false);
            optionView1.setClickable(false);
            optionView2.setClickable(false);
            scoringCorrect = "correct";
            if(correctOnFirstTry==true) {
                //numCorrect++;
                String score_name = "star" + numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            writeToScore("level2fillintheblanks.txt");
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
            TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
            TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            /*sequenceView0.setText(String.valueOf(sequence[0]));
            sequenceView1.setText(String.valueOf(sequence[1]));
            sequenceView2.setText(String.valueOf(sequence[2]));
            sequenceView3.setText(String.valueOf(sequence[3]));*/
            sequenceView0.setText(String.valueOf(sequence[0]));
            sequenceView1.setText(String.valueOf(sequence[1]));
            sequenceView2.setText(String.valueOf(sequence[2]));
            sequenceView3.setText(String.valueOf(sequence[3]));
            if(firstAttempt) {
                numCorrect++;
            }

            mediaPlayer.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateSequence();
                }
            }, 3050);

        } else {
            correctOnFirstTry=false;
            scoringCorrect = "incorrect";
            writeToScore("level2fillintheblanks.txt");
            firstAttempt = false;
            mButton.setClickable(false);
            mButton.setAlpha(.5f);
            correctOnFirstTry=false;
            optionView0.setClickable(true);
            optionView1.setClickable(true);
            optionView2.setClickable(true);
        }
    }


    @Override
    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        backButtonPressed = true;

        Intent intent = createIntent(Level2Activity.class);
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
