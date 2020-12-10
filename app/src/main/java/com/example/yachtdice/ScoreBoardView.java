package com.example.yachtdice;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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
        int cnt = 0;
        int comp = 0;
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
            ArrayList<Integer> list = new ArrayList<Integer>();  //리스트 선언
            for (int value : values) {
                list.add(value);
            }
            for(int i=1;i<7;i++){
                int res = Collections.frequency(list,i);    //frequency == 주사위값 i의 개수를 세는 기능
                if(res==4){
                    selectedCell.setText(""+(i*4));
                }
            }
        } else if (selectedCell.getId() == scoreId[8]) { // Full House 칸
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int value : values){
                list.add(value);
            }

            for(int i=1;i<7;i++){
                if(list.contains(i)){ // 리스트에 주사위값이 존재하면 True  아니면 False
                    sum += Collections.frequency(list,i)*i;     //하다보니 족보점수 산정 기준을 모르겠음
                    cnt+=1;                                     // 이하 리스트선언후 비슷하게 코딩함
                }
            }
            if(cnt==2){
                selectedCell.setText(""+sum);
            }
        } else if (selectedCell.getId() == scoreId[9]) { // Small Straight 칸
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int value : values){
                list.add(value);
            }
            cnt =0;
            int cnt2=0;
            int comp2=0;
            for(int i=1;i<7;i++) {
                if (list.contains(i)) {
                    sum += Collections.frequency(list, i) * i;
                    cnt += 1;
                } else {
                    if (i == 1 || i == 6) {
                        cnt2 += 1;
                    }  // 1 , 6 이 없는 경우 cnt2로 조건 // (1,2),(5,6) comp2로 조건
                    if (i != 3 && i != 4) {
                        comp2 += i;
                        comp = i;
                    }
                }
            }
            if(cnt==4 && (comp2==(comp+(comp-1)) || cnt2==2)) { //단, Large Staright 경우 입력 허용 X  (변경가능)
                selectedCell.setText(""+sum);
            }
        } else if (selectedCell.getId() == scoreId[10]) { // Large Straight 칸
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int value : values){
                list.add(value);
            }
            cnt =0;
            for(int i=1;i<7;i++){
                if(list.contains(i)){
                    sum += Collections.frequency(list,i)*i;
                    cnt+=1;
                }
                else{
                    if(i==1 || i==6){ comp += 1;}   //1 or 6 이 없는경우 Large 완성 comp로 조건
                }
            }
            if(cnt==5 && comp==1){
                selectedCell.setText(""+sum);
            }
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
        calcSubScore();
    }

    //    1~6 까지 점수 합산후 63넘으면 총점에 30점 추가
    public void calcSubScore() {
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
