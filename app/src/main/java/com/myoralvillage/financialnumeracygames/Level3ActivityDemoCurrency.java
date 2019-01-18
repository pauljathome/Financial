package com.myoralvillage.financialnumeracygames;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Paul on 10/19/2016
 *
 * This is a generic class implementing all of the currency games demo code
 *
 * Most of the game is currency neutral, the small part that isn't is set in a function at
 * the end
 *
 */

public abstract class Level3ActivityDemoCurrency extends CurrencyActivityGame {

   /*
     * These are for the demo code only
     *
     * The item that was bought, the amount paid for it, etc all vary by currency and country
     */

    int item_bought; // The id for the image of the item bought
    int first_bill; // The index for the image of the first bill received as change
    int second_bill; // The index for the image of the second bill received as change

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateGameSpecific();
        runDemo();
    }

    /*
     * Actual code to run the demo.
     *
     * Still code duplication, worry about that later.
     */

    void runDemo()
    {

        /*
         * So kinda vaguely working.
         *
         * Finger not going to the right place
         *
         * But making progress :-)
         *
         * But bored for now. Work on it later.
         */

        System.out.println("In Start Demo");
        /*
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout1);
        final ImageView finger1 = (ImageView) findViewById(R.id.finger1);
        */
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
        params.leftMargin = 30;
        params.topMargin = 350;
        /*
        rl.removeView(finger1);
        //  rl.addView(finger1, params);
        */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int maxX = size.x;
        int maxY = size.y;

        System.out.println(maxX + "and " + maxY);


        /*
        int x = finger1.getLeft();
        int y = finger1.getTop();
        */

        /**final ImageView finger3 = (ImageView) findViewById(R.id.finger3);
         final ImageView finger4 = (ImageView) findViewById(R.id.finger4); **/


        final TextView cashView = (TextView) findViewById(R.id.cashView);
        cashView.setText(String.format(locale, format_string, 0f));
        final ImageView item = (ImageView) findViewById(R.id.item);
        item.setImageResource(item_bought);

        // TODO Disable and enable cash listeners
        // first animation/drag action
        final AnimationSet firstAnimationSet = new AnimationSet(true);

        TranslateAnimation animation = new TranslateAnimation(-10, 70,
                5, 41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(3000);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        firstAnimationSet.addAnimation(animation);

        TranslateAnimation animation1 = new TranslateAnimation(5, 180,
                0, -150);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation1.setDuration(3000);  // animation duration
        animation1.setRepeatCount(0);  // animation repeat count
        animation1.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        animation.setFillEnabled(true);


        firstAnimationSet.addAnimation(animation1);

        //  finger1.startAnimation(firstAnimationSet);
        cash_units[first_bill].bill.startAnimation(firstAnimationSet);
        firstAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                //finger1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                // Just adding a comment to see

                //finger1.setVisibility(View.INVISIBLE);

                // How does snap work?
                cash_units[first_bill].snap.setBackgroundResource(cash_units[first_bill].drawable_id);
                cashView.setText(String.format(locale, format_string, cash_units[first_bill].value));

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //final ImageView fingerX = (ImageView) findViewById(R.id.finger1);
                final ImageView imagefinger = new ImageView(Level3ActivityDemoCurrency.this);
                imagefinger.setBackgroundResource(R.drawable.finger);
                //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(30, 50);
                params2.leftMargin = 400;
                params2.topMargin = 350;
                //   rl.addView(imagefinger, params2);


                // first animation/drag action
                final AnimationSet secondAnimationSet = new AnimationSet(true);

                TranslateAnimation animation2 = new TranslateAnimation(-10, -10,
                        5, 42);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation2.setStartOffset(500);
                animation2.setDuration(3000);  // animation duration
                animation2.setRepeatCount(0);  // animation repeat count
                animation2.setRepeatMode(1);   // repeat animation (left to right, right to left )
                //      animation.setFillAfter(true);

                //img_animation.startAnimation(animation);  // start animation
                secondAnimationSet.addAnimation(animation2);

                TranslateAnimation animation22 = new TranslateAnimation(5, 5,
                        0, -150);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation22.setStartOffset(500);
                animation22.setDuration(3000);  // animation duration
                animation22.setRepeatCount(0);  // animation repeat count
                animation22.setRepeatMode(1);   // repeat animation (left to right, right to left )
                animation22.setFillAfter(true);
                animation22.setFillEnabled(true);

                secondAnimationSet.addAnimation(animation22);
                imagefinger.startAnimation(secondAnimationSet);
                cash_units[second_bill].bill.startAnimation(secondAnimationSet);
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
                        cash_units[second_bill].snap.setBackgroundResource(cash_units[second_bill].drawable_id);
                        imagefinger.setVisibility(View.INVISIBLE);
                        cashView.setText(String.format(locale, format_string, cash_units[first_bill].value + cash_units[second_bill].value));
                    }
                });
            }
        }, 3400);

    }

    public void advanceGame(View v) {
        finish();
    }

    abstract void onCreateGameSpecific();   // This holds the ONLY changes between the
                                                // demos for exact change and PV
}
