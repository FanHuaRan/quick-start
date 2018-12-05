package com.fhr.akka.minirpg.request;

import com.fhr.akka.minirpg.GameMessage;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class CreatePlayerRequest implements GameRequest {

    private final String playerName;

    public CreatePlayerRequest(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
