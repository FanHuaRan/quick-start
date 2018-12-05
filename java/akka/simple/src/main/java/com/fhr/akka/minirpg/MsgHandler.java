package com.fhr.akka.minirpg;

import akka.actor.UntypedActor;
import com.fhr.akka.minirpg.request.AddExpRequest;
import com.fhr.akka.minirpg.request.CreatePlayerRequest;
import com.fhr.akka.minirpg.request.GetPlayerInfoRequest;
import com.fhr.akka.minirpg.request.LevelUpRequest;
import com.fhr.akka.minirpg.response.AddExpResponse;
import com.fhr.akka.minirpg.response.CreatePlayerResponse;
import com.fhr.akka.minirpg.response.GetPlayerInfoResponse;
import com.fhr.akka.minirpg.response.LevelUpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description 游戏消息处理器
 */
public class MsgHandler extends UntypedActor {
    // 记录当前的玩家数量
    private final List<Player> players = new ArrayList<>();

    @Override
    public void onReceive(Object msg) {
        // 处理游戏消息
        if (msg instanceof CreatePlayerRequest) {
            int newPlayerId = createPlayer((CreatePlayerRequest) msg);
            getSender().tell(new CreatePlayerResponse(newPlayerId), getSelf());
        } else if (msg instanceof AddExpRequest) {
            int newExp = addExpToPlayer((AddExpRequest) msg);
            getSender().tell(new AddExpResponse(newExp), getSelf());
        } else if (msg instanceof LevelUpRequest) {
            int newLevel = levelUpPlayer((LevelUpRequest) msg);
            getSender().tell(new LevelUpResponse(newLevel), getSelf());
        } else if (msg instanceof GetPlayerInfoRequest) {
            PlayerInfo playerInfo = getPlayerInfo((GetPlayerInfoRequest) msg);
            getSender().tell(new GetPlayerInfoResponse(playerInfo), getSelf());
        }
    }

    private int createPlayer(CreatePlayerRequest req) {
        int playerId = players.size() + 1;
        Player newPlayer = new Player();
        newPlayer.setId(playerId);
        newPlayer.setLevel(1);
        newPlayer.setName(req.getPlayerName());
        players.add(newPlayer);
        return playerId;
    }

    private int addExpToPlayer(AddExpRequest req) {
        Player player = players.get(req.getPlayerId());
        player.addExp(req.getExp());
        return player.getExp();
    }

    private int levelUpPlayer(LevelUpRequest req) {
        Player player = players.get(req.getPlayerId());
        player.levelUp();
        return player.getLevel();
    }

    private PlayerInfo getPlayerInfo(GetPlayerInfoRequest req) {
        Player player = players.get(req.getPlayerId());
        return new PlayerInfo(player.getId(), player.getName(), player.getExp(), player.getLevel());
    }
}

