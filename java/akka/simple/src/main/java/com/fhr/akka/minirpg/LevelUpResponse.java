package com.fhr.akka.minirpg;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class LevelUpResponse {
    private int newLevel;

    public LevelUpResponse(int newLevel) {
        this.newLevel = newLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }
}
