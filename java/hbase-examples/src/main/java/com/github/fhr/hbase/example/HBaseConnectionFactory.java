package com.github.fhr.hbase.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author huaran
 * @since 2020/12/19
 **/
public class HBaseConnectionFactory {
    private final String zk;

    public HBaseConnectionFactory(String zk) {
        this.zk = zk;
    }

    public Connection getConnection() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zk);
        return ConnectionFactory.createConnection(configuration);
    }

}
