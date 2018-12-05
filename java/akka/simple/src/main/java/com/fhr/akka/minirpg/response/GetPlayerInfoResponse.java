package com.fhr.akka.minirpg.response;

import com.fhr.akka.minirpg.PlayerInfo;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class GetPlayerInfoResponse  implements GameResponse{
    private PlayerInfo playerInfo;

    public GetPlayerInfoResponse(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
}
