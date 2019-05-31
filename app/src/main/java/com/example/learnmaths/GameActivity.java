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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {

    private SharedPreferences levelChoice;
    private SharedPreferences backgroundChoice;
    private SharedPreferences soundStatus;


    private int[] placeAry = new int[5];

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


    private CountDownTimer countDownTimer;

    //Counting Time
    private int count = 0;
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
    private boolean isFifthPlaceBlank = true;

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


    private String[][] easy = {{"1", "2", "3", "40", "47"}, {"4", "5", "30", "35", "36"}, {"6", "7", "8", "23", "24"}, {"9", "10", "11", "12", "20"}}; // total = 20
    private String[][] medium = {{"12", "63-14", "20", "1+25", "23"}, {"33+12", "24", "50-21", "30", "20-3"}, {"35", "9+4", "36", "10+4", "40"}, {"11+5", "47", "12+3", "48", "50"}}; //total = 20
    private String[][] hard = {{"29+14", "19*6-89", "79-23", "35", "17+15"}, {"44-6*3", "38-26", "109", "43+37", "2*14+23"}, {"54-17", "76", "28+16", "230-17*13", "63-14"}, {"93", "75-28", "45+12-9", "12+14", "53+3-26"}}; //total = 20
    private String[][] selectedLevel;


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

        txtTimeCount = findViewById(R.id.time_count);

        mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.main_song);
        soundStatus = getSharedPreferences("sound_status", Context.MODE_PRIVATE);
        String sound_status = soundStatus.getString("sound_status", "");
        if(sound_status.equals("On"))
        {
            mediaPlayer.start();
        }
        else if(sound_status.equals("Off"))
        {
            mediaPlayer.stop();
        }

        //Get Screen Size.
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        System.out.println("Width" + Integer.toString(screenWidth));
        System.out.println("Height" + Integer.toString(screenHeight));

        if (changeBackground().equals("Bubble")) {
            for (int i = 0; i < object.length; i++) {
                // object[i].setBackgroundResource(R.drawable.bubble);
                object[i].setBackground(getDrawable(R.drawable.bubble));


            }
        } else if (changeBackground().equals("Ball")) {
            for (int i = 0; i < object.length; i++) {
                // object[i].setBackgroundResource(R.drawable.bubble);
                object[i].setBackground(getDrawable(R.drawable.basketball_background));


            }
        }
        if (getLevel().equals("Easy")) {
            selectedLevel = easy;
            playGame(selectedLevel);
        } else if (getLevel().equals("Medium")) {
            selectedLevel = medium;
            playGame(selectedLevel);
        } else if (getLevel().equals("Hard")) {
            selectedLevel = hard;
            playGame(selectedLevel);
        }
//        if(setLevel().equals("Easy"))
//        {
//
//
//
//
//            //Display object Ary
//            System.out.println("Print Object Array");
//            for(int i=0 ; i<object.length ;i++)
//            {
//                System.out.print(object[i].getText().toString()+"\t");
//                sortAry[i] = Integer.parseInt(object[i].getText().toString());
//            }
//            //Sort the number of Text View in Ascending Order
//            for (int i = 0; i < sortAry.length; i++)
//            {
//                for (int j = i + 1; j < sortAry.length; j++)
//                {
//                    //  System.out.println(object[i].getText().toString()+"\t");
//
//                    if (sortAry[i] > sortAry[j])
//                    {
//                        temp = sortAry[i];
//                        sortAry[i] = sortAry[j];
//                        sortAry[j] = temp;
//
//
//                    }
//                }
//
//            }
//
//
//            //Display Sorted Array Object
//            System.out.println("Sorted Array: ");
//            for(int i=0 ; i<sortAry.length ; i++)
//            {
//                System.out.print(sortAry[i]+"\t");
//            }
//            textView1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isClicked = true;
//                    textView1.setVisibility(TextView.INVISIBLE);
//                    System.out.println("Text View 1 is clicked");
//                    checkBlank(textView1.getText().toString());
//
//                }
//            });
//            textView2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isClicked = true;
//                    textView2.setVisibility(TextView.INVISIBLE);
//                    checkBlank(textView2.getText().toString());
//                    System.out.println("Text View 2 is clicked");
//                }
//            });
//            textView3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isClicked = true;
//                    textView3.setVisibility(TextView.INVISIBLE);
//                    checkBlank(textView3.getText().toString());
//                    System.out.println("Text View 3 is clicked");
//                }
//            });
//            textView4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isClicked = true;
//                    textView4.setVisibility(TextView.INVISIBLE);
//                    checkBlank(textView4.getText().toString());
//                    System.out.println("Text View 4 is clicked");
//                }
//            });
//            textView5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    isClicked = true;
//                    textView5.setVisibility(TextView.INVISIBLE);
//                    checkBlank(textView5.getText().toString());
//                    System.out.println("Text View 5 is clicked");
//
//                }
//            });
//
//
//        }
//        else if(setLevel().equals("Medium"))
//        {
//            textView1.setText(medium[0]);
//            textView2.setText(medium[1]);
//            textView3.setText(medium[2]);
//            textView4.setText(medium[3]);
//            textView5.setText(medium[4]);
//
//            object[0] = textView1;
//            object[1] = textView2;
//            object[2] = textView3;
//            object[3] = textView4;
//            object[4] = textView5;
//            playMediumGame();
//        }
//        else if(setLevel().equals("Hard"))
//        {
//            for(int i = 0 ; i< object.length ; i++)
//            {
//                for(int j=0 ; j<5 ; j++)
//                {
//                    object[i].setText(hard[j].toString());
//                }
//            }
//            playHardGame();
//
//        }

        countDownTimer = new CountDownTimer(60 * 1000, 1000) {

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


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
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

    }






    public void changePos1() {
        // img1PosY -= 10;

        // update position based on current direction
        img1PosX = img1PosX + img1DirX;
        img1PosY = img1PosY + img1DirY;


        // bounce on left or right edge
        if ((img1DirX > 0 && img1PosX + img1W / 2 > screenWidth) || (img1DirX < 0 && img1PosX - img1W / 2 < 0)) {

            img1DirX = -1 * img1DirX;
        }

        // bounce on top or bottom edge
        if ((img1DirY > 0 && img1PosY + img1H / 2 > screenHeight) || (img1DirY < 0 && img1PosY - img1H / 2 < 0)) {
            img1DirY = -1 * img1DirY;
        }

        textView1.setX(img1PosX);
        textView1.setY(img1PosY);


    }

    public void changePos2() {
        // img2PosY -= 10;

        // update position based on current direction
        img2PosX = img2PosX + img2DirX;
        img2PosY = img2PosY + img2DirY;


        // bounce on left or right edge
        if ((img2DirX > 0 && img2PosX + img2W / 2 > screenWidth) || (img2DirX < 0 && img2PosX - img2W / 2 < 0)) {

            img2DirX = -1 * img2DirX;
        }

        // bounce on top or bottom edge
        if ((img2DirY > 0 && img2PosY + img2H / 2 > screenHeight) || (img2DirY < 0 && img2PosY - img2H / 2 < 0)) {
            img2DirY = -1 * img2DirY;
        }

        textView2.setX(img2PosX);
        textView2.setY(img2PosY);


    }

    public void changePos3() {
        //  img3PosY -= 10;

        // update position based on current direction
        img3PosX = img3PosX + img3DirX;
        img3PosY = img3PosY + img3DirY;


        // bounce on left or right edge
        if ((img3DirX > 0 && img3PosX + img3W / 2 > screenWidth) || (img3DirX < 0 && img3PosX - img3W / 2 < 0)) {

            img3DirX = -1 * img3DirX;
        }

        // bounce on top or bottom edge
        if ((img3DirY > 0 && img3PosY + img3H / 2 > screenHeight) || (img3DirY < 0 && img3PosY - img3H / 2 < 0)) {
            img3DirY = -1 * img3DirY;
        }

        textView3.setX(img3PosX);
        textView3.setY(img3PosY);


    }

    public void changePos4() {
        // img4PosY -= 10;

        // update position based on current direction
        img4PosX = img4PosX + img4DirX;
        img4PosY = img4PosY + img4DirY;


        // bounce on left or right edge
        if ((img4DirX > 0 && img4PosX + img4W / 2 > screenWidth) || (img4DirX < 0 && img4PosX - img4W / 2 < 0)) {

            img4DirX = -1 * img4DirX;
        }

        // bounce on top or bottom edge
        if ((img4DirY > 0 && img4PosY + img4H / 2 > screenHeight) || (img4DirY < 0 && img4PosY - img4H / 2 < 0)) {
            img4DirY = -1 * img4DirY;
        }

        textView4.setX(img4PosX);
        textView4.setY(img4PosY);


    }

    public void changePos5() {
        //   img5PosY -= 10;

        // update position based on current direction
        img5PosX = img5PosX + img5DirX;
        img5PosY = img5PosY + img5DirY;

        // bounce on left or right edge
        if ((img5DirX > 0 && img5PosX + img5W / 2 > screenWidth) || (img5DirX < 0 && img5PosX - img5W / 2 < 0)) {

            img5DirX = -1 * img5DirX;
        }

        // bounce on top or bottom edge
        if ((img5DirY > 0 && img5PosY + img5H / 2 > screenHeight) || (img5DirY < 0 && img5PosY - img5H / 2 < 0)) {
            img5DirY = -1 * img5DirY;
        }

        textView5.setX(img5PosX);
        textView5.setY(img5PosY);
    }

    public String getLevel() {

        levelChoice = getSharedPreferences("level", Context.MODE_PRIVATE);
        String level = levelChoice.getString("level", "");
        System.out.println("Level In Game Activity: " + level);
        return level;

    }

    public String changeBackground() {
        backgroundChoice = getSharedPreferences("background", Context.MODE_PRIVATE);
        String background = backgroundChoice.getString("background", "");
        System.out.println("Background in Game Activity: " + background);
        return background;

    }

    public void setVisible() {
        textView1.setVisibility(TextView.VISIBLE);
        textView2.setVisibility(TextView.VISIBLE);
        textView3.setVisibility(TextView.VISIBLE);
        textView4.setVisibility(TextView.VISIBLE);
        textView5.setVisibility(TextView.VISIBLE);
    }

    public void setBlank() {
        place1.setText("");
        place2.setText("");
        place3.setText("");
        place4.setText("");
        place5.setText("");
    }

    public int[] sort(TextView[] strAry) {
        for (int i = 0; i < strAry.length; i++) {
            sortAry[i] = Integer.parseInt(strAry[i].getText().toString());
        }
        //Sort the number of Text View in Ascending Order
        System.out.println("Before sorted: ");
        for (int i = 0; i < sortAry.length; i++) {
            System.out.print(sortAry[i] + "\t");
        }
        for (int i = 0; i < sortAry.length; i++) {
            for (int j = i + 1; j < sortAry.length; j++) {
                //  System.out.println(object[i].getText().toString()+"\t");

                if (sortAry[i] > sortAry[j]) {
                    temp = sortAry[i];
                    sortAry[i] = sortAry[j];
                    sortAry[j] = temp;


                }
            }

        }
        System.out.println("After sorted: ");
        for (int i = 0; i < sortAry.length; i++) {
            System.out.print(sortAry[i] + "\t");
        }

        return sortAry;

    }

    public void playGame(String[][] ary) {
        for (int count = 0; count < 5; count++) {
            System.out.println("The count number is fdsdfdsfd" + count);
            if (count == 0) {
                System.out.println("The count number is 0");
                //set number to the array
                for (int i = 0; i < object.length; i++) {
                    object[i].setText(ary[0][i]);
                    System.out.print(ary[0] + "\t");

                }
                placeAry = sort(object);
                while (clickCount != 5) {

                    object[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            textView1.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 1 is clicked");
                            checkBlank(textView1.getText().toString());
                            clickCount++;
//                            if (Integer.parseInt(textView1.getText().toString()) == placeAry[0] && isFirstPlaceBlank) {
//                                place1.setText(textView1.getText().toString());
//                                textView1.setVisibility(TextView.INVISIBLE);
//                                clickCount++;
//                            }


                        }
                    });
                    object[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            textView2.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 1 is clicked");
                            checkBlank(textView2.getText().toString());
                            clickCount++;
//                            if (Integer.parseInt(textView2.getText().toString()) == placeAry[1] && isSecondPlaceBlank) {
//                                place2.setText(textView2.getText().toString());
//                                textView2.setVisibility(TextView.INVISIBLE);
//                                clickCount++;
//                            }
                        }
                    });
                    object[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            textView3.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 3 is clicked");
                            checkBlank(textView3.getText().toString());
                            clickCount++;
//                            if (Integer.parseInt(textView3.getText().toString()) == placeAry[2] && isThirdPlaceBlank) {
//                                place3.setText(textView3.getText().toString());
//                                textView3.setVisibility(TextView.INVISIBLE);
//                                isThirdPlaceBlank = false;
//                                clickCount++;
//                            } else {
//                                //  count++;
//                            }
                        }
                    });
                    object[3].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickCount++;
                            textView4.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 4 is clicked");
                            checkBlank(textView4.getText().toString());
                            clickCount++;
//                            if (Integer.parseInt(textView4.getText().toString()) == placeAry[3] && isFourthPlaceBlank) {
//                                place4.setText(textView4.getText().toString());
//                                textView4.setVisibility(TextView.INVISIBLE);
//                                isFourthPlaceBlank = false;
//                                clickCount++;
//                            }
                        }
                    });
                    object[4].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            textView5.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 5 is clicked");
                            checkBlank(textView5.getText().toString());
                            clickCount++;
//                            if (Integer.parseInt(textView5.getText().toString()) == placeAry[4] && isFifthPlaceBlank) {
//                                place5.setText(textView5.getText().toString());
//                                textView5.setVisibility(TextView.INVISIBLE);
//                                isFifthPlaceBlank = false;
//                                clickCount++;
//                            } else {
//                                // count++;
//                            }
                        }
                    });
                    count++;

                }
            } else if (count == 1) {
                clickCount = 0;
                System.out.println("The count number is 0");
                setVisible();
                for (int i = 0; i < object.length; i++) {
                    object[i].setText(ary[1][i]);
                }
                placeAry = sort(object);
                while (clickCount != 5) {

                    object[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = true;
                            textView1.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 1 is clicked");
                            //checkBlank(textView1.getText().toString());
                            if (Integer.parseInt(textView1.getText().toString()) == placeAry[0] && isFirstPlaceBlank) {
                                place1.setText(textView1.getText().toString());
                                textView1.setVisibility(TextView.INVISIBLE);
                                isFirstPlaceBlank = false;
                            } else {
                                //count++;
                            }

                        }
                    });
                    object[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = true;
                            textView2.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 1 is clicked");
                            //checkBlank(textView2.getText().toString());
                            if (Integer.parseInt(textView2.getText().toString()) == placeAry[1] && isSecondPlaceBlank) {
                                place2.setText(textView2.getText().toString());
                                textView2.setVisibility(TextView.INVISIBLE);
                                isSecondPlaceBlank = false;
                            } else {
                                //count++;
                            }
                        }
                    });
                    object[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = true;
                            textView3.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 3 is clicked");
                            //checkBlank(textView3.getText().toString());
                            if (Integer.parseInt(textView3.getText().toString()) == placeAry[2]) {
                                place3.setText(textView3.getText().toString());
                                textView3.setVisibility(TextView.INVISIBLE);
                            } else {
                                //count++;
                            }
                        }
                    });
                    object[3].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = true;
                            textView4.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 4 is clicked");
                            //checkBlank(textView4.getText().toString());
                            if (Integer.parseInt(textView4.getText().toString()) == placeAry[3]) {
                                place4.setText(textView4.getText().toString());
                                textView4.setVisibility(TextView.INVISIBLE);
                            } else {
                                //count++;
                            }
                        }
                    });
                    object[4].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isClicked = true;
                            textView5.setVisibility(TextView.INVISIBLE);
                            System.out.println("Text View 5 is clicked");
                            checkBlank(textView5.getText().toString());
//                            if (Integer.parseInt(textView5.getText().toString()) == placeAry[4]) {
//                                place5.setText(textView5.getText().toString());
//                                textView5.setVisibility(TextView.INVISIBLE);
//                            } else {
//                                //count++;
//                            }
                        }
                    });
                }
                count++;
            } else if (count == 2) {
                setVisible();
                for (int i = 0; i < object.length; i++) {
                    object[i].setText(ary[2][i]);
                }
                placeAry = sort(object);
                object[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView1.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 1 is clicked");
                        checkBlank(textView1.getText().toString());
//                        if (Integer.parseInt(textView1.getText().toString()) == placeAry[0]) {
//                            place1.setText(textView1.getText().toString());
//                            textView1.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //   count++;
//                        }

                    }
                });
                object[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView2.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 1 is clicked");
                        checkBlank(textView2.getText().toString());
//                        if (Integer.parseInt(textView2.getText().toString()) == placeAry[1]) {
//                            place2.setText(textView2.getText().toString());
//                            textView2.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //count++;
//                        }
                    }
                });
                object[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView3.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 3 is clicked");
                        checkBlank(textView3.getText().toString());
//                        if (Integer.parseInt(textView3.getText().toString()) == placeAry[2]) {
//                            place3.setText(textView3.getText().toString());
//                            textView3.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //  count++;
//                        }
                    }
                });
                object[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView4.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 4 is clicked");
                        checkBlank(textView4.getText().toString());
//                        if (Integer.parseInt(textView4.getText().toString()) == placeAry[3]) {
//                            place4.setText(textView4.getText().toString());
//                            textView4.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //count++;
//                        }
                    }
                });
                object[4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView5.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 5 is clicked");
                        checkBlank(textView5.getText().toString());
//                        if (Integer.parseInt(textView5.getText().toString()) == placeAry[4]) {
//                            place5.setText(textView5.getText().toString());
//                            textView5.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //  count++;
//                        }
                    }
                });

            } else if (count == 3) {
                setVisible();
                for (int i = 0; i < object.length; i++) {
                    object[i].setText(ary[3][i]);
                }
                placeAry = sort(object);
                int j;
                object[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView1.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 1 is clicked");
                        checkBlank(textView1.getText().toString());
//                        if (Integer.parseInt(textView1.getText().toString()) == placeAry[0]) {
//                            place1.setText(textView1.getText().toString());
//                            textView1.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            // count++;
//                        }

                    }
                });
                object[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView2.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 1 is clicked");
                        checkBlank(textView2.getText().toString());
//                        if (Integer.parseInt(textView2.getText().toString()) == placeAry[1]) {
//                            place2.setText(textView2.getText().toString());
//                            textView2.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //  count++;
//                        }
                    }
                });
                object[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView3.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 3 is clicked");
                        checkBlank(textView3.getText().toString());
//                        if (Integer.parseInt(textView3.getText().toString()) == placeAry[2]) {
//                            place3.setText(textView3.getText().toString());
//                            textView3.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            // count++;
//                        }
                    }
                });
                object[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView4.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 4 is clicked");
                        checkBlank(textView4.getText().toString());
//                        if (Integer.parseInt(textView4.getText().toString()) == placeAry[3]) {
//                            place4.setText(textView4.getText().toString());
//                            textView4.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            // count++;
//                        }
                    }
                });
                object[4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClicked = true;
                        textView5.setVisibility(TextView.INVISIBLE);
                        System.out.println("Text View 5 is clicked");
                        checkBlank(textView5.getText().toString());
//                        if (Integer.parseInt(textView5.getText().toString()) == placeAry[4]) {
//                            place5.setText(textView5.getText().toString());
//                            textView5.setVisibility(TextView.INVISIBLE);
//                        } else {
//                            //count++;
//                        }
                    }
                });

            }
            count++;
        }
    }

    private boolean checkBlank(String num) {
        final int[] sortAry1 = new int[5];
        boolean isBlank = false;
        for (int i = 0; i < sortAry1.length; i++) {
            sortAry1[i] = sortAry[i];
        }



        System.out.println("Is First Place blnak" + isFirstPlaceBlank);
        if (isFirstPlaceBlank) {
            if (sortAry1[0] == Integer.parseInt(num)) {
                place1.setText(Integer.toString(sortAry1[0]));
                isFirstPlaceBlank = false;
                isBlank = false;
            }
        } else if (isSecondPlaceBlank) {
            if (sortAry1[1] == Integer.parseInt(num)) {
                place2.setText(Integer.toString(sortAry1[1]));
                isSecondPlaceBlank = false;
                isBlank = false;
            }

        } else if (isThirdPlaceBlank) {
            if (sortAry1[2] == Integer.parseInt(num)) {
                place3.setText(Integer.toString(sortAry1[2]));
                isThirdPlaceBlank = false;
                isBlank = false;
            }

        } else if (isFourthPlaceBlank) {
            if (sortAry1[3] == Integer.parseInt(num)) {
                place1.setText(Integer.toString(sortAry1[0]));
                isFourthPlaceBlank = false;
                isBlank = false;

            }

        } else {
            isBlank = true;
        }
        return isBlank;

    }

}
//        if(isFirstPlaceBlank)
//        {
//            System.out.println("Number in First Blank"+num);
//            place1.setText(num);
//
//            System.out.println("Place 1: "+ (sortAry1[0] == Integer.parseInt(place1.getText().toString())));
//            if(sortAry1[0] == Integer.parseInt(place1.getText().toString()))
//            {
//                place1.setText(Integer.toString(sortAry1[0]));
//                isFirstPlaceBlank = false;
//
//            }
//            else
//            {
//                setBlank();
//                setVisible();
//
//                String [] selectedNum = new String[5];
//                int addSelectedNum = 0;
//                for(int j=5 ; j<10 ; j++)
//                {
//                    selectedNum[addSelectedNum] = easy[j];
//                }
//
//                for(int i = 0 ; i< object.length ; i++)
//                {
//                    object[i].setText(selectedNum[i]);
//                }
//                isFirstPlaceBlank = true;
//                isSecondPlaceBlank = true;
//                isThirdPlaceBlank = true;
//                isFourthPlaceBlank = true;
//
//            }
//        }
//        else if(!isFirstPlaceBlank && isSecondPlaceBlank)
//        {
//            place2.setText(num);
//            if(sortAry1[1] == Integer.parseInt(place2.getText().toString()))
//            {
//                place2.setText(Integer.toString(sortAry1[1]));
//                isSecondPlaceBlank = false;
//
//            }
//            else
//            {
//                for(int i = 0 ; i< object.length ; i++)
//                {
//                    for(int j=10 ; j<15 ; j++)
//                    {
//                        object[i].setText(easy[j].toString());
//                    }
//                }
//                isFirstPlaceBlank = true;
//                isSecondPlaceBlank = true;
//                isThirdPlaceBlank = true;
//                isFourthPlaceBlank = true;
//                setBlank();
//                setVisible();
//            }
//
//        }
//        else if(!isFirstPlaceBlank && !isSecondPlaceBlank && isThirdPlaceBlank)
//        {
//            place3.setText(num);
//            if(sortAry1[2] == Integer.parseInt(place3.getText().toString()))
//            {
//                place3.setText(Integer.toString(sortAry1[2]));
//                isSecondPlaceBlank = false;
//
//            }
//            else
//            {
//                for(int i = 0 ; i< object.length ; i++)
//                {
//                    for(int j=15 ; j<20 ; j++)
//                    {
//                        object[i].setText(easy[j]);
//                    }
//                }
//                isFirstPlaceBlank = true;
//                isSecondPlaceBlank = true;
//                isThirdPlaceBlank = true;
//                isFourthPlaceBlank = true;
//                setBlank();
//                setVisible();
//            }
//        }
//        else if(!isFirstPlaceBlank && !isSecondPlaceBlank && !isThirdPlaceBlank && isFourthPlaceBlank)
//        {
//            place4.setText(num);
//
//            isFourthPlaceBlank = false;
//            //playEasyGame();
//            isFirstWon = true;
//        }
//        else if(!isFirstPlaceBlank && !isSecondPlaceBlank && !isThirdPlaceBlank && !isFourthPlaceBlank && isFifthPlaceBlank)
//        {
//            place4.setText(num);
//
//            isFourthPlaceBlank = false;
//            //playEasyGame();
//            isFirstWon = true;
//        }








