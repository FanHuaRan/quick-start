package com.fhr.rabbitmqdemo.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author FanHuaran
 * @description 消息消费者
 * @create 2018-04-26 12:58
 **/
public class RabbitConsumer {
    private static final String QUEUE_NAME = "queue_demo";
    private static final int PORT = 5672;
    private static final String IP_ADDRESS = "127.0.0.1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS,PORT)
        };

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");

        Connection connection = connectionFactory.newConnection(addresses);
        final Channel channel = connection.createChannel();
        // 设置最多接收未被ACK的消息的个数
        //channel.basicAck();
        // 消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("recv message:" + new String(body));

                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException er){
                    er.printStackTrace();
                }
                // 回复确认消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }

        };
        // 消费消息
        channel.basicConsume(QUEUE_NAME, consumer);
        TimeUnit.SECONDS.sleep(1);
        channel.close();
        connection.close();
    }
}
