package com.myoralvillage.financialnumeracygames;

import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;

public class Level2ActivityDemoOrdering extends GenericActivityGame {
    private ImageButton mSkip;
    public TextView sequenceView0, sequenceView1, sequenceView2, sequenceView3, optionView0, optionView1, optionView2, optionView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_demoordering);
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
        startDemo();
    }

    public void startDemo(){
        mSkip = (ImageButton) findViewById(R.id.skip_button);
        final ImageView finger1 = (ImageView) findViewById(R.id.finger1);
        final ImageView finger3 = (ImageView) findViewById(R.id.finger3);
        final ImageView finger4 = (ImageView) findViewById(R.id.finger4);
        final ImageView finger2 = (ImageView) findViewById(R.id.finger2);

        //final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams)finger1.getLayoutParams();
        //lp.leftMargin = 0;
        //lp.topMargin = 200;
        //lp.rightMargin = 10;

        sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
        sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
        sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
        sequenceView3 = (TextView) findViewById(R.id.sequenceView3);

        optionView0 = (TextView) findViewById(R.id.optionView0);

        optionView1 = (TextView) findViewById(R.id.optionView1);
        optionView2 = (TextView) findViewById(R.id.optionView2);
        optionView3 = (TextView) findViewById(R.id.optionView3);

        sequenceView0.setText(String.valueOf(180));
        sequenceView1.setText(String.valueOf(997));
        sequenceView2.setText(String.valueOf(302));
        sequenceView3.setText(String.valueOf(745));


        finger2.setVisibility(View.INVISIBLE);
        finger3.setVisibility(View.INVISIBLE);
        finger4.setVisibility(View.INVISIBLE);

        // first animation/drag action
        final AnimationSet firstAnimationSet = new AnimationSet(true);

        TranslateAnimation animation = new TranslateAnimation(-10, -70,
                5, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(3000);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        firstAnimationSet.addAnimation(animation);

        final TextView img_animation1 = (TextView) findViewById(R.id.sequenceView0);

        TranslateAnimation animation1 = new TranslateAnimation(5, -70,
                0, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation1.setDuration(3000);  // animation duration
        animation1.setRepeatCount(0);  // animation repeat count
        animation1.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        animation.setFillEnabled(true);


        firstAnimationSet.addAnimation(animation1);
        finger1.startAnimation(firstAnimationSet);
        img_animation1.startAnimation(firstAnimationSet);
        firstAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                optionView0.setText(String.valueOf(180));
                optionView0.setBackgroundResource(R.drawable.basket_1_peach_full);
                sequenceView0.setVisibility(View.INVISIBLE);

                finger1.setVisibility(View.INVISIBLE);
            }
        });



        finger3.setVisibility(View.VISIBLE);
        final AnimationSet secondAnimationSet = new AnimationSet(true);

        TranslateAnimation animation2 = new TranslateAnimation(30, -70,
                5, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation2.setStartOffset(3100);
        animation2.setDuration(3000);  // animation duration
        animation2.setRepeatCount(0);  // animation repeat count
        animation2.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        secondAnimationSet.addAnimation(animation2);

        //finger3.startAnimation(secondAnimationSet);

        final TextView img_animation2 = (TextView) findViewById(R.id.sequenceView2);

        TranslateAnimation animationTwo = new TranslateAnimation(5, -70,
                0, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animationTwo.setStartOffset(3100);
        animationTwo.setDuration(3000);  // animation duration
        animationTwo.setRepeatCount(0);  // animation repeat count
        animationTwo.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animationTwo.setFillAfter(true);
        animationTwo.setFillEnabled(true);

        //img_animation2.startAnimation(animationTwo);
        secondAnimationSet.addAnimation(animationTwo);

        finger3.startAnimation(secondAnimationSet);
        img_animation2.startAnimation(secondAnimationSet);

        secondAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                optionView1.setText(String.valueOf(302));
                optionView1.setBackgroundResource(R.drawable.basket_1_peach_full);
                sequenceView2.setVisibility(View.INVISIBLE);

                finger3.setVisibility(View.INVISIBLE);
                //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) img_animation.getLayoutParams();
                //mlp.setMargins(110,22,0,0);//all in pixels
                //img_animation.setLayoutParams(mlp);
                //lp.leftMargin = 400;
                //lp.leftMargin = 400;
            }
        });




        finger4.setVisibility(View.VISIBLE);
        final AnimationSet thirdAnimationSet = new AnimationSet(true);

        TranslateAnimation animation3 = new TranslateAnimation(30, -30,
                5, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation3.setStartOffset(6200);
        animation3.setDuration(3000);  // animation duration
        animation3.setRepeatCount(0);  // animation repeat count
        animation3.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        thirdAnimationSet.addAnimation(animation3);

        //finger3.startAnimation(secondAnimationSet);

        final TextView img_animation3 = (TextView) findViewById(R.id.sequenceView3);

        TranslateAnimation animationThree = new TranslateAnimation(5, -30,
                0, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animationThree.setStartOffset(6200);
        animationThree.setDuration(3000);  // animation duration
        animationThree.setRepeatCount(0);  // animation repeat count
        animationThree.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animationThree.setFillAfter(true);
        animationThree.setFillEnabled(true);

        //img_animation2.startAnimation(animationTwo);
        thirdAnimationSet.addAnimation(animationThree);

        finger4.startAnimation(thirdAnimationSet);
        img_animation3.startAnimation(thirdAnimationSet);

        thirdAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                optionView2.setText(String.valueOf(745));
                optionView2.setBackgroundResource(R.drawable.basket_1_peach_full);
                sequenceView3.setVisibility(View.INVISIBLE);

                finger4.setVisibility(View.INVISIBLE);
                //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) img_animation.getLayoutParams();
                //mlp.setMargins(110,22,0,0);//all in pixels
                //img_animation.setLayoutParams(mlp);
                //lp.leftMargin = 400;
                //lp.leftMargin = 400;
            }
        });





        finger2.setVisibility(View.VISIBLE);
        final AnimationSet fourthAnimationSet = new AnimationSet(true);

        TranslateAnimation animation4 = new TranslateAnimation(30, 120,
                5, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation4.setStartOffset(9300);
        animation4.setDuration(3000);  // animation duration
        animation4.setRepeatCount(0);  // animation repeat count
        animation4.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        fourthAnimationSet.addAnimation(animation4);

        //finger3.startAnimation(secondAnimationSet);

        final TextView img_animation4 = (TextView) findViewById(R.id.sequenceView1);

        TranslateAnimation animationFour = new TranslateAnimation(5, 120,
                0, -41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animationFour.setStartOffset(9300);
        animationFour.setDuration(3000);  // animation duration
        animationFour.setRepeatCount(0);  // animation repeat count
        animationFour.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animationFour.setFillAfter(true);
        animationFour.setFillEnabled(true);

        //img_animation2.startAnimation(animationTwo);
        fourthAnimationSet.addAnimation(animationFour);

        finger2.startAnimation(fourthAnimationSet);
        img_animation4.startAnimation(fourthAnimationSet);

        fourthAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                optionView3.setText(String.valueOf(997));
                optionView3.setBackgroundResource(R.drawable.basket_1_peach_full);
                sequenceView1.setVisibility(View.INVISIBLE);

                finger2.setVisibility(View.INVISIBLE);
                //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) img_animation.getLayoutParams();
                //mlp.setMargins(110,22,0,0);//all in pixels
                //img_animation.setLayoutParams(mlp);
                //lp.leftMargin = 400;
                //lp.leftMargin = 400;
                finish();
            }
        });
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void exitDemo(View v) {
        finish();
    }
}

