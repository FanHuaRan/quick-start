package com.github.fhr.quickstart.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer arg0) {
            event.set(arg0.getInt());
        }
    };

    public void onData(ByteBuffer byteBuffer){
        ringBuffer.publishEvent(TRANSLATOR, byteBuffer);
    }
}
