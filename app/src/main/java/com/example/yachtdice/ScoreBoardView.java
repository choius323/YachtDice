package com.example.yachtdice;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;

public class ScoreBoardView extends TableLayout {

    private final TableLayout cl;
    int resetCount = 0;

    public ScoreBoardView(Context context) {
        this(context, null);
    }

    public ScoreBoardView(Context context, AttributeSet attrs) {
        super(context, null);
        // 메인에 스코어 뷰 등록
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cl = (TableLayout) inflater.inflate(R.layout.score_board_view, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    // 선택된 칸 점수 계산
    public void calcScore(TextView selectedCell, int[] values) {
        int[] scoreId = new int[12];
//        TextView textView = findViewById(selectedCell);
        for (int i = 0; i < 12; i++) {
            scoreId[i] = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
        }

        int sum = 0;

        // 1~6 칸
        for (int i = 1; i < 7; i++) {
            if (selectedCell.getId() == scoreId[i - 1]) {
                for (int value : values) { // values == 받아온 주사위 값 배열
                    if (value == i) {
                        sum += value;
                    }
                }
                selectedCell.setText("" + sum);
            }
        }
        // choice 칸
        if (selectedCell.getId() == scoreId[6]) {
            for (int value : values) {
                sum += value;
                selectedCell.setText("" + sum);
            }
        } else if (selectedCell.getId() == scoreId[7]) { // 4 of a Kind 칸
            int cnt = 0;
            int temp = 7;
            int save = 0;
            for (int value : values) {
                if (value % temp == 0) {
                    cnt += 1;
                    save = value;
                }
                temp = value;
            }
            if (cnt == 4) {
                selectedCell.setText("" + (save * 4));
            } else {
                selectedCell.setText("" + 0);
            }
        } else if (selectedCell.getId() == scoreId[8]) { // Full House 칸

        } else if (selectedCell.getId() == scoreId[9]) { // Small Straight 칸

        } else if (selectedCell.getId() == scoreId[10]) { // Large Straight 칸

        } else if (selectedCell.getId() == scoreId[11]) { // Yacht 칸
            for (int value : values) {
                sum += value;
            }
            if ((float) sum /5 == (float) values[0]) {
                selectedCell.setText("" + 50);
            } else {
                selectedCell.setText("" + 0);
            }
        }
        calcBonus();
    }

    //    1~6 까지 점수 합산후 63넘으면 총점에 30점 추가
    public void calcBonus() {
        int semiTotal = 0;
        TextView textView;
        for (int i = 0; i < 6; i++) {
            int search = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
            textView = findViewById(search);
            if (textView.getText().toString().equals("")) {
                semiTotal += 0;
            } else {
                semiTotal += Integer.parseInt(textView.getText().toString());
            }
        }
        if (semiTotal >= 63) {
            // bonus score에 보너스 점수 등록
        }
        calcTotal();
    }

    //    1~12 까지 점수 합산  + 보너스점수 추가
    public void calcTotal() {
        int total = 0;
        TextView textView;
        int id;
        for (int i = 0; i < 12; i++) {
            id = getResources().getIdentifier("score" + (i + 1), "id", "com.example.yachtdice");
            textView = findViewById(id);
            if (textView.getText().toString().equals("")) {
                total += 0;
            } else {
                total += Integer.parseInt(textView.getText().toString());
            }
        }

//        id = getResources().getIdentifier("bonusScore", "id", "com.example.yachtdice");
//        textView = findViewById(id);
//        total += Integer.parseInt(textView.getText().toString());

        // total score에 점수 등록

        resetCount++;
    }
}
