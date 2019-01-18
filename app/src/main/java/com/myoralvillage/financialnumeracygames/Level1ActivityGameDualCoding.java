package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class Level1ActivityGameDualCoding extends GenericActivityGame {
    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)

    public int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gamedualcoding);
        Intent intent = getIntent();
        getExtras(intent);
        userHasViewedDemo = thisUser.demosViewed[2];

        if(!userHasViewedDemo){
            startDemo();
            thisUser.demosViewed[2] = true;
        }
        startGame();
    }


    public void startDemo() {
        //function call to go to this activity's demo (a separate activity)
        Intent intent = new Intent(this, Level1ActivityDemoDualCoding.class);
        startActivity(intent);
    }

    public void startGame() {
        //Game code in here + game method calls
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
        generateQuestion();
    }

    public void generateQuestion() {
        Random r = new Random();
        correctAnswer=r.nextInt(10);

        String filename = "game1_dualcoding_"+correctAnswer;
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());

        scoringQuestion = String.valueOf(correctAnswer);

        displayQuestion(img_id);
    }

    public void displayQuestion(int img_id) {
        ImageView iv = (ImageView) findViewById(R.id.img_question);

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
        } while(wrongAnswer1==correctAnswer);
        do {
            wrongAnswer2 = r.nextInt(10);
        } while(wrongAnswer2==correctAnswer || wrongAnswer2==wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game1_qa_answer"+wrongAnswer1;
        filenames[1] = "game1_qa_answer"+wrongAnswer2;
        filenames[2] = "game1_qa_answer"+correctAnswer;

        scoringAnswers[0] = String.valueOf(wrongAnswer1);
        scoringAnswers[1] = String.valueOf(wrongAnswer2);
        scoringAnswers[2] = String.valueOf(correctAnswer);

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(filenames, takenPositions);

    }

    public void displayAnswers(String[] filenames, int[] takenPositions) {
        for (int i = 0; i < filenames.length; i++) {

            Random answerR = new Random();
            int answerPosition = -1;
            if (i==0) {
                answerPosition = answerR.nextInt(3);
            } else {
                do {
                    answerPosition = answerR.nextInt(3);
                } while (answerPosition==takenPositions[0]||answerPosition==takenPositions[1]);
            }
            takenPositions[i]=answerPosition;

            int img_id = getResources().getIdentifier(filenames[i], "drawable", getPackageName());
            String imgView_name = "img_answer"+answerPosition;
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

    public void checkAnswer(final View v) {
        scoringNumAttempts++;
        ImageView iv = (ImageView) findViewById(v.getId());
        String thisImage = (iv.getTag()).toString();
        int imgFileNum = Integer.parseInt((thisImage.toString()).substring(15));
        scoringSelectedAnswer = String.valueOf(imgFileNum);

        if (imgFileNum==correctAnswer) {
            scoringCorrect = "correct";
            writeToScore("level1dualcoing.txt");
            if(correctOnFirstTry==true) {
                numCorrect++;
                String score_name = "star"+numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            String filename = "game1_demo_dualcoding_"+correctAnswer;
            int correct_image = getResources().getIdentifier(filename, "drawable", getPackageName());

            ImageView question = (ImageView) findViewById(R.id.img_question);

            question.requestLayout();
            question.setImageResource(correct_image);

            v.setClickable(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(numCorrect==10) {
                        thisUser.activityProgress[2] = true;
                        onBackPressed();
                    } else {
                        startNewRound();
                    }
                }
            }, 3050);
        } else {
            scoringCorrect = "incorrect";
            writeToScore("level1dualcoing.txt");
            v.setAlpha((float)0.5);
            v.setClickable(false);
            correctOnFirstTry = false;
        }
    }


    @Override
    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        backButtonPressed = true;

        Intent intent = createIntent(Level1Activity.class);
        startActivity(intent);
        finish();
    }

}
