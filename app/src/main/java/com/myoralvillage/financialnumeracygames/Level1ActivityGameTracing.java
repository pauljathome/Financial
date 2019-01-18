package com.myoralvillage.financialnumeracygames;

        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Environment;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.os.Handler;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.util.Random;

public class Level1ActivityGameTracing extends GenericActivityGame {
    private PaintView myView;
    private ImageView hImageViewPic;
    int[] images = {R.drawable.game1_tracing_0, R.drawable.game1_tracing_1, R.drawable.game1_tracing_2, R.drawable.game1_tracing_3, R.drawable.game1_tracing_4, R.drawable.game1_tracing_5, R.drawable.game1_tracing_6, R.drawable.game1_tracing_7, R.drawable.game1_tracing_8, R.drawable.game1_tracing_9};
    public int numCorrect = 0;
    public int questionNumber = 0;
    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gametracing);

        Intent intent = getIntent();
        getExtras(intent);

        startGame();
    }

    public void startGame() {
        myView = (PaintView) findViewById(R.id.custView);
        hImageViewPic = (ImageView) findViewById(R.id.image_view);

        //final Random rand = new Random();
        //int randNum = rand.nextInt(10);

        //hImageViewPic.setImageResource(images[randNum]);
        //myView.setNumber(randNum);

        hImageViewPic.setImageResource(images[questionNumber]);
        myView.setNumber(questionNumber);
        questionNumber++;
        numCorrect++;
    }

    public void newNumber(View v) {
        if (myView.completedNumber == true) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            //final Random rand = new Random();
            //int randNum = rand.nextInt(10);
            if (numCorrect == 10) {
                thisUser.activityProgress[1] = true;
                onBackPressed();
            }
            String score_name = "star" + numCorrect;
            int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
            ImageView tv = (ImageView) findViewById(R.id.score);
            tv.setImageResource(score_id);
            if (questionNumber != 10) {
                hImageViewPic.setImageResource(images[questionNumber]);
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);
            }
            myView.setNumber(questionNumber);
            questionNumber++;
            numCorrect++;
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
