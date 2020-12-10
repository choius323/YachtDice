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
    boolean isClickableDice = true;
    boolean isClickableScore = true;
    int resetCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreBoardView = new ScoreBoardView(getApplicationContext());
        dices = new DiceView(getApplicationContext());

        toggleClickableScore();
        toggleClickableDice();

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
        if(((TextView)view).getText().equals("") == true) { // 이미 채운 칸은 클릭 불가
            scoreBoardView.calcScore((TextView) view, dices.getDiceValues()); // 해당 칸 점수 계산
            findViewById(R.id.btnRoll).setClickable(true);
            dices.rollCount = 0;
            ((TextView) findViewById(R.id.txtRollCount)).setText(dices.rollCount + " / 3");
            for (int i = 1; i < 6; i++) {
                if (dices.dice[i - 1].keep == true) {
                    int id = getResources().getIdentifier("dice" + i, "id", "com.example.yachtdice");
                    dices.keepDice((ImageView) findViewById(id));
                }
            }
            if (resetCount >= 11) { // 12칸 모두 채웠을 때
                findViewById(R.id.btnReset).setVisibility(View.VISIBLE);
                findViewById(R.id.btnRoll).setVisibility(View.INVISIBLE);
                resetCount = 0;
            } else {
                resetCount++;
            }
            isClickableDice = true;
            toggleClickableDice();
            toggleClickableScore();
            calcTotalScore();
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

    //    1~6 까지 점수 합산후 63넘으면 총점에 30점 추가
    public void calcTotalScore() {
        int total = 0;
        int subTotal = 0;
        TextView textView;
        for (int i = 0; i < 12; i++) {
            int search = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
            textView = findViewById(search);
            if (textView.getText().toString().equals("")) {
                total += 0;
            } else {
                total += Integer.parseInt(textView.getText().toString());
            }
            if (i == 5){
                subTotal = total;
                if(total >= 63){
                    total += 30;
                }
            }
        }
        ((TextView)findViewById(R.id.subScore)).setText("" + subTotal);
        ((TextView)findViewById(R.id.totalScore)).setText("" + total);
    }
}