package uol.location.location.application;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationQueue;
import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageSenderApplication {

    private final static String QUEUE_NAME = "create-weather";
    private ConnectionFactory factory;

    public MessageSenderApplication() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
    }
    public void sendMessage(String ipv4, Long id) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(new LocationQueue(ipv4, id)));
            //System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

