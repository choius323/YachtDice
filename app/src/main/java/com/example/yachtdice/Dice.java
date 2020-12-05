package com.example.yachtdice;

import android.view.animation.Animation;
import android.widget.ImageView;

import java.security.AccessControlContext;
import java.util.Random;

public class Dice extends MainActivity {
    //주사위 각각의 정보
    class DiceInfo {
        ImageView img;
        Animation anim;
        int value = 1; //주사위 눈 값
        Boolean keep = false; //킵하는지 저장

        public DiceInfo(int id) {
            value = 1;
            keep = false;
            img = (ImageView) findViewById(id);
        }
    }

    private final int diceNumber = 5;
    private int rollCount;
    private DiceInfo dice[];
    //주사위 눈 바뀌는 애니메이션
    private final int[] ani = new int[]
            {R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4, R.drawable.anim5, R.drawable.anim6};
    private Random rand;

    // 주사위 클래스 생성자
    public Dice(AccessControlContext context) {
        //주사위 5개 객체 생성
        dice = new DiceInfo[diceNumber];
        rand = new Random();
        for (int i = 0; i < diceNumber; i++) {
//            dice[i] = new DiceInfo(getResources().getIdentifier("dice1", "id", context.toString()));
        }

        rollCount = 0;
    }

    //주사위 굴리기
    public void rollDice() {
        Button btn = findViewById(R.id.btnRoll);
        final ImageView[] iv = new ImageView[diceNumber];
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result[] = new int[diceNumber];
                ;
                for (int i=0;i<diceNumber;i++){
                    result[i] = rand.nextInt(6)+1;
                    int ivID = getResources().getIdentifier("dice"+i,"id","com.example.yachtdice");
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),getResources().getIdentifier("anim"+result[i],"drawable", getApplicationContext().getPackageName()));
                    int resID = getResources().getIdentifier("dice"+result[i],"drawbale","com.example.yachtdice");
                    iv[i] = findViewById(ivID);
                    iv[i].startAnimation(anim);
                    iv[i].setImageResource(resID);
                    dice[i].value =result[i];
                }
            }
        });
    }

    //주사위 킵
    public void keepDice() {

    }

    //주사위 값 반환
    public int[] returnDiceValues() {
        int[] values = new int[diceNumber];
        for (int i = 0; i < diceNumber; i++) {
            values[i] = dice[i].value;
        }
        return values;
    }
}
