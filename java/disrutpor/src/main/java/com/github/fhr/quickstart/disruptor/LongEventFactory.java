package com.github.fhr.quickstart.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
