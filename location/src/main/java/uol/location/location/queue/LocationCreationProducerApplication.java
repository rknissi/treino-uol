package uol.location.location.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import uol.location.location.queue.LocationCreationMessage;

@Component
public class LocationCreationProducerApplication {

    private final RabbitTemplate template;
    private final Queue queue;

    public LocationCreationProducerApplication(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void sendMessage(String ipv4, Long id) {
        this.template.convertAndSend(queue.getName(), new LocationCreationMessage(ipv4, id));
    }
}

