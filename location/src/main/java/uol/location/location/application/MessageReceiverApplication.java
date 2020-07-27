package uol.location.location.application;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationQueue;
import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageReceiverApplication {
    private final LocationApplication locationApplication;
    private final static String QUEUE_NAME = "create-weather";

    public MessageReceiverApplication (LocationApplication locationApplication) throws IOException, TimeoutException {
        this.locationApplication = locationApplication;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = null;
        connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            LocationQueue locationQueue = (LocationQueue) SerializationUtils.deserialize(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
            locationRepositoryEntity.setId(locationQueue.getId());
            locationApplication.populateData(locationQueue.getIp(), locationRepositoryEntity);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}