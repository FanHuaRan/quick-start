package com.github.fhr.thrift.basic.async;

import com.github.fhr.thrift.basic.service.DemoService;
import com.github.fhr.thrift.basic.service.DemoServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author Fan Huaran
 * created on 2019/1/31
 * @description
 */
public class AsyncThriftServer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncThriftServer.class);

    public static void main(String[] args) {
        DemoService.Iface demoService = new DemoServiceImpl();

        DemoService.Processor processor = new DemoService.Processor(demoService);

        Thread thread = new Thread(() -> {
            try {
                TNonblockingServerTransport transport = new TNonblockingServerSocket(9123);
                TServer server = new TNonblockingServer(new TNonblockingServer.Args(transport)
                        .processor(processor)
                        .transportFactory(new TFramedTransport.Factory())
                        .protocolFactory(new TBinaryProtocol.Factory()));

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (server.isServing()) {
                        logger.info("closing the simple server......");
                        server.stop();
                    }
                }));
                logger.info("start the non blocking server......");
                server.serve();
            } catch (TTransportException e) {
                e.printStackTrace();
                logger.error("server is wrong.", e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
