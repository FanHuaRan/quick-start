package com.github.fhr.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Fan Huaran
 * created on 2019/3/14
 * @description
 */
public class BulkAdd {

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

//        client.prepareBulk();
        for (int i = 20; i < 100; i++) {
            client.prepareIndex("testindex", "staff")
                    .setSource("staff_id", i, "staff_age", 10).get();
        }
    }

}
