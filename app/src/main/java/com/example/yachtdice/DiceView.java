package com.example.yachtdice;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class DiceView extends ConstraintLayout {
    //주사위 각각의 정보
    class DiceInfo {
        ImageView img;
        Animation anim;
        int value = 1; //주사위 눈 값
        Boolean keep = false; //킵하는지 저장
        int id;
        int num;

        public DiceInfo(int id) {
            this.id = id;
            value = 1;
            keep = false;
            img = (ImageView) findViewById(id);
        }
    }

    final Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
    final Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.rotate2);

    ConstraintLayout cl;
    private final int diceNumber = 5;
    public int rollCount = 0;
    static int[] value;
    DiceInfo dice[];
    //주사위 눈 바뀌는 애니메이션
    private final int[] ani = new int[]
            {R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4, R.drawable.anim5, R.drawable.anim6};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    // 뷰 생성자
    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);

        value = new int[diceNumber];
        // 메인에 다이스 뷰 등록
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cl = (ConstraintLayout) inflater.inflate(R.layout.dice_view, this, true);

        //주사위 5개 객체 생성
        dice = new DiceInfo[diceNumber];
        int id = 0;
        for (int i = 0; i < diceNumber; i++) {
            id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtdice");
//            id = getResources().getIdentifier("dice1", "id", "com.example.yachtdice");
            dice[i] = new DiceInfo(id);
            dice[i].img = (ImageView) cl.findViewById(id);
            dice[i].num = i;
        }
    }

    //주사위 굴리기
    public void rollDice(final ImageView imageView, int i) {
        if (dice[i].keep == false) {
            Random rand = new Random();
            int r = rand.nextInt(6) + 1;
//            int id = getResources().getIdentifier("dice" + (i + 1), "id", "com.example.yachtdice");
            Handler delayHandler = new Handler();

            imageView.startAnimation(anim1);
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.startAnimation(anim2);
                }
            }, 250);
            value[i] = r;
            imageView.setImageResource(getResources().getIdentifier("dice" + r, "drawable", "com.example.yachtdice"));
        }
        if (rollCount == 2) {
            imageView.setY(1150);
            dice[i].keep = false;
        }
    }

    //주사위 킵
    public void keepDice(ImageView diceView) {
        DiceInfo d = getDice(diceView.getId());
        if (d.keep == true) {
            d.keep = false;
            diceView.setY(1150);
        } else {
            d.keep = true;
            diceView.setY(265);
        }
    }

    //주사위 값 반환
    public int[] getDiceValues() {
//        int[] values = new int[diceNumber];
//        for (int i = 0; i < diceNumber; i++) {
//            values[i] = dice[i].value;
//        }
//        return values;
        return value;
    }

    // 객체 id로 주사위 찾기
    public DiceInfo getDice(int id) {
        for (int i = 0; i < diceNumber; i++) {
            if (dice[i].id == id) {
                return dice[i];
            }
        }
        // 찾기 오류
        return new DiceInfo(0);
    }
}
