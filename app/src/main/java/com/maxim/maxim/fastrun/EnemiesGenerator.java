package com.maxim.maxim.fastrun;

import java.util.ArrayList;
import java.util.Random;

public class EnemiesGenerator {
    private ArrayList<Enemy> arrList;
    private Random rnd;
    private int enemySize = 0;

    public void setArrList(ArrayList<Enemy> arrList) {
        this.arrList = arrList;
    }

    public void initEnemySize(int size) {
        enemySize = size;
    }

    public void initArrayList() {
        arrList = new ArrayList<Enemy>();
    }

    public void initRandom() {
        rnd = new Random();
    }

    public ArrayList<Enemy> getArrList() {
        return arrList;
    }

    public int getRandomNumber(int border) {
        return rnd.nextInt(border);
    }

    private void createEnemy(int x, int y) {
        Enemy enemy = new Enemy();
        enemy.setEnemyPosX(x);
        enemy.setEnemyPosY(y);
        enemy.setEnemySize(enemySize);
        arrList.add(enemy);
    }

    private int startGenerateX = 0;

    public void setStartGenerateX(int startGenerateX) {
        this.startGenerateX = startGenerateX;
    }

    private int px0, px1, px2;
    private int py1, py2, py3, py4;

    public void countPositions() {
        px0 = startGenerateX;
        px1 = startGenerateX + enemySize;
        px2 = startGenerateX + enemySize * 2;
        py1 = ground - enemySize;
        py2 = ground - enemySize * 2;
        py3 = ground - enemySize * 3;
        py4 = ground - enemySize * 4;
    }

    private int ground = 0;

    public void setGround(int ground) {
        this.ground = ground;
    }

    public void createEnemiesGroup() {
        int border = 8;
        int r = getRandomNumber(border);

        if(r == 0) {
            createEnemy(px0, py1);
            createEnemy(px0, py2);
            createEnemy(px1, py1);
            return;
        }

        if(r == 1) {
            createEnemy(px0, py1);
            createEnemy(px1, py2);
            createEnemy(px1, py1);
            return;
        }

        if(r == 2) {
            createEnemy(px0, py1);
            createEnemy(px1, py1);
            return;
        }

        if(r == 3) {
            createEnemy(px0, py2);
            createEnemy(px1, py2);
            createEnemy(px1, py3);
            createEnemy(px1, py4);
            createEnemy(px2, py4);
            return;
        }

        if(r == 4) {
            createEnemy(px0, py1);
            createEnemy(px1, py2);
            createEnemy(px1, py1);
            createEnemy(px2, py1);
            return;
        }

        if(r == 5) {
            createEnemy(px0, py2);
            createEnemy(px1, py2);
            createEnemy(px1, py3);
            createEnemy(px1, py4);
            createEnemy(px0, py4);
            return;
        }

        if(r == 6) {
            createEnemy(px0, py1);
            createEnemy(px0, py2);
            return;
        }

        if(r == 7) {
            createEnemy(px1, py1);
            createEnemy(px1, py2);
            createEnemy(px0, py2);
        }
    }
}
