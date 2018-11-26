package com.maxim.maxim.fastrun;

import java.util.ArrayList;

public class EnemiesMovementManager {
    private ArrayList<Enemy> arrList;
    private int speed = 0;

    public void setArrList(ArrayList<Enemy> arrList) {
        this.arrList = arrList;
    }

    public void moveAllElements() {
        for(int i = 0; i < arrList.size(); i++) {
            int x = arrList.get(i).getEnemyPosX();
            x -= speed;
            arrList.get(i).setEnemyPosX(x);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
