package com.github.fhr.quickstart.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEventMain {

    public static void main(String[] args) throws InterruptedException {
        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);
        // Connect the handler
//        disruptor.handleEventsWith(new LongEventHandler())
//                .handleEventsWith(new LongEventNegativeHandler());

        disruptor.handleEventsWith(new LongEventHandler())
                .then(new LongEventNegativeHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        for (int i = 0; true; i++) {
            ByteBuffer bb = ByteBuffer.allocateDirect(4);
            bb.putInt(0, i);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }

}
