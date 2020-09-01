package uol.location.location.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import uol.location.location.application.LocationApplication;

@Component
@RabbitListener(queues = "location-creation")
public class LocationCreationConsumerApplication {
    private final LocationApplication locationApplication;

    public LocationCreationConsumerApplication(LocationApplication locationApplication){
        this.locationApplication = locationApplication;
    }

    @RabbitHandler
    public void receive(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LocationCreationMessage locationCreationMessage = objectMapper.readValue(value, LocationCreationMessage.class);
        try {
            locationApplication.populateData(locationCreationMessage);
            System.out.println("Testeeeeeeeeee!");
        } catch (HttpClientErrorException e) {
            System.out.println("Teste!");
            throw new ImmediateRequeueAmqpException(value);
        }
    }
}