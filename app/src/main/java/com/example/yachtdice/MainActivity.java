package com.example.yachtdice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    ScoreBoardView scoreBoard;
    DiceView dices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoard = new ScoreBoardView(getApplicationContext());
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
                for(int i=0;i<5;i++){
                    int id = getResources().getIdentifier("dice"+(i+1), "id", "com.example.yachtdice");
                    ImageView iv = findViewById(id);
                    dices.rollDice(iv, i);
                }
            }
        });
    }

    // 하단바 제거
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