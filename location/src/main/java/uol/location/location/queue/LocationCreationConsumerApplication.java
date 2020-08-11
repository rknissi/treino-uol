package uol.location.location.queue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uol.location.location.application.LocationApplication;
import uol.location.location.repository.entity.LocationRepositoryEntity;

@Component
@RabbitListener(queues = "location-creation")
public class LocationCreationConsumerApplication {
    private final LocationApplication locationApplication;

    public LocationCreationConsumerApplication(LocationApplication locationApplication){
        this.locationApplication = locationApplication;
    }

    @RabbitHandler
    public void receive(LocationCreationMessage locationCreationMessage) {
        LocationRepositoryEntity locationRepositoryEntity = new LocationRepositoryEntity();
        locationRepositoryEntity.setId(locationCreationMessage.getId());
        locationApplication.populateData(locationCreationMessage.getIp(), locationCreationMessage.getId());
    }

}