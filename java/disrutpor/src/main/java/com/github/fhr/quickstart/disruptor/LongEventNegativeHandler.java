package com.github.fhr.quickstart.disruptor;


import com.lmax.disruptor.EventHandler;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEventNegativeHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.printf("negative Event:%s,sequence:%s,endOfBatch:%s\n", 0 - event.getValue(), sequence, endOfBatch);
    }
}
