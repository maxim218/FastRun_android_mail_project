package com.maxim.maxim.fastrun;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    JumpControl jumpControl = null;
    EnemiesGenerator enemiesGenerator = null;
    int currentSpeed = 12;
    EnemiesMovementManager enemiesMovementManager = null;
    int heroPosY = 0;
    int heroPosX = 0;
    int heroSize = 0;
    DrawView drawView = null;
    int timeCountCurrent = 0;
    int timeCountMax = 90;
    int timeCountMin = 50;
    int timeDelta = 2;
    EnemiesKiller enemiesKiller = null;
    boolean gameStop = false;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        gameStop = false;
        score = 0;

        drawView = new DrawView(this);
        setContentView(drawView);

        int deltaHeight = 250;
        Display display = getWindowManager().getDefaultDisplay();
        int game_width = display.getWidth();
        int game_height = display.getHeight() - deltaHeight;

        drawView.initGameSize(game_width, game_height);

        heroPosX = 110;
        heroSize = 50;
        heroPosY = game_height - heroSize;

        drawView.initHeroSize(heroSize);
        drawView.setHeroPosition(heroPosX, heroPosY);

        int enemySize = heroSize;
        int startGenerateX = game_width - enemySize * 5;
        enemiesGenerator = new EnemiesGenerator();
        enemiesGenerator.initEnemySize(enemySize);
        enemiesGenerator.initArrayList();
        enemiesGenerator.initRandom();
        enemiesGenerator.setStartGenerateX(startGenerateX);
        enemiesGenerator.setGround(game_height);
        enemiesGenerator.countPositions();

        drawView.initEnemiesArrList(enemiesGenerator.getArrList());

        enemiesMovementManager = new EnemiesMovementManager();
        enemiesMovementManager.setArrList(enemiesGenerator.getArrList());
        enemiesMovementManager.setSpeed(currentSpeed);

        drawView.invalidate();

        jumpControl = new JumpControl();
        jumpControl.initPhysicsParams(20, 1);
        jumpControl.initStartingParams(game_height, heroPosY, heroSize);

        enemiesKiller = new EnemiesKiller();
        int leftPosition = enemySize * 2 * (-1);
        enemiesKiller.setLeftPosition(leftPosition);
        enemiesKiller.setArrList(enemiesGenerator.getArrList());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpControl.jump();
            }
        };
        drawView.setOnClickListener(listener);

        makeWorkingInterval();

        enemiesGenerator.createEnemiesGroup();
    }

    private void makeWorkingInterval() {
        final int waitTime = 20;
        final Handler handler = new Handler();
        handler.post (new Runnable(){
            @Override
            public void run(){
                if(!gameStop) {
                    gameProcess();
                }
                handler.postDelayed(this, waitTime);
            }
        });
    }

    public void controlHitHeroAndEnemies() {
        ArrayList<Enemy> arrList = enemiesKiller.getArrList();
        for(int i = 0; i < arrList.size(); i++) {
            int x = arrList.get(i).getEnemyPosX();
            int y = arrList.get(i).getEnemyPosY();
            int dx = heroPosX - x;
            int dy = heroPosY - y;
            int distance = (int) Math.floor(Math.sqrt(dx * dx + dy * dy));
            if(distance < heroSize * 0.95) {
                gameStop = true;
                score++;
                drawView.setScoreValue(score);
                drawView.invalidate();
                saveScore(score);
            }
        }
    }

    public void saveScore(int scoreValue) {
        int minScore = 90;
        if(scoreValue > minScore) {
            DataWorker dataWorker = new DataWorker();
            dataWorker.saveData(getApplicationContext(), Integer.toString(scoreValue));
        }
    }

    public void gameProcess() {
        enemiesKiller.destroyEnemies();
        enemiesGenerator.setArrList(enemiesKiller.getArrList());
        enemiesMovementManager.setArrList(enemiesKiller.getArrList());
        drawView.initEnemiesArrList(enemiesKiller.getArrList());

        jumpControl.move();
        heroPosY = jumpControl.getHeroY();
        drawView.setHeroPosition(heroPosX, heroPosY);
        enemiesMovementManager.moveAllElements();
        timeCountCurrent++;
        if(timeCountCurrent >= timeCountMax) {
            timeCountCurrent = 0;
            timeCountMax -= timeDelta;
            if(timeCountMax < timeCountMin) {
                timeCountMax = timeCountMin;
            }
            enemiesGenerator.createEnemiesGroup();
        }

        score++;
        drawView.setScoreValue(score);

        controlHitHeroAndEnemies();
        drawView.invalidate();
    }

    class DrawView extends View {
        Paint p;

        private int game_width = 1;
        private int game_height = 1;

        private int hero_x = 0;
        private int hero_y = 0;
        private int hero_size = 0;

        private int scoreValue = 0;

        public void setScoreValue(int scoreParam) {
            scoreValue = scoreParam;
        }

        private void writeScore(Canvas canvas) {
            p.setColor(Color.WHITE);
            p.setTextSize(35);
            String content = "Score: " + scoreValue;
            int x = hero_x;
            int y = 110;
            canvas.drawText(content, x, y, p);
        }

        private ArrayList<Enemy> enemyArrList = null;

        public void initEnemiesArrList(ArrayList<Enemy> enemyArrListParam) {
            enemyArrList = enemyArrListParam;
        }

        private void printAllEnemies(Canvas canvas) {
            p.setColor(Color.WHITE);
            p.setStrokeWidth(1);
            for(int i = 0; i < enemyArrList.size(); i++) {
                int x = enemyArrList.get(i).getEnemyPosX();
                int y = enemyArrList.get(i).getEnemyPosY();
                int size = enemyArrList.get(i).getEnemySize();
                int right = x + size;
                int bottom = y + size;
                canvas.drawRect(x, y, right, bottom, p);
            }
        }

        public void initHeroSize(int size) {
            hero_size = size;
        }

        public void setHeroPosition(int x, int y) {
            hero_x = x;
            hero_y = y;
        }

        public DrawView(Context context) {
            super(context);
            p = new Paint();
        }

        public void initGameSize(int width, int height) {
            game_width = width;
            game_height = height;
        }

        private void drawGround(Canvas canvas) {
            p.setColor(Color.WHITE);
            p.setStrokeWidth(2);
            int groundLinePosY = game_height;
            int groundLineStartPosX = 0;
            int groundLineFinishPosX = game_width;
            canvas.drawLine(groundLineStartPosX, groundLinePosY, groundLineFinishPosX, groundLinePosY, p);
        }

        private void drawHero(Canvas canvas) {
            p.setColor(Color.BLACK);
            p.setStrokeWidth(1);
            int heroRight = hero_x + hero_size;
            int heroBottom = hero_y + hero_size;
            canvas.drawRect(hero_x, hero_y, heroRight, heroBottom, p);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(255,46, 139, 87);
            drawGround(canvas);
            printAllEnemies(canvas);
            drawHero(canvas);
            writeScore(canvas);
        }
    }
}
