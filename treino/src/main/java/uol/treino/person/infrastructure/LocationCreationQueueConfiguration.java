package uol.treino.person.infrastructure;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean(name = "binding-creation" )
    public Binding bindingCreation(Exchange direct,
                                   @Qualifier("location-creation") Queue createLocationCreationQueue) {
        return BindingBuilder.bind(createLocationCreationQueue)
                .to(direct).with(createLocationCreationQueue.getName()).noargs();
    }

    @Bean(name = "binding-delete" )
    public Binding bindingDelete(Exchange direct,
                                 @Qualifier("location-delete") Queue createLocationDeleteQueue) {
        return BindingBuilder.bind(createLocationDeleteQueue)
                .to(direct).with(createLocationDeleteQueue.getName()).noargs();
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("test");
    }
    //@Bean
    //public Exchange direct() {
    //    return new TopicExchange("test");
    //}

}
