package com.github.fhr.thrift.basic.sync;

import com.github.fhr.thrift.basic.dto.QueryParameter;
import com.github.fhr.thrift.basic.service.DemoService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fan Huaran
 * created on 2019/1/31
 * @description
 */
public class ThriftClient {

    private static final Logger logger = LoggerFactory.getLogger(ThriftClient.class);

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9123);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            DemoService.Client client = new DemoService.Client(protocol);

            logger.info("call the ping: {}", client.ping());

            int max = 100000;
            Long start = System.currentTimeMillis();

            for (int i = 0; i < max; i++) {
                call(client);
            }

            Long end = System.currentTimeMillis();
            Long elapse = end - start;
            double perform = max / (elapse / 1000d);
            logger.info("client {} 次RPC调用，耗时：{} 毫秒，平均 {} 次/秒", max, elapse, perform);
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
            logger.error("client client is wrong.", e);
        }
    }

    private static void call(DemoService.Client client) throws TException {
        QueryParameter parameter = new QueryParameter();
        parameter.setAgeStart(Short.valueOf("5"));
        parameter.setAgeEnd(Short.valueOf("50"));

        client.getPersonList(parameter);
    }
}
