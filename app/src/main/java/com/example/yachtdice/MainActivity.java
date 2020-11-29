package com.example.yachtdice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    Dice dices;
    ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dices = new Dice(getContext());
        scoreBoard = new ScoreBoard();
        //굴리기 버튼
        (findViewById(R.id.btnRoll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dices.rollDice();
            }
        });
        //초기화 버튼
        (findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dices = new Dice(getContext());
            }
        });
    }
}