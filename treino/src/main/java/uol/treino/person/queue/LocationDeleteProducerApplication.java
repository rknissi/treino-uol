package uol.treino.person.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LocationDeleteProducerApplication {

    private final RabbitTemplate template;

    @Qualifier("location-delete")
    @Autowired
    private Queue queue;

    @Qualifier("binding-delete")
    @Autowired
    private Binding binding;

    public LocationDeleteProducerApplication(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.template.convertAndSend(binding.getExchange(), queue.getName(), objectMapper.writeValueAsString(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

