package com.example.yachtdice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    ScoreBoard scoreBoard;//  sss
    DiceView dices;

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
                reset();
            }
        });

        // 주사위 굴리기 버튼
        ((Button)findViewById(R.id.btnRoll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dices.rollDice();
            }
        });
    }

    //점수 입력
//    public void onClickScore(View view){
//        scoreBoard.calcScore(view.getId(), dices.getDiceValues());
//    }

    public void reset(){

    }
}