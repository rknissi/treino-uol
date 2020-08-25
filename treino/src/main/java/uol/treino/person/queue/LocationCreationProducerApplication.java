package uol.treino.person.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocationCreationProducerApplication {

    private final RabbitTemplate template;
    private final Queue queue;

    public LocationCreationProducerApplication(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void sendMessage(String ipv4, Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.template.convertAndSend(queue.getName(), objectMapper.writeValueAsString(new LocationCreationMessage(ipv4, id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

