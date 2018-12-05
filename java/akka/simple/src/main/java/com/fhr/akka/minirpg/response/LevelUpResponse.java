package com.fhr.akka.minirpg.response;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class LevelUpResponse implements GameResponse {
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
