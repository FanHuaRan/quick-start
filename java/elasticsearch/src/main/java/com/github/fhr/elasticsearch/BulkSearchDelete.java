package com.github.fhr.elasticsearch;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author Fan Huaran
 * created on 2019/3/14
 * @description
 */
public class BulkSearchDelete {

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        SearchHits searchHits = client.prepareSearch("testindex").setTypes("staff").execute().actionGet().getHits();
        System.out.println(searchHits.getHits());


        System.out.println("scroll 模式启动！");
        QueryBuilder query = QueryBuilders.matchAllQuery();
        SearchResponse scrollResponse = client
                .prepareSearch("testindex")
                .setTypes("staff")
                .setQuery(query)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setSize(3)
                .setScroll(TimeValue.timeValueMinutes(8))
                .execute()
                .actionGet();

        long total = scrollResponse.getHits().getTotalHits();
        System.out.println("total:" + total);

        while (scrollResponse.getHits().getHits().length > 0) {
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

            long currentCount = scrollResponse.getHits().getHits().length;
            System.out.println("current count:" + currentCount);
            System.out.println("current total:" + scrollResponse.getHits().totalHits);

            for (SearchHit searchHit : scrollResponse.getHits().getHits()) {
//                String sourceString = searchHit.getSourceAsString();
//                System.out.println(sourceString);
                Map<String, Object> sourceValues = searchHit.getSourceAsMap();
                System.out.println("staff_id:" + sourceValues.get("staff_id"));

//                if (((int) sourceValues.get("staff_id")) % 3 == 0) {
//                    client.prepareDelete("testindex", "staff", searchHit.getId()).get();
//                    System.out.println("delete staff_id::" + sourceValues.get("staff_id"));
//                }

                bulkRequestBuilder.add(client.prepareDelete("testindex", "staff", searchHit.getId()));
            }

            BulkResponse bulkResponse = bulkRequestBuilder.get();
            if(bulkResponse.hasFailures()){
                System.out.println("has failures:");
            }

            scrollResponse = client.prepareSearchScroll(scrollResponse.getScrollId())
                    .setScroll(TimeValue.timeValueMinutes(8))
                    .execute()
                    .actionGet();
        }
    }
}
