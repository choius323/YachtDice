package com.example.yachtdice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    ScoreBoard scoreBoard;
    DiceView dices;
    DiceView DiceInfo ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoard = new ScoreBoard();
        dices = new DiceView(getApplicationContext());

        //초기화 버튼
        (findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreBoard.resetGame();
            }
        });

        // 주사위 굴리기 버튼
        ((Button)findViewById(R.id.btnRoll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView dice1 = findViewById(R.id.dice1);
                ImageView dice2 = findViewById(R.id.dice2);
                ImageView dice3 = findViewById(R.id.dice3);
                ImageView dice4 = findViewById(R.id.dice4);
                ImageView dice5 = findViewById(R.id.dice5);

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);  // 애니메이션을 수행하기 위해 anim폴더의 rotate.xml을 불러옴

                Handler delayHandler = new Handler();
                dice1.startAnimation(animation);
                dice2.startAnimation(animation);
                dice3.startAnimation(animation);
                dice4.startAnimation(animation);
                dice5.startAnimation(animation);
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate2);
                ImageView dice1 = findViewById(R.id.dice1);
                ImageView dice2 = findViewById(R.id.dice2);
                ImageView dice3 = findViewById(R.id.dice3);
                ImageView dice4 = findViewById(R.id.dice4);
                ImageView dice5 = findViewById(R.id.dice5);

                dice1.startAnimation(animation2);
                dice2.startAnimation(animation2);
                dice3.startAnimation(animation2);
                dice4.startAnimation(animation2);
                dice5.startAnimation(animation2);
                    }
                }, 250);
                for(int i=0;i<5;i++){
                    int id = getResources().getIdentifier("dice"+(i+1), "id", "com.example.yachtdice");
                    ImageView iv = findViewById(id);
                    dices.rollDice(iv, i);
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //주사위 클릭, 킵 설정
    public void onClickDice(View view){
        dices.keepDice((ImageView)view);
    }

    //점수 입력
    public void onClickScore(View view){
        scoreBoard.calcScore(view.getId(), dices.getDiceValues());
    }

}