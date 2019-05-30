package com.example.learnmaths;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {

    private SharedPreferences levelChoice;
    private SharedPreferences backgroundChoice;

    private MediaPlayer mediaPlayer;
    private MediaPlayer correctTone;
    private MediaPlayer wrongTone;
    private TextView txtTimeCount;
    private Random r = new Random();

    private boolean isClicked = false;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Screen Size
    protected static int screenWidth;
    protected static int screenHeight;

    //Bubble Size
    private int img1W;
    private int img1H;
    private int img2W;
    private int img2H;
    private int img3W;
    private int img3H;
    private int img4W;
    private int img4H;
    private int img5W;
    private int img5H;


    private int clickCount = 0;

    //Winning Status
    private boolean isGameFinish = false;
    private boolean isGameFinalFinish = false;
    private boolean isFirstWon = false;
    private boolean isSecondWon = false;
    private boolean isThirdWon = false;


    //Place status;
    private boolean isFirstPlaceBlank = true;
    private boolean isSecondPlaceBlank = true;
    private boolean isThirdPlaceBlank = true;
    private boolean isFourthPlaceBlank = true;

    //Bubble
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    //Blank space to place bubble
    private TextView place1;
    private TextView place2;
    private TextView place3;
    private TextView place4;
    private TextView place5;

    //Score
    private TextView score;

    private TextView[] object = new TextView[5];
    private int[] sortAry = new int[5];
    private int temp;
    private int num1, num2;


    //Pause Button
    private ImageView btnPause;

    //Size

    //Position
    private float img1PosX;
    private float img1PosY;
    private float img2PosX;
    private float img2PosY;
    private float img3PosX;
    private float img3PosY;
    private float img4PosX;
    private float img4PosY;
    private float img5PosX;
    private float img5PosY;

    //Move Direction //nextInt((max - min) + 1) + min;
    private float img1DirX = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img1DirY = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img2DirX = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img2DirY = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img3DirX = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img3DirY = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img4DirX = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img4DirY = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img5DirX = (float) r.nextInt((10 - 5) + 1) + 5;
    private float img5DirY = (float) r.nextInt((10 - 5) + 1) + 5;


    private String[] easy = {"1", "47", "2", "40", "3", "36", "4", "35", "5", "30", "6", "24", "7", "23", "8", "20", "9", "12", "10", "11"}; // total = 20
    private String[] medium = {"12", "63-14", "20", "1+25", "23", "33+12", "24", "50-21", "30", "20-3", "35", "9+4", "36", "10+4", "40", "11+5", "47", "12+3", "48", "50"}; //total = 20
    private String[] hard = {"29+14", "19*6-89", "79-23", "35", "17+15", "44-6*3", "38-26", "109", "43+37", "2*14+23", "54-17", "76", "28+16", "230-17*13", "63-14", "93", "75-28", "45+12-9", "12+14", "53+3-26"}; //total = 20


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        //bubble view
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);

        object[0] = textView1;
        object[1] = textView2;
        object[2] = textView3;
        object[3] = textView4;
        object[4] = textView5;

        //blank view
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place3 = findViewById(R.id.place3);
        place4 = findViewById(R.id.place4);
        place5 = findViewById(R.id.place5);

        //get Images Width and Height
        img1W = textView1.getLayoutParams().width;
        img1H = textView1.getLayoutParams().height;
        img2W = textView2.getLayoutParams().width;
        img2H = textView2.getLayoutParams().height;
        img3W = textView3.getLayoutParams().width;
        img3H = textView3.getLayoutParams().height;
        img4W = textView4.getLayoutParams().width;
        img4H = textView4.getLayoutParams().height;
        img5W = textView5.getLayoutParams().width;
        img5H = textView5.getLayoutParams().height;

        btnPause = findViewById(R.id.pause_image);
        score = findViewById(R.id.score);

        //Get Screen Size.
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        System.out.println("Width" + Integer.toString(screenWidth));
        System.out.println("Height" + Integer.toString(screenHeight));

        if(changeBackground().equals("Bubble"))
        {
            for (TextView textView : object) {
                // object[i].setBackgroundResource(R.drawable.bubble);
                textView.setBackground(getDrawable(R.drawable.bubble));


            }
        }
        else if(changeBackground().equals("Basketball"))
        {
            //object[i].setBackgroundResource(R.drawable.basketball_background);
            for (TextView textView : object)
                textView.setBackground(getDrawable(R.drawable.basketball_background));
        }
        if(setLevel().equals("Easy"))
        {
            textView1.setText(easy[0]);
            textView2.setText(easy[1]);
            textView3.setText(easy[2]);
            textView4.setText(easy[3]);
            textView5.setText(easy[4]);
            System.out.println("Text View");
            System.out.println(textView1.getText().toString());
            System.out.println(textView2.getText().toString());
            System.out.println(textView3.getText().toString());
            System.out.println(textView4.getText().toString());
            System.out.println(textView5.getText().toString());

            object[0] = textView1;
            object[1] = textView2;
            object[2] = textView3;
            object[3] = textView4;
            object[4] = textView5;

            //Display object Ary
            System.out.println("Print Object Array");
            for(int i=0 ; i<object.length ;i++)
            {
                System.out.print(object[i].getText().toString()+"\t");
                sortAry[i] = Integer.parseInt(object[i].getText().toString());
            }
            //Sort the number of Text View in Ascending Order
            for (int i = 0; i < sortAry.length; i++)
            {
                for (int j = i + 1; j < sortAry.length; j++)
                {
                    //  System.out.println(object[i].getText().toString()+"\t");

                    if (sortAry[i] > sortAry[j])
                    {
                        temp = sortAry[i];
                        sortAry[i] = sortAry[j];
                        sortAry[j] = temp;


                    }
                }

            }


            //Display Sorted Array Object
            System.out.println("Sorted Array: ");
            for(int i=0 ; i<sortAry.length ; i++)
            {
                System.out.print(sortAry[i]+"\t");
            }
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    textView1.setVisibility(TextView.INVISIBLE);
                    System.out.println("Text View 1 is clicked");
                    checkBlank(textView1.getText().toString());

                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    textView2.setVisibility(TextView.INVISIBLE);
                    checkBlank(textView2.getText().toString());
                    System.out.println("Text View 2 is clicked");
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    textView3.setVisibility(TextView.INVISIBLE);
                    checkBlank(textView3.getText().toString());
                    System.out.println("Text View 3 is clicked");
                }
            });
            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    textView4.setVisibility(TextView.INVISIBLE);
                    checkBlank(textView4.getText().toString());
                    System.out.println("Text View 4 is clicked");
                }
            });
            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = true;
                    textView5.setVisibility(TextView.INVISIBLE);
                    checkBlank(textView5.getText().toString());
                    System.out.println("Text View 5 is clicked");

                }
            });


        }
        else if(setLevel().equals("Medium"))
        {
            textView1.setText(medium[0]);
            textView2.setText(medium[1]);
            textView3.setText(medium[2]);
            textView4.setText(medium[3]);
            textView5.setText(medium[4]);

            object[0] = textView1;
            object[1] = textView2;
            object[2] = textView3;
            object[3] = textView4;
            object[4] = textView5;
            playMediumGame();
        }
        else if(setLevel().equals("Hard"))
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=0 ; j<5 ; j++)
                {
                    object[i].setText(hard[j].toString());
                }
            }
            playHardGame();

        }


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder message = new AlertDialog.Builder(GameActivity.this);
                message.setMessage("Paused").setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentMain = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intentMain);
                    }
                }).setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                }).show();
                timer.cancel();
            }
        });



        //Start the timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos1();
                        changePos2();
                        changePos3();
                        changePos4();
                        changePos5();





                    }
                });
            }
        }, 0, 20);

        txtTimeCount = findViewById(R.id.time_count);

        mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.main_song);


//        String soundStatus = mPreferences.getString(getString(R.string.soundStatus), "On");
//        if(soundStatus == "On")
//        {
//            mediaPlayer.start();
//        }
//        else
//        {
//            mediaPlayer.stop();
//        }


        CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtTimeCount.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                //txtTimeCount.setText("FINISH!!");
                AlertDialog.Builder message = new AlertDialog.Builder(GameActivity.this);
                message.setMessage("Sorry! Time is over.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }).show();
                timer.cancel();
            }
        }.start();
    }

    public void changePos1()
    {
        // img1PosY -= 10;

        // update position based on current direction
        img1PosX = img1PosX + img1DirX;
        img1PosY = img1PosY + img1DirY;


        // bounce on left or right edge
        if ((img1DirX > 0 && img1PosX+img1W/2 > screenWidth) || (img1DirX < 0 && img1PosX-img1W/2 < 0)) {

            img1DirX = -1 * img1DirX;
        }

        // bounce on top or bottom edge
        if ((img1DirY > 0 && img1PosY+img1H/2 > screenHeight) || (img1DirY < 0 && img1PosY-img1H/2 < 0)) {
            img1DirY = -1 * img1DirY;
        }

        textView1.setX(img1PosX);
        textView1.setY(img1PosY);


    }
    public void changePos2()
    {
        // img2PosY -= 10;

        // update position based on current direction
        img2PosX = img2PosX + img2DirX;
        img2PosY = img2PosY + img2DirY;



        // bounce on left or right edge
        if ((img2DirX > 0 && img2PosX+img2W/2 > screenWidth) || (img2DirX < 0 && img2PosX-img2W/2 < 0)) {

            img2DirX = -1 * img2DirX;
        }

        // bounce on top or bottom edge
        if ((img2DirY > 0 && img2PosY+img2H/2 > screenHeight) || (img2DirY < 0 && img2PosY-img2H/2 < 0)) {
            img2DirY = -1 * img2DirY;
        }

        textView2.setX(img2PosX);
        textView2.setY(img2PosY);


    }
    public void changePos3()
    {
        //  img3PosY -= 10;

        // update position based on current direction
        img3PosX = img3PosX + img3DirX;
        img3PosY = img3PosY + img3DirY;



        // bounce on left or right edge
        if ((img3DirX > 0 && img3PosX+img3W/2 > screenWidth) || (img3DirX < 0 && img3PosX-img3W/2 < 0)) {

            img3DirX = -1 * img3DirX;
        }

        // bounce on top or bottom edge
        if ((img3DirY > 0 && img3PosY+img3H/2 > screenHeight) || (img3DirY < 0 && img3PosY-img3H/2 < 0)) {
            img3DirY = -1 * img3DirY;
        }

        textView3.setX(img3PosX);
        textView3.setY(img3PosY);


    }
    public void changePos4()
    {
        // img4PosY -= 10;

        // update position based on current direction
        img4PosX = img4PosX + img4DirX;
        img4PosY = img4PosY + img4DirY;



        // bounce on left or right edge
        if ((img4DirX > 0 && img4PosX+img4W/2 > screenWidth) || (img4DirX < 0 && img4PosX-img4W/2 < 0)) {

            img4DirX = -1 * img4DirX;
        }

        // bounce on top or bottom edge
        if ((img4DirY > 0 && img4PosY+img4H/2 > screenHeight) || (img4DirY < 0 && img4PosY-img4H/2 < 0)) {
            img4DirY = -1 * img4DirY;
        }

        textView4.setX(img4PosX);
        textView4.setY(img4PosY);


    }
    public void changePos5()
    {
        //   img5PosY -= 10;

        // update position based on current direction
        img5PosX = img5PosX + img5DirX;
        img5PosY = img5PosY + img5DirY;

        // bounce on left or right edge
        if ((img5DirX > 0 && img5PosX+img5W/2 > screenWidth) || (img5DirX < 0 && img5PosX-img5W/2 < 0)) {

            img5DirX = -1 * img5DirX;
        }

        // bounce on top or bottom edge
        if ((img5DirY > 0 && img5PosY+img5H/2 > screenHeight) || (img5DirY < 0 && img5PosY-img5H/2 < 0)) {
            img5DirY = -1 * img5DirY;
        }

        textView5.setX(img5PosX);
        textView5.setY(img5PosY);
    }


    private void checkBlank(String num)
    {
        final int[] sortAry1 = new int[5];
        for(int i = 0 ; i< sortAry1.length ; i++)
        {
            sortAry1[i] = sortAry[i];
        }
        System.out.println("Is First Place blnak"+ isFirstPlaceBlank);
        if(isFirstPlaceBlank)
        {
            System.out.println("Number in First Blank"+num);
            place1.setText(num);

            System.out.println("Place 1: "+ (sortAry1[0] == Integer.parseInt(place1.getText().toString())));
            if(sortAry1[0] == Integer.parseInt(place1.getText().toString()))
            {
                place1.setText(Integer.toString(sortAry1[0]));
                isFirstPlaceBlank = false;

            }
            else
            {
                for(int i = 0 ; i< object.length ; i++)
                {
                    for(int j=5 ; j<10 ; j++)
                    {
                        object[i].setText(easy[j].toString());
                    }
                }
                isFirstPlaceBlank = true;
                isSecondPlaceBlank = true;
                isThirdPlaceBlank = true;
                isFourthPlaceBlank = true;
                place1.setText("");
                place2.setText("");
                place3.setText("");
                place4.setText("");
                place5.setText("");
            }
        }
        else if(!isFirstPlaceBlank && isSecondPlaceBlank)
        {
            place2.setText(num);
            if(sortAry1[1] == Integer.parseInt(place2.getText().toString()))
            {
                place2.setText(Integer.toString(sortAry1[1]));
                isSecondPlaceBlank = false;

            }
            else
            {
                for(int i = 0 ; i< object.length ; i++)
                {
                    for(int j=10 ; j<15 ; j++)
                    {
                        object[i].setText(easy[j].toString());
                    }
                }
                isFirstPlaceBlank = true;
                isSecondPlaceBlank = true;
                isThirdPlaceBlank = true;
                isFourthPlaceBlank = true;
                place1.setText("");
                place2.setText("");
                place3.setText("");
                place4.setText("");
                place5.setText("");
            }

        }
        else if(!isFirstPlaceBlank && !isSecondPlaceBlank && isThirdPlaceBlank)
        {
            place3.setText(num);
            if(sortAry1[2] == Integer.parseInt(place3.getText().toString()))
            {
                place3.setText(Integer.toString(sortAry1[2]));
                isSecondPlaceBlank = false;

            }
            else
            {
                for(int i = 0 ; i< object.length ; i++)
                {
                    for(int j=15 ; j<20 ; j++)
                    {
                        object[i].setText(easy[j]);
                    }
                }
                isFirstPlaceBlank = true;
                isSecondPlaceBlank = true;
                isThirdPlaceBlank = true;
                isFourthPlaceBlank = true;
                place1.setText("");
                place2.setText("");
                place3.setText("");
                place4.setText("");
                place5.setText("");
            }
        }
        else if(!isFirstPlaceBlank && !isSecondPlaceBlank && !isThirdPlaceBlank && isFourthPlaceBlank)
        {
            place4.setText(num);
            score.setText("1");
            isFourthPlaceBlank = false;
            //playEasyGame();
            isFirstWon = true;
        }
    }
    public void playEasyGame()
    {
        if(isFirstWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=5 ; j<10 ; j++)
                {
                    object[i].setText(easy[j].toString());
                }
            }
        }
        else if(isSecondWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=10 ; j<15 ; j++)
                {
                    object[i].setText(easy[j].toString());
                }
            }
        }
        else if(isThirdWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=15 ; j<20 ; j++)
                {
                    object[i].setText(easy[j].toString());
                }
            }
        }
        else
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=0 ; j<5 ; j++)
                {
                    object[i].setText(easy[j].toString());
                }
            }
        }


    }
    public void playMediumGame()
    {
        if(isFirstWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=5 ; j<10 ; j++)
                {
                    object[i].setText(medium[j]);
                }
            }
        }
        else if(isSecondWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=10 ; j<15 ; j++)
                {
                    object[i].setText(medium[j]);
                }
            }
        }
        else if(isThirdWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=15 ; j<20 ; j++)
                {
                    object[i].setText(medium[j]);
                }
            }
        }
        else
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=0 ; j<5 ; j++)
                {
                    object[i].setText(medium[j]);
                }
            }
        }


    }
    public void playHardGame()
    {
        if(isFirstWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=5 ; j<10 ; j++)
                {
                    object[i].setText(hard[j]);
                }
            }
        }
        else if(isSecondWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=10 ; j<15 ; j++)
                {
                    object[i].setText(hard[j]);
                }
            }
        }
        else if(isThirdWon)
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=15 ; j<20 ; j++)
                {
                    object[i].setText(hard[j]);
                }
            }
        }
        else
        {
            for(int i = 0 ; i< object.length ; i++)
            {
                for(int j=0 ; j<5 ; j++)
                {
                    object[i].setText(hard[j]);
                }
            }
        }


    }

    public String setLevel() {

        levelChoice = getSharedPreferences("level", Context.MODE_PRIVATE);
        String level = levelChoice.getString("level", "");
        System.out.println("Level In Game Activity: "+level);
        return level;

    }
    public String changeBackground() {
        backgroundChoice = getSharedPreferences("background", Context.MODE_PRIVATE);
        String background = backgroundChoice.getString("background", "");
        System.out.println("Background in Game Activity?: "+background);
        return background;

    }





}
