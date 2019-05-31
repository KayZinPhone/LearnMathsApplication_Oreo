package com.example.learnmaths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {


    private String backgroundChose;
    private String levelChose;


    private boolean isBackgroundChosen = false;
    private boolean isLevelChosen = false;
    private boolean isBubbleBackgroundChosen = false;
    private boolean isEasyChosen = false;
    private boolean isMediumChosen = false;
    private boolean isHardChosen = false;

    private SharedPreferences levelChoice;
    private SharedPreferences.Editor levelChoiceEditor;
    private SharedPreferences backgroundChoice;
    private SharedPreferences.Editor backgroundChoiceEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        levelChoice = getSharedPreferences("level", Context.MODE_PRIVATE);
        backgroundChoice = getSharedPreferences("background", Context.MODE_PRIVATE);


        ImageView btnBack = findViewById(R.id.back_button);
        final ImageView btnBackgroundBubble = findViewById(R.id.bubble_background);
        ImageView btnBackgroundBall = findViewById(R.id.ball_background);
        ImageView btnEasy = findViewById(R.id.easy_image);
        ImageView btnMedium = findViewById(R.id.medium_image);
        ImageView btnHard = findViewById(R.id.hard_image);

        btnBackgroundBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundChose = "Bubble";
                isBubbleBackgroundChosen = true;
                isBackgroundChosen = true;
                System.out.println("Clicked Bubble");

            }
        });
        btnBackgroundBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundChose = "Ball";
                isBubbleBackgroundChosen = false;
                isBackgroundChosen = true;
                System.out.println("Clicked Ball");
            }
        });

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelChose = "Easy";
                isEasyChosen = true;
                isMediumChosen = false;
                isHardChosen = false;
                isLevelChosen = true;
                System.out.println("Clicked Easy");
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelChose = "Medium";
                isMediumChosen = true;
                isEasyChosen = false;
                isHardChosen = false;
                isLevelChosen = true;
                System.out.println("Clicked Medium");
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelChose = "Hard";
                isHardChosen = true;
                isMediumChosen = false;
                isEasyChosen = false;
                isLevelChosen = true;
                System.out.println("Clicked Hard");
            }
        });

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(intent);
//
//            }
//        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);

                //Test if the background or level chosen
                if(!isBackgroundChosen && !isLevelChosen)
                {
                    backgroundChoiceEditor = backgroundChoice.edit();
                    backgroundChoiceEditor.putString("background", "Bubble");
                    backgroundChoiceEditor.apply();
                    levelChoiceEditor = levelChoice.edit();
                    levelChoiceEditor.putString("level", "Easy");
                    levelChoiceEditor.apply();
                    startActivity(intent);
                }
                else if(isBackgroundChosen)
                {
                    if(isBubbleBackgroundChosen)
                    {
                        backgroundChoiceEditor = backgroundChoice.edit();
                        backgroundChoiceEditor.putString("background", "Bubble");
                        backgroundChoiceEditor.apply();
                        startActivity(intent);

                    }
                    else
                    {
                        backgroundChoiceEditor = backgroundChoice.edit();
                        backgroundChoiceEditor.putString("background", "Ball");
                        backgroundChoiceEditor.apply();
                        startActivity(intent);
                    }
                }
                else if(isLevelChosen)
                {
                    if(isEasyChosen)
                    {
                        levelChoiceEditor = levelChoice.edit();
                        levelChoiceEditor.putString("level", "Easy");
                        levelChoiceEditor.apply();
                        startActivity(intent);

                    }
                    else if(isMediumChosen)
                    {
                        levelChoiceEditor = levelChoice.edit();
                        levelChoiceEditor.putString("level", "Medium");
                        levelChoiceEditor.apply();
                        startActivity(intent);
                    }
                    else{
                        levelChoiceEditor = levelChoice.edit();
                        levelChoiceEditor.putString("level", "Hard");
                        levelChoiceEditor.apply();
                        startActivity(intent);
                    }

                }
                else if(isBackgroundChosen && isLevelChosen)
                {
                    if(isBubbleBackgroundChosen)
                    {
                        backgroundChoiceEditor = backgroundChoice.edit();
                        backgroundChoiceEditor.putString("background", "Bubble");
                        backgroundChoiceEditor.apply();
                        if(isEasyChosen)
                        {
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Easy");
                            levelChoiceEditor.apply();

                            startActivity(intent);
                        }
                        else if(isMediumChosen)
                        {
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Medium");
                            levelChoiceEditor.apply();
                            startActivity(intent);

                        }
                        else{
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Hard");
                            levelChoiceEditor.apply();
                            startActivity(intent);

                        }
                    }
                    else
                    {
                        backgroundChoiceEditor = backgroundChoice.edit();
                        backgroundChoiceEditor.putString("background", "Ball");
                        backgroundChoiceEditor.apply();
                        if(isEasyChosen)
                        {
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Easy");
                            levelChoiceEditor.apply();
                            startActivity(intent);
                        }
                        else if(isMediumChosen)
                        {
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Medium");
                            levelChoiceEditor.apply();
                            startActivity(intent);

                        }
                        else{
                            levelChoiceEditor = levelChoice.edit();
                            levelChoiceEditor.putString("level", "Hard");
                            levelChoiceEditor.apply();
                            startActivity(intent);

                        }
                    }

                }
            }
        });



    }
}
