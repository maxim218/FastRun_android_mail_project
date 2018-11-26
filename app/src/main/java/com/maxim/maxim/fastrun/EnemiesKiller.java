package com.maxim.maxim.fastrun;

import java.util.ArrayList;

public class EnemiesKiller {
    private ArrayList<Enemy> arrList;

    public void setArrList(ArrayList<Enemy> arrList) {
        this.arrList = arrList;
    }

    public ArrayList<Enemy> getArrList() {
        return arrList;
    }

    private int leftPosition = 0;

    public void destroyEnemies() {
        ArrayList<Enemy> buffer = new ArrayList<Enemy>();
        for(int i = 0; i < arrList.size(); i++) {
            int x = arrList.get(i).getEnemyPosX();
            if(x > leftPosition) {
                buffer.add(arrList.get(i));
            }
        }
        arrList = buffer;
    }

    public void setLeftPosition(int leftPosition) {
        this.leftPosition = leftPosition;
    }
}
