package com.example.yachtdice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ScoreBoardView scoreBoardView;
    DiceView dices;
    boolean isClickableDice = false;
    boolean isClickableScore = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoardView = new ScoreBoardView(getApplicationContext());
        dices = new DiceView(getApplicationContext());

        //초기화 버튼
        (findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        // 주사위 굴리기 버튼
        ((Button) findViewById(R.id.btnRoll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClickableDice == false){
                    toggleClickableDice();
                }
                if(isClickableScore == false){
                    toggleClickableScore();
                }
                for (int i = 0; i < 5; i++) {
                    int id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtdice");
                    ImageView iv = findViewById(id);
                    dices.rollDice(iv, i);
                }
                dices.rollCount += 1;
                ((TextView)findViewById(R.id.txtRollCount)).setText(dices.rollCount + " / 3");
//                굴리기 횟수 끝
                if(dices.rollCount >= 3){
                    findViewById(R.id.btnRoll).setClickable(false);
                    toggleClickableDice();
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
    public void onClickDice(View view) {
        dices.keepDice((ImageView) view);
    }

    //점수 입력
    public void onClickScore(View view) {
        scoreBoardView.calcScore((TextView) view, dices.getDiceValues());
        findViewById(R.id.btnRoll).setClickable(true);
        dices.rollCount = 0;
        ((TextView)findViewById(R.id.txtRollCount)).setText(dices.rollCount + " / 3");
        for (int i=1;i<6;i++){
            if(dices.dice[i-1].keep == true){
                int id = getResources().getIdentifier("dice" + i, "id", "com.example.yachtdice");
                dices.keepDice((ImageView)findViewById(id));
            }
        }
        if(scoreBoardView.resetCount >= 12){
            findViewById(R.id.btnReset).setVisibility(View.VISIBLE);
            findViewById(R.id.btnRoll).setVisibility(View.INVISIBLE);
            scoreBoardView.resetCount = 0;
        } else {
            toggleClickableDice();
            toggleClickableScore();
        }
    }

    // 게임 초기화
    public void resetGame(){
        findViewById(R.id.btnRoll).setVisibility(View.VISIBLE);
        findViewById(R.id.btnReset).setVisibility(View.INVISIBLE);
        int id;
        for (int i = 0; i < 12; i++) {
            id = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
            ((TextView) findViewById(id)).setText("");
        }
        id = getResources().getIdentifier("subScore", "id", "com.example.yachtdice");
        ((TextView) findViewById(id)).setText("");

        id = getResources().getIdentifier("totalScore", "id", "com.example.yachtdice");
        ((TextView) findViewById(id)).setText("");
    }

    // 스코어 클릭 유무
    public void toggleClickableScore(){
        int id;
        isClickableScore = !isClickableScore;

        for (int i = 1; i < 13; i++) {
            id = getResources().getIdentifier("score" + i, "id", "com.example.yachtdice");
            ((TextView) findViewById(id)).setClickable(isClickableScore);
        }
        id = getResources().getIdentifier("subScore", "id", "com.example.yachtdice");
        ((TextView) findViewById(id)).setClickable(isClickableScore);

        id = getResources().getIdentifier("totalScore", "id", "com.example.yachtdice");
        ((TextView) findViewById(id)).setClickable(isClickableScore);
    }

    // 주사위 클릭 유무
    public void toggleClickableDice(){
        int id;
        isClickableDice = !isClickableDice;

        for (int i = 1; i < 6; i++) {
            id = getResources().getIdentifier("dice" + i, "id", "com.example.yachtdice");
            ((ImageView) findViewById(id)).setClickable(isClickableDice);
        }
    }
}