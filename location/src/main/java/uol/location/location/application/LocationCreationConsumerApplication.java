package uol.location.location.application;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;
import uol.location.location.queue.LocationCreationMessage;
import uol.location.location.queue.LocationCreationQueueConfiguration;
import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class LocationCreationConsumerApplication {
    private final LocationApplication locationApplication;
    private final LocationCreationQueueConfiguration locationCreationQueueConfiguration;
    private final static String QUEUE_NAME = "create-weather";

    public LocationCreationConsumerApplication(LocationApplication locationApplication, LocationCreationQueueConfiguration locationCreationQueueConfiguration){
        this.locationApplication = locationApplication;
        this.locationCreationQueueConfiguration = locationCreationQueueConfiguration;
    }

    public void consumeMessage() {
        try(Channel channel = locationCreationQueueConfiguration.createConnection()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                LocationCreationMessage locationCreationMessage = (LocationCreationMessage) SerializationUtils.deserialize(delivery.getBody());
                LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
                locationRepositoryEntity.setId(locationCreationMessage.getId());
                locationApplication.populateData(locationCreationMessage.getIp(), locationCreationMessage.getId());
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}