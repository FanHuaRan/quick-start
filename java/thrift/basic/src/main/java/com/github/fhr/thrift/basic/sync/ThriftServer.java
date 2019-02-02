package com.github.fhr.thrift.basic.sync;

import com.github.fhr.thrift.basic.service.DemoService;
import com.github.fhr.thrift.basic.service.DemoServiceImpl;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Fan Huaran
 * created on 2019/1/31
 * @description
 */
public class ThriftServer {

    private static final Logger logger = LoggerFactory.getLogger(ThriftServer.class);

    public static void main(String[] args) {
        DemoService.Iface demoService = new DemoServiceImpl();

        DemoService.Processor processor = new DemoService.Processor(demoService);

        Thread thread = new Thread(() -> {
            try {
                TServerTransport transport = new TServerSocket(9123);
                TServer server = new TSimpleServer(new TServer.Args(transport)
                        .processor(processor));

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (server.isServing()) {
                        logger.info("closing the simple server......");
                        server.stop();
                    }
                }));
                logger.info("start the simple server......");
                server.serve();
            } catch (TTransportException e) {
                e.printStackTrace();
                logger.error("client server is wrong.", e);
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
