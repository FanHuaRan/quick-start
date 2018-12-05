package com.fhr.akka.minirpg.request;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class GetPlayerInfoRequest implements GameRequest{
    private final int playerId;

    public GetPlayerInfoRequest(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
