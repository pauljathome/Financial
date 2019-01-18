package com.myoralvillage.financialnumeracygames;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Level2ActivityDemoFillInTheBlanks extends AppCompatActivity {

   public int demoNumber = 0;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_demofillintheblanks);
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
        startDemo();
    }

    public void startDemo() {
            TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
            sequenceView0.setText("50");

            TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
            sequenceView1.setText("52");

            TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
            sequenceView2.setText("54");

            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            sequenceView3.setText("_");


        Button optionView0 = (Button) findViewById(R.id.optionView0);
        Button optionView1 = (Button) findViewById(R.id.optionView1);
        Button optionView2 = (Button) findViewById(R.id.optionView2);
        optionView0.setText(String.valueOf(56));
        optionView1.setText(String.valueOf(59));
        optionView2.setText(String.valueOf(58));


    }

    public void exitDemo(View v) {
        finish();
    }

    public void nextArrow(View v){
        demoNumber++;
        demo(demoNumber, v);
    }

    public void demo(int demoNumber, View v){
        if (demoNumber==1){
            //show highlights
        }

        if (demoNumber==2){
            Button optionView0 = (Button) findViewById(R.id.optionView0);
            Button optionView1 = (Button) findViewById(R.id.optionView1);
            Button optionView2 = (Button) findViewById(R.id.optionView2);
            optionView1.setAlpha(0.5f);
            optionView2.setAlpha(0.5f);
        }

        if (demoNumber==3){
            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            sequenceView3.setText("56");
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            Button optionView0 = (Button) findViewById(R.id.optionView0);
            optionView0.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            //show final highlight
        }

        if (demoNumber == 4) {
            TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
            sequenceView0.setText("100");

            TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
            sequenceView1.setText("105");

            TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
            sequenceView2.setText("110");

            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            sequenceView3.setText("_");

            Button optionView0 = (Button) findViewById(R.id.optionView0);
            optionView0.setText(String.valueOf(108));
            optionView0.setClickable(false);
            Button optionView1 = (Button) findViewById(R.id.optionView1);
            optionView1.setText(String.valueOf(115));
            optionView1.setClickable(false);
            Button optionView2 = (Button) findViewById(R.id.optionView2);
            optionView2.setText(String.valueOf(125));
            optionView2.setClickable(false);
            optionView0.setAlpha(1f);
            optionView1.setAlpha(1f);
            optionView2.setAlpha(1f);
        }

        if (demoNumber==5){
            //show highlights
        }

        if (demoNumber==6){
            Button optionView0 = (Button) findViewById(R.id.optionView0);
            Button optionView1 = (Button) findViewById(R.id.optionView1);
            Button optionView2 = (Button) findViewById(R.id.optionView2);
            optionView0.setAlpha(0.5f);
            optionView2.setAlpha(0.5f);
        }

        if (demoNumber==7){
            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            sequenceView3.setText("115");
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            Button optionView1 = (Button) findViewById(R.id.optionView1);
            optionView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            //show final highlight
        }

        if (demoNumber==8){
            finish();
        }
    }

    public void skipDemo(View v){
        finish();
    }

}
