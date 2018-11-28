package com.fhr.akka.minirpg;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class CreatePlayerResponse {

    private int newPlayerId;

    public CreatePlayerResponse(int newPlayerId) {
        this.newPlayerId = newPlayerId;
    }

    public int getNewPlayerId() {
        return newPlayerId;
    }

    public void setNewPlayerId(int newPlayerId) {
        this.newPlayerId = newPlayerId;
    }
}
