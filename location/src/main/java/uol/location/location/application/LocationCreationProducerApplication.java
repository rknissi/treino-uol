package uol.location.location.application;

import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationCreationQueueConfiguration;
import uol.location.location.queue.LocationCreationMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class LocationCreationProducerApplication {

    private final static String QUEUE_NAME = "create-weather";
    private final LocationCreationQueueConfiguration locationCreationQueueConfiguration;

    public LocationCreationProducerApplication(LocationCreationQueueConfiguration locationCreationQueueConfiguration) {
        this.locationCreationQueueConfiguration = locationCreationQueueConfiguration;
    }

    public void sendMessage(String ipv4, Long id) {
        try (Channel channel = locationCreationQueueConfiguration.createConnection()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(new LocationCreationMessage(ipv4, id)));
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

