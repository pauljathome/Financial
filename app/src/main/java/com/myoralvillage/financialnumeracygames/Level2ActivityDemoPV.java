package com.myoralvillage.financialnumeracygames;

import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Level2ActivityDemoPV extends AppCompatActivity {
    int demoStage = 0;
    int demoType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_demopv);
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(),"fonts/TanzaFont.ttf");
        TextView myTextView = (TextView)findViewById(R.id.tv_answer);
        myTextView.setTypeface(myTypeFace);
        startDemo();
    }

    public void startDemo() {

    }

    public void nextNumber(View v) {
        demoStage++;
        if(demoType==0) {
            if (demoStage==3) {
                demoType++;
                nextNumberType2();
            } else {
                nextNumberType1();

                if(demoStage!=0) {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.VISIBLE);
                } else {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.INVISIBLE);
                }
            }
        } else if (demoType == 1) {
            nextNumberType2();
        }
    }

    public void exitDemo(View v) {
        finish();
    }

    public void nextNumberType2() {

        if(demoStage==9) {
            finish();
        } else if(demoStage==3) {
            String repFileName = "game2_ones";
            String numeral = "3";
            setThreePVs(repFileName,numeral);
        } else if(demoStage==4) {
            String repFileName = "game2_tens";
            String numeral = "30";
            setThreePVs(repFileName,numeral);
        } else if(demoStage==5) {
            String repFileName = "game2_hundreds";
            String numeral = "300";
            setThreePVs(repFileName,numeral);
        } else if(demoStage==6) {
            LinearLayout ll1 = (LinearLayout) findViewById(R.id.rep1);
            LinearLayout ll2 = (LinearLayout) findViewById(R.id.rep2);
            LinearLayout ll3 = (LinearLayout) findViewById(R.id.rep3);
            LinearLayout.LayoutParams ll1Params = (LinearLayout.LayoutParams) ll1.getLayoutParams();
            LinearLayout.LayoutParams ll2Params = (LinearLayout.LayoutParams) ll2.getLayoutParams();
            LinearLayout.LayoutParams ll3Params = (LinearLayout.LayoutParams) ll3.getLayoutParams();
            ll1Params.weight=1;
            ll2Params.weight=1;
            ll3Params.weight=1;
            ll1.setLayoutParams(ll1Params);
            ll2.setLayoutParams(ll2Params);
            ll3.setLayoutParams(ll3Params);
            setHundreds(2);
            setTens(3);
            setOnes(0);
            setNumber("230");
            pulseImage(2,3,0);
        } else if(demoStage==7) {
            setHundreds(3);
            setTens(0);
            setOnes(2);
            setNumber("302");
            pulseImage(3,0,2);
        } else if(demoStage==8) {
            setHundreds(2);
            setTens(2);
            setOnes(2);
            setNumber("222");
            pulseImage(2,2,2);
        }
    }

    public void setNumber(String num) {
        TextView tv = (TextView) findViewById(R.id.tv_answer);
        tv.setText(num);
    }

    public void setHundreds(int a) {
        int img_id_representation = getResources().getIdentifier("game2_hundreds", "drawable", getPackageName());

        ImageView iv1a = (ImageView) findViewById(R.id.img_representation1a);
        ImageView iv1b = (ImageView) findViewById(R.id.img_representation1b);
        ImageView iv1c = (ImageView) findViewById(R.id.img_representation1c);

        iv1a.setVisibility(View.INVISIBLE);
        iv1b.setVisibility(View.INVISIBLE);
        iv1c.setVisibility(View.INVISIBLE);

        ImageView[] imageViews = {iv1a,iv1b,iv1c};

        for(int i = 0; i < a; i++) {
            imageViews[i].setImageResource(img_id_representation);
            imageViews[i].setVisibility(View.VISIBLE);
        }
    }

    public void setTens(int a) {
        int img_id_representation = getResources().getIdentifier("game2_tens", "drawable", getPackageName());

        ImageView iv2a = (ImageView) findViewById(R.id.img_representation2a);
        ImageView iv2b = (ImageView) findViewById(R.id.img_representation2b);
        ImageView iv2c = (ImageView) findViewById(R.id.img_representation2c);

        iv2a.setVisibility(View.INVISIBLE);
        iv2b.setVisibility(View.INVISIBLE);
        iv2c.setVisibility(View.INVISIBLE);

        ImageView[] imageViews = {iv2a,iv2b,iv2c};

        for(int i = 0; i < a; i++) {
            imageViews[i].setImageResource(img_id_representation);
            imageViews[i].setVisibility(View.VISIBLE);
        }
    }

    public void setOnes(int a) {
        int img_id_representation = getResources().getIdentifier("game2_ones", "drawable", getPackageName());

        ImageView iv3a = (ImageView) findViewById(R.id.img_representation3a);
        ImageView iv3b = (ImageView) findViewById(R.id.img_representation3b);
        ImageView iv3c = (ImageView) findViewById(R.id.img_representation3c);

        iv3a.setVisibility(View.INVISIBLE);
        iv3b.setVisibility(View.INVISIBLE);
        iv3c.setVisibility(View.INVISIBLE);

        ImageView[] imageViews = {iv3a,iv3b,iv3c};

        for(int i = 0; i < a; i++) {
            imageViews[i].setImageResource(img_id_representation);
            imageViews[i].setVisibility(View.VISIBLE);
        }
    }

    public void setThreePVs(String repFileName, String numeral) {
        int img_id_representation = getResources().getIdentifier(repFileName, "drawable", getPackageName());

        final ImageView iv2a = (ImageView) findViewById(R.id.img_representation2a);
        final ImageView iv2b = (ImageView) findViewById(R.id.img_representation2b);
        final ImageView iv2c = (ImageView) findViewById(R.id.img_representation2c);
        TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

        iv2a.setImageResource(img_id_representation);
        iv2b.setImageResource(img_id_representation);
        iv2c.setImageResource(img_id_representation);

        iv2a.setVisibility(View.VISIBLE);
        iv2b.setVisibility(View.VISIBLE);
        iv2c.setVisibility(View.VISIBLE);

        final ImageView ivPrev = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
        final ImageView ivNext = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_next);

        ivPrev.setClickable(false);
        ivNext.setClickable(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv2a.startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv2b.startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
            }
        }, 1500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv2c.startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
            }
        }, 2500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivPrev.setClickable(true);
                ivNext.setClickable(true);
            }
        }, 3000);

        tvNumber.setText(numeral);
    }

    public void nextNumberType1() {
        String representationFileName;
        String numeral;
        if(demoStage==1) {
            representationFileName = "game2_tens";
            numeral="10";
        } else if (demoStage == 2) {
            representationFileName = "game2_hundreds";
            numeral="100";
        } else {
            representationFileName = "game2_ones";
            numeral="1";
        }

        int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation2b);
        TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

        ivRepresentation.setImageResource(img_id_representation);
        tvNumber.setText(numeral);
    }

    public void prevNumber(View v) {
        if (demoStage==1) {
            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.INVISIBLE);
        } else {

            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.VISIBLE);
        }
        demoStage--;
        if(demoStage==2) {
            demoType--;
            final ImageView iv2a = (ImageView) findViewById(R.id.img_representation2a);
            final ImageView iv2c = (ImageView) findViewById(R.id.img_representation2c);

            iv2a.setVisibility(View.INVISIBLE);
            iv2c.setVisibility(View.INVISIBLE);

            prevNumberType1();
        }
        if(demoType==0){
            prevNumberType1();
        } else {
            prevNumberType2();
        }
    }

    public void prevNumberType2() {
        if(demoStage==3) {
            String repFileName = "game2_ones";
            String numeral = "3";
            setThreePVs(repFileName, numeral);
        } else if(demoStage==4) {
            String repFileName = "game2_tens";
            String numeral = "30";
            setThreePVs(repFileName,numeral);
        } else if(demoStage==5) {
            LinearLayout ll1 = (LinearLayout) findViewById(R.id.rep1);
            LinearLayout ll2 = (LinearLayout) findViewById(R.id.rep2);
            LinearLayout ll3 = (LinearLayout) findViewById(R.id.rep3);
            LinearLayout.LayoutParams ll1Params = (LinearLayout.LayoutParams) ll1.getLayoutParams();
            LinearLayout.LayoutParams ll2Params = (LinearLayout.LayoutParams) ll2.getLayoutParams();
            LinearLayout.LayoutParams ll3Params = (LinearLayout.LayoutParams) ll3.getLayoutParams();
            ll1Params.weight=0;
            ll2Params.weight=1;
            ll3Params.weight=0;
            ll1.setLayoutParams(ll1Params);
            ll2.setLayoutParams(ll2Params);
            ll3.setLayoutParams(ll3Params);

            String repFileName = "game2_hundreds";
            String numeral = "300";
            setThreePVs(repFileName,numeral);
        } else if(demoStage==6) {
            LinearLayout ll1 = (LinearLayout) findViewById(R.id.rep1);
            LinearLayout ll2 = (LinearLayout) findViewById(R.id.rep2);
            LinearLayout ll3 = (LinearLayout) findViewById(R.id.rep3);
            LinearLayout.LayoutParams ll1Params = (LinearLayout.LayoutParams) ll1.getLayoutParams();
            LinearLayout.LayoutParams ll2Params = (LinearLayout.LayoutParams) ll2.getLayoutParams();
            LinearLayout.LayoutParams ll3Params = (LinearLayout.LayoutParams) ll3.getLayoutParams();
            ll1Params.weight=1;
            ll2Params.weight=1;
            ll3Params.weight=1;
            ll1.setLayoutParams(ll1Params);
            ll2.setLayoutParams(ll2Params);
            ll3.setLayoutParams(ll3Params);
            setHundreds(3);
            setTens(2);
            setOnes(1);
            setNumber("321");
            pulseImage(3,2,1);
        }
    }

    public void pulseImage(final int a, final int b, final int c) {
        final ImageView iv1a = (ImageView) findViewById(R.id.img_representation1a);
        final ImageView iv1b = (ImageView) findViewById(R.id.img_representation1b);
        final ImageView iv1c = (ImageView) findViewById(R.id.img_representation1c);
        final ImageView iv2a = (ImageView) findViewById(R.id.img_representation2a);
        final ImageView iv2b = (ImageView) findViewById(R.id.img_representation2b);
        final ImageView iv2c = (ImageView) findViewById(R.id.img_representation2c);
        final ImageView iv3a = (ImageView) findViewById(R.id.img_representation3a);
        final ImageView iv3b = (ImageView) findViewById(R.id.img_representation3b);
        final ImageView iv3c = (ImageView) findViewById(R.id.img_representation3c);

        final ImageView ivPrev = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
        final ImageView ivNext = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_next);

        ivPrev.setClickable(false);
        ivNext.setClickable(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView[] images = {iv1a,iv1b,iv1c};
                for (int i = 0; i < a; i++) {
                    images[i].startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
                }
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView[] images = {iv2a,iv2b,iv2c};
                for (int i = 0; i < b; i++) {
                    images[i].startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
                }
            }
        }, 1500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView[] images = {iv3a,iv3b,iv3c};
                for (int i = 0; i < c; i++) {
                    images[i].startAnimation(AnimationUtils.loadAnimation(Level2ActivityDemoPV.this, R.anim.game1_qa_positive_click));
                }
            }
        }, 2500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivPrev.setClickable(true);
                ivNext.setClickable(true);
            }
        }, 3000);
    }

    public void prevNumberType1() {
        String representationFileName;
        String numeral;
        if(demoStage==1) {
            representationFileName = "game2_tens";
            numeral = "10";
        } else if (demoStage == 2) {
            representationFileName = "game2_hundreds";
            numeral = "100";
        } else {
            representationFileName = "game2_ones";
            numeral = "1";
        }

        int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation2b);
        TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

        tvNumber.setText(numeral);
        ivRepresentation.setImageResource(img_id_representation);
    }
}
