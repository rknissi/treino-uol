package uol.treino.person.infrastructure;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationCreationQueueConfiguration {

    @Bean(name = "location-creation" )
    public Queue createLocationCreationQueue() {
        return new Queue("location-creation");
    }

    @Bean(name = "location-delete" )
    public Queue createLocationDeleteQueue() {
        return new Queue("location-delete");
    }
}
