package com.fhr.quickstart.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Fan Huaran
 * created on 2018/11/6
 * @description
 */
public class SimpleProducer {
    private static final String TOPIC = "helloworld";

    public static void main(String[] args) {
        Properties properties = new Properties();
        //客户端用于建立与kafka集群连接的host:port组，如果有多个broker,则用“,”隔开
//        "host1:port1,host2:port2,host3,post3"
        properties.put("bootstrap.servers", "127.0.0.1:9092");
//        producer在向servers发送信息后，是否需要serveres向客户端（producer）反馈接受消息状态用此参数配置
//        acks=0:表示producer不需要等待集群服务器发送的确认消息；acks=1:表示producer需要等到topic对应的leader发送的消息确认；
//        acks=all:表示producer需要等到leader以及所有followers的消息确认，这是最安全的消息保障机制
        properties.put("acks", "all");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("buffer.memory", "33554432");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        Date startDate = new Date();

        for (int i = 0; i < 100000; i++) {

            String message = "Sync : this is the " + i + "th message for test!";
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC, message);
            producer.send(producerRecord);
        }

        System.out.println(new Date().getTime() - startDate.getTime());

        producer.close();
    }
}
