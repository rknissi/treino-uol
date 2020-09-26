package uol.treino.person.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LocationCreationProducerApplication {

    private final RabbitTemplate template;

    @Qualifier("location-creation")
    @Autowired
    private Queue queue;

    @Qualifier("binding-creation")
    @Autowired
    private Binding binding;

    public LocationCreationProducerApplication(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(String ipv4, Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //this.template.convertAndSend(binding.getExchange(), queue.getName(), objectMapper.writeValueAsString(new LocationCreationMessage(ipv4, id)));
            this.template.convertAndSend(binding.getExchange(), binding.getRoutingKey(), objectMapper.writeValueAsString(new LocationCreationMessage(ipv4, id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

