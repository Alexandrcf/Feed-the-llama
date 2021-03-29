package com.example.feedthellama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivityGame extends AppCompatActivity {

    private ImageButton buttonLeft, buttonUp, buttonDown, buttonRight;
    private RelativeLayout relativeLayout;
    private Random random = new Random();
    private boolean isCheck = true;
    private ImageView grassView;
    private ImageView llamaView;
    private TextView tvCount, tvTimer;
    private float xLlama;
    private float yLlama;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        buttonActions();
        timer();

    }

    private void buttonActions() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.button_left:
                        if (llamaView.getX() > 0) {
                            llamaView.setX(xLlama - 50);
                            llamaView.setY(yLlama);
                            xLlama = llamaView.getX();
                            yLlama = llamaView.getY();
                            checkCoordinates();
                            break;
                        }
                        break;
                    case R.id.button_up:
                        if (llamaView.getY() > 0){
                            llamaView.setX(xLlama);
                            llamaView.setY(yLlama - 50);
                            xLlama = llamaView.getX();
                            yLlama = llamaView.getY();
                            checkCoordinates();
                            break;
                        }
                        break;
                    case R.id.button_down:
                        if (llamaView.getY() < relativeLayout.getHeight() - llamaView.getHeight()){
                            llamaView.setX(xLlama);
                            llamaView.setY(yLlama + 50);
                            xLlama = llamaView.getX();
                            yLlama = llamaView.getY();
                            checkCoordinates();
                            break;
                        }
                        break;
                    case R.id.button_right:
                        if (llamaView.getX() < relativeLayout.getWidth() - llamaView.getWidth()){
                            llamaView.setX(xLlama + 50);
                            llamaView.setY(yLlama);
                            xLlama = llamaView.getX();
                            yLlama = llamaView.getY();
                            checkCoordinates();
                            break;
                        }
                        break;



                }
            }
        };
        buttonLeft.setOnClickListener(onClickListener);
        buttonUp.setOnClickListener(onClickListener);
        buttonDown.setOnClickListener(onClickListener);
        buttonRight.setOnClickListener(onClickListener);
    }

    private void checkCoordinates() {
        float x1Llama = llamaView.getX();
        float x2Llama = llamaView.getX() + llamaView.getWidth();
        float y1Llama = llamaView.getY();
        float y2Llama = llamaView.getY() + llamaView.getHeight();

        float x1Grass = grassView.getX();
        float x2Grass = grassView.getX() + grassView.getWidth();
        float y1Grass = grassView.getY();
        float y2Grass = grassView.getY() + grassView.getHeight();

        if ((x1Llama >= x1Grass && x1Llama <= x2Grass || x2Llama >= x1Grass && x2Llama <= x2Grass) &&
        (y1Llama >= y1Grass && y1Llama <= y2Grass || y2Llama >= y1Grass && y2Llama <= y2Grass)) {
            count++;
            tvCount.setText("Съедено травы: " + count);
            relativeLayout.removeView(grassView);
            createViewGrass();
        }
    }

    private void initView() {
        buttonLeft = (ImageButton) findViewById(R.id.button_left);
        buttonUp = (ImageButton) findViewById(R.id.button_up);
        buttonDown = (ImageButton) findViewById(R.id.button_down);
        buttonRight = (ImageButton) findViewById(R.id.button_right);

        relativeLayout = (RelativeLayout) findViewById(R.id.layout_game);

        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvCount = (TextView) findViewById(R.id.tv_count);

        tvCount.setText("Съедено травы: " + count);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (isCheck){
            createViewLlama();
            createViewGrass();
            isCheck = false;
        }
    }

    private void createViewGrass() {
        grassView = new ImageView(MainActivityGame.this);
        grassView.setImageResource(R.drawable.grass_img);

        float xGrass = random.nextInt(relativeLayout.getWidth() - 150);
        float yGrass = random.nextInt(relativeLayout.getHeight() - 150);
        relativeLayout.addView(grassView);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) grassView.getLayoutParams();
        params.leftMargin = (int) xGrass;
        params.topMargin = (int) yGrass;
        params.width = 150;
        params.height = 150;

        grassView.setLayoutParams(params);

    }

    private void createViewLlama() {
        llamaView = new ImageView(MainActivityGame.this);
        llamaView.setImageResource(R.drawable.llama);

        xLlama = random.nextInt(relativeLayout.getWidth() - 150);
        yLlama = random.nextInt(relativeLayout.getHeight() - 150);
        relativeLayout.addView(llamaView);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llamaView.getLayoutParams();
        params.leftMargin = (int) xLlama;
        params.topMargin = (int) yLlama;
        params.width = 150;
        params.height = 150;

        llamaView.setLayoutParams(params);
    }

    private void timer() {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millis) {
                tvTimer.setText("Время: " + (int) (millis / 1000) +" сек");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), EndGameActivity.class);
                intent.putExtra("value", count);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}