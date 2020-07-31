package uol.location.location.application;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationQueue;
import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class MessageReceiverApplication {
    private final LocationApplication locationApplication;
    private final static String QUEUE_NAME = "create-weather";

    public MessageReceiverApplication (LocationApplication locationApplication,
                                       @Value("${rabbitmq.url}") String rabbitmqUrl,
                                       @Value("${rabbitmq.port}") String rabbitmqPort) throws IOException, TimeoutException {
        this.locationApplication = locationApplication;

        ConnectionFactory factory;
        if (rabbitmqPort.equals("8089")) {
            factory = new MockConnectionFactory();

        } else {
            factory = new ConnectionFactory();
            factory.setHost(rabbitmqUrl);
            factory.setPort(Integer.parseInt(rabbitmqPort));
        }
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            LocationQueue locationQueue = (LocationQueue) SerializationUtils.deserialize(delivery.getBody());
            LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
            locationRepositoryEntity.setId(locationQueue.getId());
            locationApplication.populateData(locationQueue.getIp(), locationQueue.getId());
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}