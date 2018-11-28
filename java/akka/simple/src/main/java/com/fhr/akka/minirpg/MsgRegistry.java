package com.fhr.akka.minirpg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class MsgRegistry {
    private static final Map<Integer, Class<?>> msgById = new HashMap<>();
    private static final Map<Class<?>, Integer> idByMsg = new HashMap<>();
    static {
//        register(1, CreatePlayerRequest.class);
//        register(2, CreatePlayerResponse.class);
//        register(3, AddExpRequest.class);
//        register(4, AddExpResponse.class);
//        register(5, LevelUpRequest.class);
//        register(6, LevelUpResponse.class);
//        register(7, GetPlayerInfoRequest.class);
//        register(8, GetPlayerInfoResponse.class);
    }

    private static void register(int msgId, Class<?> msgClass) {
        msgById.put(msgId, msgClass);
        idByMsg.put(msgClass, msgId);
    }

    public static Class<?> getMsgClass(int msgId) {
        return msgById.get(msgId);
    }

    public static int getMsgId(Class<?> msgClass) {
        return idByMsg.get(msgClass);
    }

    public static int getMsgId(Object msg) {
        return getMsgId(msg.getClass());
    }

}
