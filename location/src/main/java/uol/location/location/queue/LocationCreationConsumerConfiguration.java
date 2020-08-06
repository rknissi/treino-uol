package uol.location.location.queue;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;
import uol.location.location.application.LocationApplication;
import uol.location.location.repository.objects.LocationRepositoryEntity;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class LocationCreationConsumerConfiguration {

    public LocationCreationConsumerConfiguration(LocationApplication locationApplication,
                                                 @Value("${rabbitmq.url}") String rabbitmqUrl,
                                                 @Value("${rabbitmq.port}") String rabbitmqPort) throws IOException, TimeoutException {

        ConnectionFactory factory;
        if (rabbitmqPort.equals("8089")) {
            factory = new MockConnectionFactory();

        } else {
            factory = new ConnectionFactory();
            factory.setHost(rabbitmqUrl);
            factory.setPort(Integer.parseInt(rabbitmqPort));
        }
    }
}