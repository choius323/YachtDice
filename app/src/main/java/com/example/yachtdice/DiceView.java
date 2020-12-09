package com.example.yachtdice;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

import java.util.Random;

public class DiceView extends ConstraintLayout {


    //주사위 각각의 정보
    class DiceInfo {
        ImageView img;
        Animation anim;
        int value = 1; //주사위 눈 값
        Boolean keep = false; //킵하는지 저장
        int id;

        public DiceInfo(int id) {
            this.id = id;
            value = 1;
            keep = false;
            img = (ImageView) findViewById(id);
        }
    }

    final Animation anim1 = AnimationUtils.loadAnimation(getContext(),R.anim.rotate);
    final Animation anim2 = AnimationUtils.loadAnimation(getContext(),R.anim.rotate2);
    final Animation anim3 = AnimationUtils.loadAnimation(getContext(),R.anim.keeping_dice);
    final Animation anim4 = AnimationUtils.loadAnimation(getContext(),R.anim.translate);






    ConstraintLayout cl;
    private final int diceNumber = 5;
    public int rollcount=0;
    public int Chance=2;
    DiceInfo dice[];
    //주사위 눈 바뀌는 애니메이션
    private final int[] ani = new int[]
            {R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4, R.drawable.anim5, R.drawable.anim6};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, AttributeSet attrs, int defStyleAttrs){
        super(context, attrs, defStyleAttrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflate(getContext(), R.layout.dice_view, (ViewGroup) getParent());
        cl = (ConstraintLayout) inflater.inflate(R.layout.dice_view, null);
        // 메인에 다이스 뷰 등록
        View view = View.inflate(context, R.layout.dice_view, this);

        //주사위 5개 객체 생성
        dice = new DiceInfo[diceNumber];
        int id = 0;
        for (int i = 0; i < diceNumber; i++) {
            id = getResources().getIdentifier("dice"+(i+1), "id", "com.example.yachtdice");
//            id = getResources().getIdentifier("dice1", "id", "com.example.yachtdice");
            dice[i] = new DiceInfo(id);
            dice[i].img = (ImageView) cl.findViewById(id);
        }
    }

    //주사위 굴리기
    public void rollDice(final ImageView imageView, int i) {


        if(dice[i].keep == false){
            Random rand = new Random();
            int r = rand.nextInt(6)+1;
           // int resID = getResources().getIdentifier("dice"+r,"drawable","com.example.yachtdice");
            Handler delayHandler = new Handler();

            imageView.startAnimation(anim1);
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.startAnimation(anim2);
                }
            },250);
            dice[i].value = r;
            imageView.setImageResource(ani[r-1]);
        }
        if(rollcount==2){
            imageView.setY(1150);
            dice[i].keep = false;
        }
    }

    //주사위 킵
    public void keepDice(ImageView diceView) {
        DiceInfo d = getDice(diceView.getId());
        if(d.keep == true){
            d.keep = false;
            diceView.setY(1150);
        } else {
            d.keep = true;
            diceView.setY(265);

        }
        // 애니메이션 추가 필요
    }

    //주사위 값 반환
    public int[] getDiceValues() {
        int[] values = new int[diceNumber];
        for (int i = 0; i < diceNumber; i++) {
            values[i] = dice[i].value;
        }
        return values;
    }

    // 객체 id로 주사위 찾기
    public DiceInfo getDice(int id){
        for(int i=0; i<diceNumber;i++){
            if(dice[i].id == id){
                return dice[i];
            }
        }
        // 찾기 오류
        return new DiceInfo(0);
    }
}