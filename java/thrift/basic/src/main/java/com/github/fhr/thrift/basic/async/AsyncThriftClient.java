package com.github.fhr.thrift.basic.async;

import com.github.fhr.thrift.basic.dto.Person;
import com.github.fhr.thrift.basic.dto.QueryParameter;
import com.github.fhr.thrift.basic.service.DemoService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2019/1/31
 * @description
 */
public class AsyncThriftClient {
    private static final Logger logger = LoggerFactory.getLogger(AsyncThriftClient.class);

    public static void main(String[] args) throws IOException {
        try {
            TNonblockingTransport transport = new TNonblockingSocket("localhost", 9123);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            TAsyncClientManager clientManager = new TAsyncClientManager();
            DemoService.AsyncClient client = new DemoService.AsyncClient(protocolFactory, clientManager, transport);

            client.ping(new AsyncMethodCallback<String>() {
                @Override
                public void onComplete(String s) {
                    logger.info("call the ping: {}", s);
                }

                @Override
                public void onError(Exception e) {
                    logger.info("call the ping wrong", e);
                }
            });

            Thread.sleep(1000);

            int max = 100000;
            Long start = System.currentTimeMillis();

            for (int i = 0; i < max; i++) {
                asyncCall(client);
            }

            Long end = System.currentTimeMillis();
            Long elapse = end - start;
            double perform = max / (elapse / 1000d);
            logger.info("client {} 次RPC调用，耗时：{} 毫秒，平均 {} 次/秒", max, elapse, perform);
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
            logger.error("client client is wrong.", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static void asyncCall(DemoService.AsyncClient asyncClient) throws TException {
        QueryParameter parameter = new QueryParameter();
        parameter.setAgeStart(Short.valueOf("5"));
        parameter.setAgeEnd(Short.valueOf("50"));

        asyncClient.getPersonList(parameter, new AsyncMethodCallback<List<Person>>() {
            @Override
            public void onComplete(List<Person> people) {
                // ignore
            }

            @Override
            public void onError(Exception e) {
                logger.info("async execute wrong", e);
            }
        });
    }
}
