package com.github.fhr.cassandra.basic;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * @author Fan Huaran
 * created on 2019/1/29
 * @description
 */
public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    public void connect(String node, Integer port){
        Cluster.Builder builder =   Cluster.builder().addContactPoint(node);
        if(port != null){
            builder.withPort(port);
        }

        cluster = builder.build();
        session = cluster.connect();
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Session getSession() {
        return session;
    }

    public void close(){
        session.close();
        cluster.close();
    }

}
