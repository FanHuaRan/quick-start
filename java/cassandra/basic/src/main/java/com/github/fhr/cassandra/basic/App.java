package com.github.fhr.cassandra.basic;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author Fan Huaran
 * created on 2019/1/29
 * @description
 */
public class App {
    public static void main(String[] args) throws TTransportException {
        TTransport transport = new TSocket("localhost", 9160);
        TProtocol protocol = new TBinaryProtocol(transport);
        Cassandra.Client client = new Cassandra.Client(protocol);
        transport.open();
        String keyspace = "mykeyspace";

    }
}
