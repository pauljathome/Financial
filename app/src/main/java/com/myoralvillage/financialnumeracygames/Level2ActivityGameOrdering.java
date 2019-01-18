package com.myoralvillage.financialnumeracygames;

import android.content.ClipData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.DragEvent;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import android.view.View.DragShadowBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level2ActivityGameOrdering extends GenericActivityGame {

    public boolean userHasViewedDemo = false;
    public int numCorrect;
    public int numWrong;
    public int score=0;
    public boolean correctOnFirstTry;
    public CharSequence dragData;
    public Button mNextButton;
    public TextView sequenceView0, sequenceView1, sequenceView2, sequenceView3, optionView0, optionView1, optionView2, optionView3;
    public int[][] difficulty0Problems = {{50,30,20,10},{95,45,85,65},{25,15,55,35},{95,55,75,35},{75,35,85,25},{65,85,15,75},{60,40,70,50},{40,90,80,100},{55,65,75,85},{85,15,45,55},{10,20,90,40},{20,30,40,10},{40,20,80,60},{50,40,30,60},{20,10,30,50},{60,30,50,80},{50,90,30,20},{40,30,20,10},{90,50,60,20},{90,80,20,10},{30,20,50,10},{30,50,20,90},{10,20,40,30},{50,20,90,40},{60,80,70,20},{40,30,20,10},{40,60,50,30},{60,80,50,20},{30,20,10,40},{70,80,90,60},{90,40,30,70}};
    public int[][] difficulty1Problems = {{590,330,280,120},{660,420,730,570},{400,900,800,100},{100,200,900,400},{220,380,430,130},{400,270,820,670},{500,490,300,600},{200,130,300,520},{670,360,500,800},{560,950,320,230},{400,360,200,100},{900,500,600,200},{900,840,200,100},{350,260,520,190},{300,500,200,900},{100,200,400,300},{570,280,940,430},{610,860,700,210},{400,340,290,100},{400,600,500,300},{690,850,530,220},{350,240,130,480},{920,620,130,950},{590,370,830,910},{100,120,190,180},{930,920,990,910},{230,210,270,290},{430,440,490,450},{320,390,350,310},{590,580,520,540},{550,590,530,520},{670,610,620,680},{850,860,880,890},{760,730,710,720},{390,300,350,380},{400,830,580,650}};
    public int difficultyLevel=0;
    List<TextView> wrongAnswers = new ArrayList<TextView>();
    List<TextView> wrongBaskets = new ArrayList<TextView>();

    public int[] randomNumbers = new int[4];
    public int[] orderedNumbers = new int[4];

    public boolean firstAttempt = true;
    public int numAnswersCorrect = 0;
    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    int scoringNumAttempts = 0;
    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    String[] scoringAnswers = new String[3];

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_gameordering);
        Intent intent = getIntent();
        getExtras(intent);

        userHasViewedDemo = thisUser.demosViewed[4];

        if(!userHasViewedDemo) {
            startDemo();
            thisUser.demosViewed[4] = true;
        }
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(),"fonts/TanzaFont.ttf");
        TextView myTextView = (TextView)findViewById(R.id.optionView0);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.optionView1);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.optionView2);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.optionView3);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView0);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView1);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView2);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView3);
        myTextView.setTypeface(myTypeFace);
        generateSequence();
    }

    public void startDemo() {
    //method call to DemoActivity (separate activity)
        Intent intent = new Intent(this, Level2ActivityDemoOrdering.class);
        startActivity(intent);
    }


    public void generateSequence() {
        correctOnFirstTry=true;
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        numCorrect = 0;
        numWrong = 0;
        firstAttempt = true;

//        Random r = new Random();
//        int randomNum = r.nextInt(problems[difficulty].length)-1;
//        generate an array of random numbers if diffciulty is
        int randomInt=0;
        if (difficultyLevel==0) {
            Random r = new Random();
            randomInt = r.nextInt(difficulty0Problems.length);
            randomNumbers=difficulty0Problems[randomInt];
        }
        else if (difficultyLevel==1) {
            Random r = new Random();
            randomInt = r.nextInt(difficulty1Problems.length);
            randomNumbers=difficulty1Problems[randomInt];
        }
        else if (difficultyLevel==2) {
            for (int i = 0; i < 4; i++) {
                Random r = new Random();
                randomNumbers[i] = r.nextInt(989) + 10;
            }
        }
//        randomNumbers = problems[randomNum];
        int[] tempNumbers = new int[4];
        tempNumbers = randomNumbers.clone();
        orderedNumbers = bubbleSort(tempNumbers);
        playGame(randomNumbers, orderedNumbers);
    }

    public int[] bubbleSort(int[] tempNum) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < tempNum.length - j; i++) {
                if (tempNum[i] > tempNum[i + 1]) {
                    tmp = tempNum[i];
                    tempNum[i] = tempNum[i + 1];
                    tempNum[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
        return tempNum;
    }

    public void playGame(int[] randomNumbers, int[] orderedNumbers) {
        // take the options array and display each number in a button at the bottom of the screen
        // views to drag
        sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
        sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
        sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
        sequenceView3 = (TextView) findViewById(R.id.sequenceView3);

        sequenceView0.setText(String.valueOf(randomNumbers[0]));
        sequenceView1.setText(String.valueOf(randomNumbers[1]));
        sequenceView2.setText(String.valueOf(randomNumbers[2]));
        sequenceView3.setText(String.valueOf(randomNumbers[3]));

        optionView0 = (TextView) findViewById(R.id.optionView0);
        optionView1 = (TextView) findViewById(R.id.optionView1);
        optionView2 = (TextView) findViewById(R.id.optionView2);
        optionView3 = (TextView) findViewById(R.id.optionView3);

        optionView0.setText(String.valueOf(orderedNumbers[0]));
        optionView1.setText(String.valueOf(orderedNumbers[1]));
        optionView2.setText(String.valueOf(orderedNumbers[2]));
        optionView3.setText(String.valueOf(orderedNumbers[3]));

        //set touch listeners
        sequenceView0.setOnTouchListener(new ChoiceTouchListener());
        sequenceView1.setOnTouchListener(new ChoiceTouchListener());
        sequenceView2.setOnTouchListener(new ChoiceTouchListener());
        sequenceView3.setOnTouchListener(new ChoiceTouchListener());

        //set drag listeners
        optionView0.setOnDragListener(new ChoiceDragListener());
        optionView1.setOnDragListener(new ChoiceDragListener());
        optionView2.setOnDragListener(new ChoiceDragListener());
        optionView3.setOnDragListener(new ChoiceDragListener());

        scoringQuestion = orderedNumbers[0] +","+ orderedNumbers[1] +","+ orderedNumbers[2] +","+ orderedNumbers[3];
    }

        // private final class
        class ChoiceTouchListener implements OnTouchListener {
            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    /*
                     * Drag details: we only need default behavior
                     * - clip data could be set to pass data as part of drag
                     * - shadow can be tailored
                     */
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    //start dragging the item touched
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                } else {
                    return false;
                }
            }
        }

        @SuppressLint("NewApi")
        private class ChoiceDragListener implements OnDragListener {

            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //no action necessary
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //no action necessary
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //no action necessary
                        break;
                    case DragEvent.ACTION_DROP:
                        //handle the dragged view being dropped over a drop view
                        View view = (View) event.getLocalState();
                        //view dragged item is being dropped on
                        TextView dropTarget = (TextView) v;
                        //view being dragged and dropped
                        TextView dropped = (TextView) view;
                        int number1 = Integer.valueOf(dropped.getText().toString());
                        int number2 = Integer.valueOf(dropTarget.getText().toString());
                        view.setVisibility(View.INVISIBLE);
                        //update the text in the target view to reflect the data being dropped
                        dropTarget.setText(dropped.getText().toString());
                        //make it bold to highlight the fact that an item has been dropped
                        dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                        dropTarget.setTextColor(0xffffffff);
                        dropTarget.setBackgroundResource(R.drawable.basket_1_peach_full);
                        //if an item has already been dropped here, there will be a tag
                        Object tag = dropTarget.getTag();
                        //if there is already an item here, set it back visible in its original place
                        if (tag != null) {
                        //the tag is the view id already dropped here
                            int existingID = (Integer) tag;
                            //set the original view visible again
                            findViewById(existingID).setVisibility(View.VISIBLE);
                        }
                        //set the tag in the target view being dropped on - to the ID of the view being dropped
                        dropTarget.setTag(dropped.getId());
                        //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                        dropTarget.setOnDragListener(null);
                        //checking whether they are equal
                        if (number1 == number2) {
                            numCorrect = numCorrect+1;
                        }
                        else {
                            //set the original view visible again
                            numWrong=numWrong+1;
                            String IdAsString = dropped.getResources().getResourceName(dropped.getId());
                            IdAsString=IdAsString.substring(IdAsString.lastIndexOf('/') + 1);
                            String IdAsStringTar = dropTarget.getResources().getResourceName(dropTarget.getId());
                            IdAsStringTar=IdAsStringTar.substring(IdAsString.lastIndexOf('/') + 1);
                            wrongAnswers.add(dropped);
                            wrongBaskets.add(dropTarget);
                        }
                        if(numWrong+numCorrect==4) {
                            checkAnswer();
                        }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }

            return true;
        }
    }

    // reset question
    public void reset() {
        wrongBaskets.clear();
        wrongAnswers.clear();
        sequenceView0.setVisibility(TextView.VISIBLE);
        sequenceView1.setVisibility(TextView.VISIBLE);
        sequenceView2.setVisibility(TextView.VISIBLE);
        sequenceView3.setVisibility(TextView.VISIBLE);

        optionView0.setTag(null);
        optionView1.setTag(null);
        optionView2.setTag(null);
        optionView3.setTag(null);

        optionView0.setTypeface(Typeface.DEFAULT);
        optionView1.setTypeface(Typeface.DEFAULT);
        optionView2.setTypeface(Typeface.DEFAULT);
        optionView3.setTypeface(Typeface.DEFAULT);

        // reset text color
        optionView0.setTextColor(0x01060014);
        optionView1.setTextColor(0x01060014);
        optionView2.setTextColor(0x01060014);
        optionView3.setTextColor(0x01060014);

        // reset images
        optionView0.setBackgroundResource(R.drawable.basket_1);
        optionView1.setBackgroundResource(R.drawable.basket_1);
        optionView2.setBackgroundResource(R.drawable.basket_1);
        optionView3.setBackgroundResource(R.drawable.basket_1);

        optionView0.setOnDragListener(new ChoiceDragListener());
        optionView1.setOnDragListener(new ChoiceDragListener());
        optionView2.setOnDragListener(new ChoiceDragListener());
        optionView3.setOnDragListener(new ChoiceDragListener());
        generateSequence();
    }

    public void checkAnswer() {
        scoringNumAttempts++;
        int checkTotal=wrongBaskets.size()+numCorrect;
        scoringSelectedAnswer = optionView0.getText().toString() +","+ optionView1.getText().toString() +","+ optionView2.getText().toString() +","+ optionView3.getText().toString()+",";
        scoringAnswers[0] = "reorder";
        scoringAnswers[1] = "reorder";
        scoringAnswers[2] = "reorder";

        if ((numCorrect!=4)&&(checkTotal==4)) {
            correctOnFirstTry = false;
            scoringCorrect = "incorrect";
            writeToScore("level2gameordering.txt");
            firstAttempt = false;
            numWrong=0;
            // set bag of apples to visible
            for (int i=0; i<wrongAnswers.size(); i++) {
                TextView a = wrongAnswers.get(i);
                a.setVisibility(TextView.VISIBLE);
            }
//            sequenceView0.setVisibility(TextView.VISIBLE);
            for (int i=0; i<wrongBaskets.size(); i++) {
                TextView b = wrongBaskets.get(i);
                b.setVisibility(TextView.VISIBLE);
                b.setTypeface(Typeface.DEFAULT);
                b.setTextColor(0x01060014);
                b.setTag(null);
                b.setBackgroundResource(R.drawable.basket_1);
                b.setOnDragListener(new ChoiceDragListener());
            }
            optionView0.setText(String.valueOf(orderedNumbers[0]));
            optionView1.setText(String.valueOf(orderedNumbers[1]));
            optionView2.setText(String.valueOf(orderedNumbers[2]));
            optionView3.setText(String.valueOf(orderedNumbers[3]));
            wrongBaskets.clear();
            wrongAnswers.clear();
        }
        else if (numCorrect==4) {
            scoringCorrect = "correct";
            if(correctOnFirstTry==true) {
                score++;
                String score_name = "star" + score;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            writeToScore("level2gameordering.txt");
            if(firstAttempt) {
                numAnswersCorrect++;
            }
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (score == 10) {
                        thisUser.activityProgress[4] = true;
                        if (difficultyLevel<2) {
                            difficultyLevel++;
                            score=0;
                            String score_name = "star" + score;
                            int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                            ImageView tv = (ImageView) findViewById(R.id.score);
                            tv.setImageResource(score_id);
                            reset();
                        }
                        else if (difficultyLevel==2) {
                            onBackPressed();
                        }
                    } else {
                        reset();
                    }
                }
            }, 2050);
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