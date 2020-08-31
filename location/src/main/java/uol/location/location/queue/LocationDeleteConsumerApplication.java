package uol.location.location.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uol.location.location.application.LocationApplication;

import java.io.IOException;

@Component
@RabbitListener(queues = "location-delete")
public class LocationDeleteConsumerApplication {
    private final LocationApplication locationApplication;

    public LocationDeleteConsumerApplication(LocationApplication locationApplication){
        this.locationApplication = locationApplication;
    }

    @RabbitHandler
    public void receive(String value) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Long id = objectMapper.readValue(value, Long.class);
        boolean deleted = locationApplication.deleteById(id);
        if (!deleted) {
            throw new ImmediateRequeueAmqpException(value);
        }
    }
}