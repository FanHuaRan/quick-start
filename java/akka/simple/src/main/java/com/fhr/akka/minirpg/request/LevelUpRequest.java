package com.fhr.akka.minirpg.request;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class LevelUpRequest implements GameRequest{
    private final int playerId;

    public LevelUpRequest(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
