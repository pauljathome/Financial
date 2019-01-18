package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Level2ActivityGamePV extends GenericActivityGame {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    //getValue for level from text file, set it here
    //right now, because there are no users, we'll set this to 0
    //(user starts from easiest level)
    public int difficultyLevel = 0;
    public Integer[][][] problems = {{{10,1,100},{2,200,20},{500,50,5},{900,9,90},{60,600,6},{7,70,700},{300,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{25,20,5},{82,80,2},{79,70,9},{21,11,31},{53,63,43},{84,74,94},{66,65,67},{34,35,33},{98,99,97},{81,18,19},{12,21,23},{75,57,58},{79,97,78},{43,42,44},{49,94,48},{66,56,67},{22,33,11},{71,17,72},{25,52,26},{69,96,68},},{{852,500,800},{583,500,800},{298,200,900},{230,200,30},{905,900,5},{370,300,70},{348,843,308},{910,190,901},{452,204,254},{976,679,769},{531,134,351},{86,68,67},{79,97,78},{43,42,44},{900,90,9},{66,860,680},{989,898,998},{494,949,499},{255,552,522},{171,717,117}}};
    public int problemNumber = -1;
    public List<List<Integer>> questions = new ArrayList<>();

    public int correctAnswer=0;
    public boolean correctOnFirstTry = true;
    public int numCorrect = 0;
    public List<Integer> correctList = new ArrayList<>();


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
        setContentView(R.layout.activity_level2_gamepv);
        Intent intent = getIntent();
        getExtras(intent);

        userHasViewedDemo = thisUser.demosViewed[5];

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(),"fonts/TanzaFont.ttf");
        TextView myTextView = (TextView)findViewById(R.id.tv_answer0);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.tv_answer1);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.tv_answer2);
        myTextView.setTypeface(myTypeFace);
        if(!userHasViewedDemo) {
            startDemo();
            thisUser.demosViewed[5] = true;
        }
        startNewRound();
    }

    public void startDemo() {
        //go to demo activity (should be a separate activity)
        Intent intent = new Intent(this, Level2ActivityDemoPV.class);
        startActivity(intent);
    }

    public void startNewRound () {
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "error";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        correctAnswer = 0;
        correctOnFirstTry = true;
        clearLinearLayouts();
        clearImageViews();

        if(questions.size()==0) {
            questions = generateQuestions(problems[difficultyLevel]);
        }
        do {
            Random rand = new Random();
            int randInt = questions.size();
            problemNumber = rand.nextInt(randInt);
        } while (questions.get(problemNumber)==null);
        generateRepresentation();
    }

    public void clearLinearLayouts() {
        int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
        LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
        LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
        onesLLParams.weight = 0f;
        ll_ones.setLayoutParams(onesLLParams);

        int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
        LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
        LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
        tensLLParams.weight = 0f;
        ll_tens.setLayoutParams(tensLLParams);

        int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
        LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
        LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
        hundredsLLParams.weight = 0f;
        ll_hundreds.setLayoutParams(hundredsLLParams);
    }

    public void clearImageViews() {
        for (int i = 1; i <= 10; i++) {
            String imgView_name_ones = "ones_representation"+i;
            int res_id_ones = getResources().getIdentifier(imgView_name_ones, "id", getPackageName());
            ImageView iv_ones = (ImageView) findViewById(res_id_ones);
            iv_ones.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams onesParams = (LinearLayout.LayoutParams) iv_ones.getLayoutParams();
            onesParams.weight = 0f;
            onesParams.gravity = Gravity.CENTER;
            iv_ones.setLayoutParams(onesParams);

            String imgView_name_tens = "tens_representation"+i;
            int res_id_tens = getResources().getIdentifier(imgView_name_tens, "id", getPackageName());
            ImageView iv_tens = (ImageView) findViewById(res_id_tens);
            iv_tens.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams tensParams = (LinearLayout.LayoutParams) iv_tens.getLayoutParams();
            tensParams.weight = 0f;
            tensParams.gravity = Gravity.CENTER;
            iv_tens.setLayoutParams(tensParams);

            String imgView_name_hundreds = "hundreds_representation"+i;
            int res_id_hundreds = getResources().getIdentifier(imgView_name_hundreds, "id", getPackageName());
            ImageView iv_hundreds = (ImageView) findViewById(res_id_hundreds);
            iv_hundreds.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams hundredsParams = (LinearLayout.LayoutParams) iv_hundreds.getLayoutParams();
            hundredsParams.weight = 0f;
            hundredsParams.gravity = Gravity.CENTER;
            iv_hundreds.setLayoutParams(hundredsParams);
        }
    }
    public void generateRepresentation() {
        //maximum 9 images (90, 900, 9000)
        //int pv=randomPlaceValue.nextInt(3)+1;
        correctAnswer = questions.get(problemNumber).get(0);

        scoringQuestion = String.valueOf(correctAnswer);
        int[] representation = getRepresentation();
        drawRepresentation(representation);

        //questions = generateQuestions(problems[difficultyLevel][problemNumber]);
        generateAnswers(questions.get(problemNumber));
    }

    public List<List<Integer>> generateQuestions(Integer[][] a) {
        List<List<Integer>> c = new ArrayList<>(a.length);
        for(int i = 0; i < a.length; i++) {
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < a[i].length; j++) {
                temp.add(a[i][j]);
            }
            c.add(temp);
        }
        return c;
    }

    public int[] getRepresentation(){
        int ones = correctAnswer%10;
        int tens = ((correctAnswer%100)-(correctAnswer%10))/10;
        int hundreds = (correctAnswer-(correctAnswer%100))/100;
        int[] representation = {hundreds,tens,ones};
        return representation;
    }

    public void drawRepresentation(int[] representation) {
        int numPVs = 0;
        if(representation[2]==0) {
            int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
            LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
            LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
            onesLLParams.weight = 0f;
            ll_ones.setLayoutParams(onesLLParams);
        } else {
            numPVs++;
        }
        if(representation[1]==0) {
            int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
            LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
            LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
            tensLLParams.weight = 0f;
            ll_tens.setLayoutParams(tensLLParams);
        } else {
            numPVs++;
        }
        if(representation[0]==0) {
            int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
            LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
            LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
            hundredsLLParams.weight = 0f;
            ll_hundreds.setLayoutParams(hundredsLLParams);
        } else {
            numPVs++;
        }

        if(representation[2]>0) {
            int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
            LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
            LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
            onesLLParams.weight = (float) (1.0/(double)numPVs);
            ll_ones.setLayoutParams(onesLLParams);

        }
        if(representation[1]>0) {
            int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
            LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
            LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
            tensLLParams.weight = (float) (1.0/(double)numPVs);
            ll_tens.setLayoutParams(tensLLParams);
        }
        if(representation[0]>0) {
            int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
            LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
            LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
            hundredsLLParams.weight = (float) (1.0 / (double) numPVs);
            ll_hundreds.setLayoutParams(hundredsLLParams);
        }

        for (int i = 1; i <= representation[2]; i++) {
            String imgView_name = "ones_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_ones);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_ones");
            LinearLayout.LayoutParams onesParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            onesParams.weight = (float)(1.0/(double)representation[2]);
            onesParams.gravity = Gravity.CENTER;

            iv.setLayoutParams(onesParams);
            iv.setVisibility(View.VISIBLE);
        }

        for (int i = 1; i <= representation[1]; i++) {
            String imgView_name = "tens_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_tens);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_tens");
            LinearLayout.LayoutParams tensParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            tensParams.weight = (float)(1.0/(double)representation[1]);

            tensParams.gravity = Gravity.CENTER;

            iv.setLayoutParams(tensParams);
            iv.setVisibility(View.VISIBLE);
        }

        for (int i = 1; i <= representation[0]; i++) {
            String imgView_name = "hundreds_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_hundreds);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_hundreds");
            LinearLayout.LayoutParams hundredsParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            hundredsParams.weight = (float)(1.0/(double)representation[0]);

            hundredsParams.gravity = Gravity.CENTER;

            iv.setLayoutParams(hundredsParams);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public void generateAnswers(List<Integer> answers) {
        int wrongAnswer1 = answers.get(1);
        int wrongAnswer2 = answers.get(2);

        String[] filenames = new String[3];
        filenames[0] = "game2_answer"+wrongAnswer1;
        filenames[1] = "game2_answer"+wrongAnswer2;
        filenames[2] = "game2_answer"+correctAnswer;

        scoringAnswers[0] = String.valueOf(wrongAnswer1);
        scoringAnswers[1] = String.valueOf(wrongAnswer2);
        scoringAnswers[2] = String.valueOf(correctAnswer);

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(answers, takenPositions);

    }

    public void displayAnswers(List<Integer> answers, int[] takenPositions) {
        for (int i = 0; i < answers.size(); i++) {

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

            String tvName = "tv_answer"+answerPosition;
            int resourceId = getResources().getIdentifier(tvName, "id", getPackageName());
            TextView tv = (TextView) findViewById(resourceId);
            tv.setText(String.valueOf(answers.get(i)));
            tv.setVisibility(tv.VISIBLE);

            String rlName = "rel_answer"+answerPosition;
            int resourceIdRL= getResources().getIdentifier(rlName, "id", getPackageName());
            RelativeLayout rl = (RelativeLayout) findViewById(resourceIdRL);
            rl.setVisibility(View.VISIBLE);
            rl.setClickable(true);
            rl.setAlpha((float) 1.0);
        }
    }

    public void checkAnswer(View v) {
        scoringNumAttempts++;
        RelativeLayout rl = (RelativeLayout) findViewById(v.getId());
        TextView tv = (TextView) rl.getChildAt(1);
        int thisNumber = Integer.parseInt(tv.getText().toString());

        scoringSelectedAnswer = String.valueOf(thisNumber);

        if (thisNumber==correctAnswer) {
            scoringCorrect = "correct";
            writeToScore("level2pv.txt");
            if(correctOnFirstTry) {
                if(!correctList.contains(problemNumber)) {
                    numCorrect++;
                }
                correctList.add(problemNumber);
                questions.set(problemNumber,null);
                String score_name = "star" + numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv_score = (ImageView) findViewById(R.id.score);
                tv_score.setImageResource(score_id);
            }


            v.setClickable(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(numCorrect>=10 && difficultyLevel < 2) {
                        thisUser.activityProgress[5] = true;
                        difficultyLevel++;
                        numCorrect=0;
                        String score_name = "star" + numCorrect;
                        int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                        ImageView tv_score = (ImageView) findViewById(R.id.score);
                        tv_score.setImageResource(score_id);
                        correctList.clear();
                        questions.clear();
                        startNewRound();
                    } else if (numCorrect>=10 && difficultyLevel >= 2) {
                        onBackPressed();
                    } else {
                        startNewRound();
                    }
                }
            }, 3050);

        } else {
            scoringCorrect = "incorrect";
            writeToScore("level2pv.txt");
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
