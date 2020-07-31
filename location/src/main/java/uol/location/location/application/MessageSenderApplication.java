package uol.location.location.application;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationQueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class MessageSenderApplication {

    private final static String QUEUE_NAME = "create-weather";
    private ConnectionFactory factory;

    public MessageSenderApplication(@Value("${rabbitmq.url}") String rabbitmqUrl, @Value("${rabbitmq.port}") String rabbitmqPort) {
        factory = new ConnectionFactory();
        factory.setHost(rabbitmqUrl);
        factory.setPort(Integer.parseInt(rabbitmqPort));
    }
    public void sendMessage(String ipv4, Long id) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(new LocationQueue(ipv4, id)));
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

