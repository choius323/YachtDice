package com.example.yachtdice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ScoreBoardView extends TableLayout {

    private final TableLayout cl;

    public ScoreBoardView(Context context) {
        this(context, null);
    }

    public ScoreBoardView(Context context, AttributeSet attrs) {
        super(context, null);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cl = (TableLayout) inflater.inflate(R.layout.score_board_view, null);
        // 메인에 스코어 뷰 등록
        View view = View.inflate(context, R.layout.score_board_view, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    int Total = 0;
    public int[] result(int values[]) {



        int[] countList = new int[12];
        int[] sameList = new int[6];
        int small = 0;
        int large = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + 1 == values[j]) {
                    sameList[i]++;
                }
            }
        }
        for (int i = 0; i < 6; i++) {              // 1~6 까지 점수
            countList[i] = (i + 1) * sameList[i];
        }
        for (int i = 0; i < 6; i++) {              //choice
            countList[6] += (i + 1) * sameList[i];
        }
        for (int i = 0; i < 6; i++) {      // 4 of a kind
            if (sameList[i] >= 4) {
                countList[7] = 30;
            }
        }
        for (int i = 0; i < 6; i++) {      //full house
            if (sameList[i] == 3) {
                for (int j = 0; j < 6; j++) {
                    if (sameList[j] == 2) {
                        countList[8] = 30;
                    }
                }
            }
        }
        for (int i=0;i<3;i++){
            for (int j=0;j<4;j++){
                if (sameList[i+j]==0){
                    small += 1;
                    break;
                }
            }
        }
        for (int i=0;i<2;i++){
            for (int j=0;j<5;j++){
                if(sameList[i+j]==0){
                    large += 1;
                    break;
                }
            }
        }
        if(small<3){                //Small Straight
            countList[9] = 15;
        }
        if(large<2){                //Large Straight
            countList[10] = 30;
        }
        for (int i=0;i<6;i++){      //Yacht
            if(sameList[i]==5){
                countList[11] = 50;
            }
        }
        return countList;
    }

    public void calcScore(int selectedCell, int[] values) { //
        int[] score  = result( values);
        TextView textView = findViewById(selectedCell);
        for (int i=0;i<12;i++){
            if(textView.getId()==getResources().getIdentifier("score"+(i+1),"id","com.example.yachtdice")){
                textView.setText(score[i]);
            }
        }// switch(v.getID()){
        // case R.id.name1;
        // case R.id.name2; ...
        //  Log.i("ID값",v.getResources().getResourceEntryName(v.getID()));
        //   결과  : name1,name2,...
    }

    public void calcBonus() {  //1~6 까지 점수 합산후 63넘으면 총점에 30점 추가
        int semiTotal = 0;
        TextView textView[] = new TextView[6];
        for (int i=0;i<6;i++){
            int search = getResources().getIdentifier("score"+(i+1),"id","com.example.yachtdice");
            textView[i].findViewById(search);
            semiTotal += Integer.parseInt(textView[i].getText().toString());
        }
        if(semiTotal>=63){
            Total+=semiTotal;
        }
        calcBonus();
    }

    public void calcTotal() {   // 1~12 까지 점수 합산  + 보너스점수 추가
        TextView textView[] = new TextView[12];
        for (int i=0;i<12;i++) {
            int search = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
            textView[i].findViewById(search);
            Total += Integer.parseInt((textView[i].getText().toString()));
        }
    }

    public void finishRound(int totalScore) {

    }

    public void resetGame() {

    }
}
