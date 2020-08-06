package uol.location.location.queue;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class LocationCreationProducerConfiguration {

    private ConnectionFactory factory;

    public LocationCreationProducerConfiguration(@Value("${rabbitmq.url}") String rabbitmqUrl, @Value("${rabbitmq.port}") String rabbitmqPort) {
        if (rabbitmqPort.equals("8089")) {
            factory = new MockConnectionFactory();
        } else {
            factory = new ConnectionFactory();
        }
        factory.setHost(rabbitmqUrl);
        factory.setPort(Integer.parseInt(rabbitmqPort));
    }

    public Channel createConnection() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
