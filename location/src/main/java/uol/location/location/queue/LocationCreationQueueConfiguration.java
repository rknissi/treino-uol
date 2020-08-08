package uol.location.location.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationCreationQueueConfiguration {

    @Bean
    public Queue createLocationCreationQueue() {
        return new Queue("location-creation");
    }
}
