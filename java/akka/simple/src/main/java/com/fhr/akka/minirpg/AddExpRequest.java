package com.fhr.akka.minirpg;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class AddExpRequest {
    private final int playerId;

    private final int exp;

    public AddExpRequest(int playerId, int exp) {
        this.playerId = playerId;
        this.exp = exp;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getExp() {
        return exp;
    }
}
