package com.github.fhr.quickstart.disruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.printf("Event:%s,sequence:%s,endOfBatch:%s\n", event.getValue(), sequence, endOfBatch);
    }
}
